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
    /// <summary>
    /// When the game is in the splash screen state
    /// it draws the splash screen and waits for the player to press enter.
    /// 
    /// When the player presses enter the game switchs to the in-play state which is the state
    /// where the game play takes place.
    /// </summary>
    public class SplashScreenState : GameState
    {
        Game      game;
        Texture2D overlayTexture;
        Rectangle overlayRect;
        DateTime  enteredAt;

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

        public void Update()
        {
            KeyboardState keyboardState = Keyboard.GetState();

            // This waits 300 milliseconds after the splash screen state has been entered
            // before processing input.             
            if(keyboardState.IsKeyDown(Keys.Enter))
            {
                if (DateTime.Now.Subtract(enteredAt).Milliseconds > 300)
                {
                    game.CurrentState = game.InPlayState;
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
