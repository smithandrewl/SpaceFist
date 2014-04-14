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

namespace SpaceFist.State
{
    public class InPlayState : GameState
    {
        private const int NumBlocks  = 20;
        private const int NumEnemies = 40;
        private const float ScrollSpeed = 1.5f;

        public RoundData RoundData { get; set; }

        Random rand = new Random();

        Game game;
        public Ship ship
        {
            get
            {
                return shipManager.Ship;
            }
        }

        public Rectangle World  { get; set; }
        public Vector2   Camera { get; set; }

        // The entity managers used by this state (all of them)
        BlockManager      blockManager;
        ProjectileManager projectileManager;
        ExplosionManager  explosionManager;
        PlayerManager     shipManager;
        CollisionManager  collisionManager;
        PickUpManager     pickupManager;
        EnemyManager      enemyManager;

        public EnemyManager EnemyManager { get { return EnemyManager; }}

        public ProjectileManager ProjectileManager
        {
            get
            {
                return projectileManager;
            }
        }

        public InPlayState(Game game)
        {
            this.game = game;
            RoundData = new RoundData();
        }

        public void LoadContent()
        {
            var resolution   = game.GraphicsDevice.Viewport.Bounds;
            var screenRect   = new Rectangle(0, 0, resolution.Width, resolution.Height);
            
            blockManager      = new BlockManager(game, screenRect);
            projectileManager = new ProjectileManager(game);
            explosionManager  = new ExplosionManager(game);
            shipManager       = new PlayerManager(game);
            pickupManager     = new PickUpManager(game, resolution);
            enemyManager      = new EnemyManager(game, resolution);
            collisionManager  = new CollisionManager(blockManager, shipManager, projectileManager, explosionManager, pickupManager, enemyManager, RoundData);

            World = new Rectangle(0, 0, resolution.Width, resolution.Height * 10);
        }

        public void EnteringState()
        {
            RoundData.Reset();

            var resolution = game.GraphicsDevice.Viewport.Bounds;

            Camera = new Vector2(0, World.Height - resolution.Height);

            // Tell the ship manager to spawn the ship
            shipManager.Spawn();

            // Since the game states are reused, clear the score and lives
            shipManager.ResetLives();
            shipManager.ResetScore();

            // Spawn blocks to the world
            blockManager.SpawnBlocks(NumBlocks);

            enemyManager.Spawn(NumEnemies);
            // Spawn the players ship
            shipManager.Initialize();

            pickupManager.SpawnExamplePickups(5);
        }

        public void Update()
        {
            if (shipManager.Alive)
            {
                KeyboardState keys = Keyboard.GetState();

                if (keys.IsKeyDown(Keys.Q))
                {
                    game.Exit();
                }

                var viewPort = game.GraphicsDevice.Viewport.TitleSafeArea;
                
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
            }
            else
            {
                game.CurrentState = game.GameOverState;
            }
        }

        public void Draw(Microsoft.Xna.Framework.GameTime gameTime)
        {
            // Calculate the position that the score should be written to
            // It is the upper left with a padding of one percent
            Vector2 scorePosition =
                new Vector2(game.GraphicsDevice.Viewport.Width  * .01f,
                            game.GraphicsDevice.Viewport.Height * .01f);

            var scoreDisplay = String.Format("Score: {0} | Health: {1:P0} | Lives: {2}", shipManager.Score, shipManager.Ship.Health, shipManager.Lives);

            // Draw the background
            game.SpriteBatch.Draw(game.Background, game.BackgroundRect, Color.White);

            // Draw the score green if the user has a non-negative score
            // red otherwise
            var color = Color.Ivory;

             // Draw the entities
            explosionManager.Draw();
            blockManager.Draw();
            projectileManager.Draw();
            shipManager.Draw();
            enemyManager.Draw();
            pickupManager.Draw();

            // Write the score to the screen
            game.SpriteBatch.DrawString(
                game.Font, 
                scoreDisplay, 
                scorePosition, 
                color, 
                0f, 
                new Vector2(0, 0), 
                game.ScreenScale, 
                SpriteEffects.None, 
                0
            );
            
            game.SpriteBatch.Draw(
                game.HudTexture, 
                new Rectangle(
                    0, 
                    0, 
                    game.HudTexture.Width, 
                    game.HudTexture.Height
                ), 
                Color.White
            );        
        }

        public void ExitingState()
        {
        }

        // Keep the player on the screen
        private void KeepOnScreen(Entity obj)
        {
            var screen = game.GraphicsDevice.Viewport.TitleSafeArea;
            var world  = game.InPlayState.World;

            int farRight   = (int) Camera.X + screen.Width;
            int Bottom     = (int)Camera.Y + screen.Height;
            int halfHeight = obj.Rectangle.Height / 2;
            
            float velDecrease = .125f;

            bool offScreenRight  = obj.X > farRight;
            bool offScreenLeft   = obj.X < Camera.X;
            bool offscreenTop    = obj.Y + halfHeight > Bottom;
            bool offscreenBottom = obj.Y < Camera.Y;

            bool offScreen = offScreenRight || offScreenLeft || offscreenTop || offscreenBottom;

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
