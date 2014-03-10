using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using SpaceshipShooter.Entities;
using SpaceshipShooter.Managers;
using SpaceshipShooter.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceshipShooter.State
{
    public class InPlayState : GameState
    {
        private int score = 0;
        private int lives = 3;
        Random rand = new Random();

        // GameObject Derived 
        Ship ship;
        Game game;

        // Entity Managers
        BlockManager     blockManager;
        LaserManager     laserManager;
        ExplosionManager explosionManager;

        public InPlayState(Game game)
        {
            this.game = game;
        }

        public void LoadContent()
        {
            var resolution = game.GraphicsDevice.Viewport.Bounds;

            var screenRect   = new Rectangle(0, 0, resolution.Width, resolution.Height);
            blockManager     = new BlockManager(game, screenRect);
            laserManager     = new LaserManager(game);
            explosionManager = new ExplosionManager(game);
        }

        public void EnteringState()
        {
            var resolution = game.GraphicsDevice.Viewport.Bounds;

            // Start the ship at the bottom in the center of the screen
            ship = new Ship(game, new Vector2((float)(resolution.Width / 2), 
                                              (float)(resolution.Height * .80)));
            
            blockManager.SpawnBlocks(35);

            ship.Initialize();
        }

        public void Update(Microsoft.Xna.Framework.GameTime gameTime)
        {
            KeyboardState keys = Keyboard.GetState();

            if (keys.IsKeyDown(Keys.Q))
            {
                game.Exit();
            }

            var viewPort = game.GraphicsDevice.Viewport.TitleSafeArea;


            var blocksToRemove = new List<SpaceBlock>();

            //  Update blocks
            foreach (var block in blockManager.collisions(ship))
            {
                    // Create an explosion at the coordinates of the block
                    explosionManager.add(block.X, block.Y);

                    // Decrement the score since the ship hit a block
                    ship.HealthPoints -= 25;

                    if (ship.HealthPoints <= 0)
                    {
                        if (lives > 0)
                        {
                            lives--;
                            ship.HealthPoints = 100;
                        }
                        else
                        {
                            ship.Alive = false;
                            explosionManager.add(ship.X, ship.Y);
                        }
                    }
                    // Notify the block that it has been hit
                    block.Thump();
                    blocksToRemove.Add(block);
            }
            
            foreach (var laser in laserManager)
            {
                // Only process lasers that are still in play
                if (laser.Alive)
                {
                    // If an alive laser hits a block
                    foreach (var block in blockManager.collisions(laser))
                    {
                        // Create and add a new explosion
                        explosionManager.add(block.X, block.Y);

                        // Update the score
                        score += 1;

                        // Kill the block
                        block.Destroy();
                        blocksToRemove.Add(block);
                    }

                }
            }      

            blocksToRemove.ForEach(block => blockManager.Remove(block));

            WrapOffScreen(ship);

            laserManager.Update(gameTime);
            blockManager.Update(gameTime);
            explosionManager.Update(gameTime);

            ship.Update(gameTime);
        }

        public void Draw(Microsoft.Xna.Framework.GameTime gameTime)
        {
            // Calculate the position that the score should be written to
            // It is the upper left with a padding of one percent
            Vector2 scorePosition =
                new Vector2(game.GraphicsDevice.Viewport.Width * .01f,
                            game.GraphicsDevice.Viewport.Height * .01f);

            var scoreDisplay = String.Format("Score: {0} | Health: {1:P0} | Lives: {2}", score, ship.Health, lives);

            // Draw the background
            game.SpriteBatch.Draw(game.Background, game.BackgroundRect, Color.White);

            // Draw the score green if the user has a non-negative score
            // red otherwise
            var color = Color.Ivory;

             // Draw the entities
            explosionManager.Draw(gameTime);
            blockManager.Draw(gameTime);
            laserManager.Draw(gameTime);

            ship.Draw(gameTime);
            // Write the score to the screen
            game.SpriteBatch.DrawString(game.Font, scoreDisplay, scorePosition, color, 0f, new Vector2(0, 0), game.ScreenScale, SpriteEffects.None, 0);
               
        
            game.SpriteBatch.Draw(game.HudTexture, new Rectangle(0, 0, game.HudTexture.Width, game.HudTexture.Height), Color.White);
            
        }

        public void fireLaser(int x, int y)
        {
            laserManager.fireLaser(x, y);
        }

        public void ExitingState()
        {
        }

        // If the specified game object has left the screen,
        // wrap it around
        private void WrapOffScreen(GameObject obj)
        {
            var viewPort = game.GraphicsDevice.Viewport.TitleSafeArea;

            if (obj.X > viewPort.Width) obj.X = 0;
            if (obj.X < 0) obj.X = viewPort.Width;

            if (obj.Y > viewPort.Height) obj.Y = 0;
            if (obj.Y < 0) obj.Y = viewPort.Height;
        } 
    }
}
