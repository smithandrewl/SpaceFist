using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Project2.Entities;
using Project2.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Project2.State
{
    public class InPlayState : GameState
    {
        private int score = 0;

        Random rand = new Random();

        // GameObject Derived 
        Ship ship;
        Game game;

        List<SpaceBlock> blocks     = new List<SpaceBlock>();
        List<Laser>      lasers     = new List<Laser>();
        List<Explosion>  explosions = new List<Explosion>();

        public InPlayState(Game game)
        {
            this.game = game;
        }

        public void LoadContent()
        {
            var resolution = game.GraphicsDevice.Viewport.Bounds;

            // Start the ship at the bottom in the center of the screen
            ship = new Ship(game, new Vector2((float)(resolution.Width / 2), (float)(resolution.Height * .80)));
            ship.LoadContent();

            blocks.ForEach(block => block.LoadContent());
        }

        public void EnteringState()
        {
            var resolution = game.GraphicsDevice.Viewport.Bounds;

            // Start the ship at the bottom in the center of the screen
            ship = new Ship(game, new Vector2((float)(resolution.Width / 2), 
                                              (float)(resolution.Height * .80)));
            ship.LoadContent();

            var viewport  = game.GraphicsDevice.Viewport;
            var titleSafe = game.GraphicsDevice.Viewport.TitleSafeArea;

            // Spawn space blocks
            for (int i = 0; i < 15; i++)
            {
                // Generate a random position on-screen
                var randX = rand.Next(titleSafe.Width);
                var randY = rand.Next(titleSafe.Height);

                // Generate a random velocity of between -5 and 5d 
                var randXVel = rand.Next(10) - 5;
                var randYVel = rand.Next(10) - 5;

                // Construct the block
                var block =
                    new SpaceBlock(game,
                        new Vector2(randX, randY),
                        new Vector2(randXVel, randYVel));

                // Initialize and the block to the list
                
                block.Initialize();
                block.LoadContent();
                blocks.Add(block);
            }

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

            //  Update blocks
            foreach (var block in blocks.Where(block => block.Alive))
            {
                block.Update(gameTime);
                WrapOffScreen(block);

                // Block collided with the ship
                if (block.Rectangle.Intersects(ship.Rectangle))
                {
                    // Create an explosion at the coordinates of the blockdddd
                    // and and add it to the list of explosions
                    var explosion = new Explosion(game, new Vector2(block.X, block.Y));
                    
                    explosion.LoadContent();
                    explosions.Add(explosion);

                    // Decrement the score since the ship hit a block
                    score -= 1;

                    // Notify the block that it has been hit
                    block.Thump();

                }

                foreach (var laser in lasers)
                {
                    // Only process lasers that are still in play
                    if (laser.Alive)
                    {
                        // If a live laser has hit the block
                        if (block.Rectangle.Intersects(laser.Rectangle))
                        {
                            // Create and add a new explosion to the list
                            var explosion = new Explosion(game, new Vector2(block.X, block.Y));
                    
                            explosion.LoadContent();
                            explosions.Add(explosion);

                            // Update the score
                            score += 1;

                            // Kill the block
                            block.Destroy();
                        }
                    }
                }
            };

            WrapOffScreen(ship);

            lasers.ForEach(laser => laser.Update(gameTime));
            explosions.ForEach(explosion => explosion.Update(gameTime));

            ship.Update(gameTime);
        }

        public void Draw(Microsoft.Xna.Framework.GameTime gameTime)
        {
            // Calculate the position that the score should be written to
            // It is the upper left with a padding of one percent
            Vector2 scorePosition =
                new Vector2(game.GraphicsDevice.Viewport.Width * .01f,
                            game.GraphicsDevice.Viewport.Height * .01f);

            var scoreDisplay = String.Format("Score: {0}", score);

            // Draw the background
            game.SpriteBatch.Draw(game.Background, game.BackgroundRect, Color.White);

            // Draw the score green if the user has a non-negative score
            // red otherwise
            var color = score >= 0 ? Color.GreenYellow : Color.Crimson;

            // Write the score to the screen
            game.SpriteBatch.DrawString(game.Font, scoreDisplay, scorePosition, color, 0f, new Vector2(0, 0), game.ScreenScale, SpriteEffects.None, 0);

            // Draw the entities
            explosions.ForEach(explosion => explosion.Draw(gameTime));
            blocks.ForEach(block => block.Draw(gameTime));
            lasers.ForEach(laser => laser.Draw(gameTime));
            ship.Draw(gameTime);
        }

        public void ExitingState()
        {
        }

        public void fireLaser(int x, int y)
        {
            // Place a new active laser at x, y
            Laser laser = new Laser(game, new Vector2(x, y));
            laser.LoadContent();

            lasers.Add(laser);
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
