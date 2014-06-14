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

using FuncWorks.XNA.XTiled;
using SpaceFist.Entities.Enemies;

namespace SpaceFist.State
{
    /// <summary>
    /// The main state of the game.  All game play occurs in the InPlayState.
    /// </summary>
    public class InPlayState : GameState
    {
        private const int NumBlocks   = 40;

        // The number of background particles to spawn
        private const int DebrisCount = 4000;

        // The speed at which the camera scrolls up the map / world
        private const float ScrollSpeed = 1.5f;
        
        private List<Rectangle> debrisField;

        GameData gameData;
        private Hud hud;

        // The portion of the world which is currently visible.
        public Rectangle OnScreenWorld
        {
            get
            {
                var screenWidth  = gameData.Resolution.Width;
                var screenHeight = gameData.Resolution.Height;

                return new Rectangle((int) gameData.Camera.X, (int) gameData.Camera.Y, screenWidth, screenHeight);
            }
        }

        public Map Map { get; set; }

        // It is used to measure playtime.
        Stopwatch stopwatch = new Stopwatch();

        private Rectangle StartOfLevelMarkerPos { get; set; }
        private Rectangle EndOfLevelMarkerPos   { get; set; }

        public InPlayState(GameData gameData)
        {
            this.gameData        = gameData;
            gameData.RoundData   = new RoundData();
            debrisField          = new List<Rectangle>(DebrisCount);
        }

        public void LoadContent()
        {
            var resolution   = gameData.Resolution;
            var screenRect   = new Rectangle(0, 0, resolution.Width, resolution.Height);

            Map = gameData.Content.Load<Map>(@"Maps\01");

            gameData.World = new Rectangle(0, 0, resolution.Width, resolution.Height * 10);

            hud = new Hud(gameData, gameData.PlayerManager);
        }

        public void EnteringState()
        {
            // Reset the round statistics
            gameData.RoundData.Reset();

            // Start playing music on a loop
            MediaPlayer.Play(gameData.Songs["InPlay"]);
            MediaPlayer.IsRepeating = true;

            var resolution = gameData.Resolution;
            
            // Position the camera at the bottom of the world
            gameData.Camera = new Vector2(0, gameData.World.Height - resolution.Height);

            // Tell the ship manager to spawn the ship
            gameData.PlayerManager.Spawn();

            // Since the game states are reused, clear the score and lives
            gameData.PlayerManager.ResetLives();
            gameData.PlayerManager.ResetScore();
            gameData.PlayerManager.ResetWeapon();
            
            // Spawn blocks to the world
            gameData.BlockManager.SpawnBlocks(NumBlocks);

            gameData.EnemyManager.Clear();
            gameData.EnemyMineManager.Clear();
            gameData.PickUpManager.Reset();

            foreach (var fighter in Map.ObjectLayers[0].MapObjects)
            {
                var bounds = fighter.Bounds;
                Func<Vector2, Enemy> func;

                int count = 1;

                if (fighter.Properties.ContainsKey("count"))
                {
                    count = fighter.Properties["count"].AsInt32 ?? 1;
                }
                
                if (fighter.Type == "FighterZone")
                {
                    func = position => new EnemyFighter(gameData, position);

                    gameData.EnemyManager.SpawnEnemies(count, bounds.Left, bounds.Right, bounds.Top, bounds.Bottom, func);
                }
                else if (fighter.Type == "FreighterZone")
                {
                    func = position => new EnemyFreighter(gameData, position);

                    gameData.EnemyManager.SpawnEnemies(count, bounds.Left, bounds.Right, bounds.Top, bounds.Bottom, func);
                } 
                else if (fighter.Type == "Mines")
                {
                    gameData.EnemyMineManager.SpawnEnemyMine(bounds.Center.X, bounds.Center.Y);
                }
            }

            // Spawn the players ship
            gameData.PlayerManager.Initialize();

            // Spawn the different pickups to the world
            gameData.PickUpManager.Reset();
            gameData.PickUpManager.SpawnExtraLifePickups(3);
            gameData.PickUpManager.SpawnExamplePickups(4);
            gameData.PickUpManager.SpawnHealthPickups(4);

            /***Dongcai*/
            gameData.PickUpManager.SpawnLaserbeamPickups(5);
            gameData.PickUpManager.SpawnMissilePickups(3);
            /**********/

            Random rand = new Random();

            debrisField.Clear();

            // init debris field
            for (int i = 0; i < DebrisCount; i++)
            {
                var maxX  = gameData.World.Width;
                var maxY  = gameData.World.Height;
                var scale = rand.Next(10, 60) * .01f;

                Rectangle rect = new Rectangle(
                    rand.Next(0, maxX), 
                    rand.Next(0, maxY), 
                    (int) (gameData.Textures["Particle"].Width * scale), 
                    (int) (gameData.Textures["Particle"].Height * scale)
                );

                debrisField.Add(rect);
            }

            stopwatch.Reset();
            stopwatch.Start();
        }

