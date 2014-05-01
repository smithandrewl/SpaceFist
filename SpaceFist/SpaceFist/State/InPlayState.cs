using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using SpaceFist.Entities;
using SpaceFist.Managers;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;
using Microsoft.Xna.Framework.Media;

namespace SpaceFist.State
{
    public class InPlayState : GameState
    {
        private const int NumBlocks   = 40;
        private const int NumEnemies  = 80;
        private const int DebrisCount = 4000;

        private const float ScrollSpeed = 1.5f;
        
        private List<Rectangle> debrisField;

        Game game;
        private Hud hud;

        public Ship ship
        {
            get
            {
                return shipManager.Ship;
            }
        }

        public Rectangle OnScreenWorld
        {
            get
            {
                var screenWidth = game.Resolution.Width;
                var screenHeight = game.Resolution.Height;

                return new Rectangle((int) Camera.X, (int) Camera.Y, screenWidth, screenHeight);
            }
        }

        public Rectangle World     { get; set; }
        public Vector2   Camera    { get; set; }
        public RoundData RoundData { get; set; }

        // The entity managers used by this state (all of them)
        BlockManager      blockManager;
        ProjectileManager projectileManager;
        ExplosionManager  explosionManager;
        PlayerManager     shipManager;
        CollisionManager  collisionManager;
        PickUpManager     pickupManager;
        EnemyManager      enemyManager;

        // It is used to measure playtime.
        Stopwatch stopwatch = new Stopwatch();

        private Rectangle StartOfLevelMarkerPos { get; set; }
        private Rectangle EndOfLevelMarkerPos   { get; set; }

        public EnemyManager EnemyManager 
        { 
            get 
            { 
                return enemyManager; 
            } 
        }
        
        public BlockManager BlockManager 
        { 
            get 
            { 
                return blockManager; 
            } 
        }

        public ProjectileManager ProjectileManager
        {
            get
            {
                return projectileManager;
            }
        }

        public InPlayState(Game game)
        {
            this.game   = game;
            RoundData   = new RoundData();
            debrisField = new List<Rectangle>(DebrisCount);
        }

        public void LoadContent()
        {
            var resolution   = game.Resolution;
            var screenRect   = new Rectangle(0, 0, resolution.Width, resolution.Height);
            
            blockManager      = new BlockManager(game);
            projectileManager = new ProjectileManager(game);
            explosionManager  = new ExplosionManager(game);
            shipManager       = new PlayerManager(game);
            pickupManager     = new PickUpManager(game, resolution);
            enemyManager      = new EnemyManager(game);

            collisionManager  = new CollisionManager(
                game, 
                blockManager, 
                shipManager, 
                projectileManager, 
                explosionManager, 
                pickupManager, 
                enemyManager, 
                RoundData
            );

            World = new Rectangle(0, 0, resolution.Width, resolution.Height * 10);

            hud = new Hud(game, shipManager);
        }

        public void EnteringState()
        {
            RoundData.Reset();

            MediaPlayer.Play(game.InPlaySong);
            MediaPlayer.IsRepeating = true;

            var resolution = game.Resolution;

            Camera = new Vector2(0, World.Height - resolution.Height);

            // Tell the ship manager to spawn the ship
            shipManager.Spawn();

            // Since the game states are reused, clear the score and lives
            shipManager.ResetLives();
            shipManager.ResetScore();

            // Spawn blocks to the world
            blockManager.SpawnBlocks(NumBlocks);

            enemyManager.Clear();
            enemyManager.SpawnEnemyFighters((int) (NumEnemies * (7/8f)));
            enemyManager.SpawnEnemyFreighters((int) (NumEnemies * (1/8f)));

            // Spawn the players ship
            shipManager.Initialize();

            pickupManager.Reset();
            pickupManager.SpawnExtraLifePickups(3);
            pickupManager.SpawnExamplePickups(4);
            pickupManager.SpawnHealthPickups(4);

            /***Dongcai*/
            pickupManager.SpawnLaserbeamPickups(5);
            pickupManager.SpawnMissilePickups(3);
            /**********/

            Random rand = new Random();

            debrisField.Clear();

            // init debris field
            for (int i = 0; i < DebrisCount; i++)
            {
                var maxX  = World.Width;
                var maxY  = World.Height;
                var scale = rand.Next(10, 60) * .01f;

                Rectangle rect = new Rectangle(
                    rand.Next(0, maxX), 
                    rand.Next(0, maxY), 
                    (int) (game.ParticleTexture.Width * scale), 
                    (int) (game.ParticleTexture.Height * scale)
                );

                debrisField.Add(rect);
            }

            stopwatch.Reset();
            stopwatch.Start();
        }

