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
        Random rand = new Random();

        // Entity Derived 
        Game game;

        // Entity Managers
        BlockManager     blockManager;
        LaserManager     laserManager;
        ExplosionManager explosionManager;
        PlayerManager    shipManager;
        CollisionManager collisionManager;

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
            shipManager      = new PlayerManager(game);
            collisionManager = new CollisionManager(blockManager, shipManager, laserManager, explosionManager);
        }

        public void EnteringState()
        {
            var resolution = game.GraphicsDevice.Viewport.Bounds;

            // Start the ship at the bottom in the center of the screen
            shipManager.Spawn();
            shipManager.ResetLives();
            shipManager.ResetScore();

            blockManager.SpawnBlocks(35);

            shipManager.Initialize();
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


                var blocksToRemove = new List<SpaceBlock>();

                blocksToRemove.ForEach(block => blockManager.Remove(block));

                WrapOffScreen(shipManager.Ship);

                laserManager.Update();
                blockManager.Update();
                explosionManager.Update();
                collisionManager.Update();
                shipManager.Update();
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
            laserManager.Draw();
            collisionManager.Draw();

            shipManager.Draw();
            // Write the score to the screen
            game.SpriteBatch.DrawString(game.Font, scoreDisplay, scorePosition, color, 0f, new Vector2(0, 0), game.ScreenScale, SpriteEffects.None, 0);
            game.SpriteBatch.Draw(game.HudTexture, new Rectangle(0, 0, game.HudTexture.Width, game.HudTexture.Height), Color.White);        }

        public void fireLaser(int x, int y)
        {
            laserManager.fireLaser(x, y);
        }

        public void ExitingState()
        {
        }

        // If the specified game object has left the screen,
        // wrap it around
        private void WrapOffScreen(Entity obj)
        {
            var viewPort = game.GraphicsDevice.Viewport.TitleSafeArea;

            if (obj.X > viewPort.Width) obj.X = 0;
            if (obj.X < 0) obj.X = viewPort.Width;

            if (obj.Y > viewPort.Height) obj.Y = 0;
            if (obj.Y < 0) obj.Y = viewPort.Height;
        } 
    }
}