        public void Update()
        {
            if (gameData.PlayerManager.Alive)
            {
                KeyboardState keys = Keyboard.GetState();

                if (keys.IsKeyDown(Keys.Q) || keys.IsKeyDown(Keys.Escape))
                {
                    gameData.CurrentState = gameData.MenuState;
                }
                
                KeepOnScreen(gameData.Ship);

                // Tell the entity managers to update
                gameData.ProjectileManager.Update();
                gameData.BlockManager.Update();
                gameData.ExplosionManager.Update();
                gameData.CollisionManager.Update();
                gameData.PlayerManager.Update();
                gameData.EnemyManager.Update();
                gameData.PickUpManager.Update();
                gameData.EnemyMineManager.Update();
 
                // Until the end of the world is reached, move the camera up the world
                if (gameData.Camera.Y >= gameData.World.Y)
                {
                    gameData.Camera = new Vector2(gameData.Camera.X, gameData.Camera.Y - ScrollSpeed);
                }

                // When the ship reaches the end of game marker, switch to the 
                // end of game state.
                if (gameData.Ship.Rectangle.Intersects(EndOfLevelMarkerPos))
                {
                    gameData.CurrentState = new EndOfGameState(gameData);
                }
            }
            else
            {
                // If the player has been killed, switch to the game over state
                gameData.CurrentState = gameData.GameOverState;
            }

            hud.Update();
        }

        public void Draw(Microsoft.Xna.Framework.GameTime gameTime)
        {
            // Draw the background
            gameData.SpriteBatch.Draw(gameData.Textures["Background"], gameData.Resolution, Color.White);

            // Draw the map
            Map.Draw(gameData.SpriteBatch, OnScreenWorld);

            // Draw debris
            foreach(var rect in debrisField)
            {
                gameData.SpriteBatch.Draw(
                    gameData.Textures["Particle"], 
                    new Rectangle(
                        rect.X - (int)gameData.Camera.X, 
                        rect.Y - (int)gameData.Camera.Y, 
                        rect.Width, 
                        rect.Height
                    ), 
                    Color.White
                );
            }

            // Draw the entities
            gameData.ExplosionManager.Draw();
            gameData.BlockManager.Draw();
            gameData.ProjectileManager.Draw();
            gameData.PlayerManager.Draw();
            gameData.EnemyManager.Draw();
            gameData.PickUpManager.Draw();
            gameData.EnemyMineManager.Draw();

            DrawLevelMarkers();

            hud.Draw();
        }

        private void DrawLevelMarkers()
        {
            int halfWidth  = (int)((gameData.World.Width / 2)    - gameData.Camera.X);
            int nearBottom = (int)((gameData.World.Bottom * .98) - gameData.Camera.Y);
            int nearTop    = (int)((gameData.World.Top * .02)    - gameData.Camera.Y);

            StartOfLevelMarkerPos = new Rectangle(
                (int)halfWidth - (gameData.Textures["LevelStart"].Width / 2),
                (int)nearBottom - gameData.Textures["LevelStart"].Height,
                gameData.Textures["LevelStart"].Width,
                gameData.Textures["LevelStart"].Height
            );

            EndOfLevelMarkerPos = new Rectangle(
                (int)halfWidth - (gameData.Textures["LevelEnd"].Width / 2),
                (int)nearTop + gameData.Textures["LevelEnd"].Height,
                gameData.Textures["LevelEnd"].Width,
                gameData.Textures["LevelEnd"].Height
            );

            // Draw the level markers
            gameData.SpriteBatch.Draw(
                gameData.Textures["LevelStart"],
                StartOfLevelMarkerPos,
                Color.White
            );

            gameData.SpriteBatch.Draw(
                gameData.Textures["LevelEnd"],
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
            var resolution = gameData.Resolution;

            int farRight   = (int)gameData.Camera.X + resolution.Width;
            int Bottom     = (int)gameData.Camera.Y + resolution.Height;
            int halfHeight = obj.Rectangle.Height / 2;

            float velDecrease = .125f;
            
            bool offScreenRight  = obj.X > farRight;
            bool offScreenLeft   = obj.X < gameData.Camera.X;
            bool offscreenTop    = obj.Y + halfHeight > Bottom;
            bool offscreenBottom = obj.Y < gameData.Camera.Y;

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
                    obj.X = (int)gameData.Camera.X;
                }
                else if (offscreenTop)
                {
                    obj.Y = (int)Bottom - obj.Rectangle.Height;
                }
                else if (offscreenBottom)
                {
                    obj.Y = (int)gameData.Camera.Y + (obj.Rectangle.Height / 16);
                }

                obj.Velocity *= -1 * velDecrease;
            }
        }
    }
}
