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
            game.SpriteBatch.Draw(game.CreditsTexture, game.GraphicsDevice.Viewport.Bounds, Color.White);
        }

        public void Update()
        {
            if (DateTime.Now.Subtract(enteredAt).Milliseconds > 300)
            {
                enteredAt = DateTime.Now;

                MouseState mouse = Mouse.GetState();

                if (mouse.LeftButton == ButtonState.Pressed)
                {
                    game.CurrentState = game.MenuState;
                }
            }
        }

        public void EnteringState()
        {
            enteredAt = DateTime.Now;
        }

        public void ExitingState()
        {
        }
    }
}
