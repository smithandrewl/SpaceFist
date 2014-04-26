using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State
{
    public class CreditsState : GameState
    {
        private Game game;
        private DateTime enteredAt;

        public CreditsState(Game game)
        {
            this.game = game;
        }

        public void LoadContent()
        {
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            var resolution = game.GraphicsDevice.Viewport;

            var y      = (int) (0.63   * resolution.Height);
            var leftX  = (int) (0.1    * resolution.Width);
            var rightX = (int) (0.7325 * resolution.Width);

            game.SpriteBatch.Draw(game.CreditsTexture, game.GraphicsDevice.Viewport.Bounds, Color.White);

            game.SpriteBatch.DrawString(game.Font, "Dongcai Huang",     new Vector2(leftX, y),  Color.White);
            game.SpriteBatch.DrawString(game.Font, "Tatsuya Takahashi", new Vector2(rightX, y), Color.White);
        }

        public void Update()
        {
            if (DateTime.Now.Subtract(enteredAt).Milliseconds > 300)
            {
                enteredAt = DateTime.Now;

                MouseState    mouse = Mouse.GetState();
                KeyboardState keys  = Keyboard.GetState();

                if (mouse.LeftButton == ButtonState.Pressed || keys.IsKeyDown(Keys.Enter))
                {
                    game.CurrentState = game.MenuState;
                }
            }
        }

        public void EnteringState()
        {
            game.IsMouseVisible = true;
            enteredAt = DateTime.Now;
        }

        public void ExitingState()
        {
            game.IsMouseVisible = false;
        }
    }
}
