using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State
{
    public class SplashScreenState : GameState
    {
        Game      game;
        Texture2D overlayTexture;
        Rectangle overlayRect;
        DateTime enteredAt;

        public SplashScreenState(Game game)
        {
            this.game = game;
        }

        public void LoadContent()
        {
            var viewport  = game.GraphicsDevice.Viewport;
            var titleSafe = game.GraphicsDevice.Viewport.TitleSafeArea;

            overlayTexture = game.Content.Load<Texture2D>(@"Images\Backgrounds\TitleScreen");
            overlayRect    = new Rectangle(0, 0, titleSafe.Width, titleSafe.Height);
        }

        public void EnteringState()
        {
            enteredAt = DateTime.Now;
        }

        public void Update(Microsoft.Xna.Framework.GameTime time)
        {
            KeyboardState keyboardState = Keyboard.GetState();


            if(keyboardState.IsKeyDown(Keys.Enter))
            {
                if (DateTime.Now.Subtract(enteredAt).Milliseconds > 300)
                {
                    game.SetState(game.InPlayState);
                }
            }
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            game.SpriteBatch.Draw(game.Background, game.BackgroundRect, Color.White);
            game.SpriteBatch.Draw(overlayTexture, overlayRect, Color.White);
        }

        public void ExitingState()
        {
        }
    }
}