        public void Update()
        {
            if (shipManager.Alive)
            {
                KeyboardState keys = Keyboard.GetState();

                if (keys.IsKeyDown(Keys.Q) || keys.IsKeyDown(Keys.Escape))
                {
                    game.CurrentState = game.MenuState;
                }
                
                KeepOnScreen(shipManager.Ship);

                // Tell the entity managers to update
                projectileManager.Update();
                blockManager.Update();
                explosionManager.Update();
                collisionManager.Update();
                shipManager.Update();
                enemyManager.Update();
                pickupManager.Update();
 
                if (Camera.Y >= World.Y)
                {
                    Camera = new Vector2(Camera.X, Camera.Y - ScrollSpeed);
                }

                if (ship.Rectangle.Intersects(EndOfLevelMarkerPos))
                {
                    game.CurrentState = new EndOfGameState(game, RoundData);
                }
            }
            else
            {
                game.CurrentState = game.GameOverState;

                //send gameData(playtime and score)
                stopwatch.Stop();
                game.gameData.finalScore = RoundData.Score;
                game.gameData.ConvertToSecond(stopwatch.ElapsedMilliseconds);

            }

            hud.Update();
        }

        public void Draw(Microsoft.Xna.Framework.GameTime gameTime)
        {     
            // Draw the background
            game.SpriteBatch.Draw(game.Background, game.BackgroundRect, Color.White);

            // Draw debris
            foreach(var rect in debrisField)
            {
                game.SpriteBatch.Draw(
                    game.ParticleTexture, 
                    new Rectangle(
                        rect.X - (int)Camera.X, 
                        rect.Y - (int)Camera.Y, 
                        rect.Width, 
                        rect.Height
                    ), 
                    Color.White
                );
            }

            // Draw the entities
            explosionManager.Draw();
            blockManager.Draw();
            projectileManager.Draw();
            shipManager.Draw();
            enemyManager.Draw();
            pickupManager.Draw();

            DrawLevelMarkers();

            hud.Draw();
        }

        private void DrawLevelMarkers()
        {
            int halfWidth  = (int)((World.Width / 2) - Camera.X);
            int nearBottom = (int)((World.Bottom * .98) - Camera.Y);
            int nearTop    = (int)((World.Top * .02) - Camera.Y);

            StartOfLevelMarkerPos = new Rectangle(
                (int)halfWidth - (game.LevelStartTexture.Width / 2),
                (int)nearBottom - game.LevelStartTexture.Height,
                game.LevelStartTexture.Width,
                game.LevelStartTexture.Height
            );

            EndOfLevelMarkerPos = new Rectangle(
                (int)halfWidth - (game.LevelEndTexture.Width / 2),
                (int)nearTop + game.LevelEndTexture.Height,
                game.LevelEndTexture.Width,
                game.LevelEndTexture.Height
            );

            // Draw the level markers
            game.SpriteBatch.Draw(
                game.LevelStartTexture,
                StartOfLevelMarkerPos,
                Color.White
            );

            game.SpriteBatch.Draw(
                game.LevelEndTexture,
                EndOfLevelMarkerPos,
                Color.White
            );
        }

        public void ExitingState()
        {
            MediaPlayer.Stop();
        }

       // Keep the player on the screen
        private void KeepOnScreen(Entity obj)
        {
            var resolution = game.Resolution;

            int farRight   = (int)Camera.X + resolution.Width;
            int Bottom     = (int)Camera.Y + resolution.Height;
            int halfHeight = obj.Rectangle.Height / 2;

            float velDecrease = .125f;
            
            bool offScreenRight  = obj.X > farRight;
            bool offScreenLeft   = obj.X < Camera.X;
            bool offscreenTop    = obj.Y + halfHeight > Bottom;
            bool offscreenBottom = obj.Y < Camera.Y;

            bool offScreen = offScreenRight || 
                             offScreenLeft  || 
                             offscreenTop   || 
                             offscreenBottom;

            if (offScreen)
            {
                if (offScreenRight)
                {
                    obj.X = (int)farRight - obj.Rectangle.Width;
                }
                else if (offScreenLeft)
                {
                    obj.X = (int)Camera.X;
                }
                else if (offscreenTop)
                {
                    obj.Y = (int)Bottom - obj.Rectangle.Height;
                }
                else if (offscreenBottom)
                {
                    obj.Y = (int)Camera.Y + (obj.Rectangle.Height / 16);
                }

                obj.Velocity *= -1 * velDecrease;
            }
        }
    }
}
