using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;
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
            var titleSafe = game.GraphicsDevice.Viewport.TitleSafeArea;

            overlayTexture = game.Content.Load<Texture2D>(@"Images\Backgrounds\TitleScreen");
            overlayRect    = new Rectangle(0, 0, titleSafe.Width, titleSafe.Height);
        }

        public void EnteringState()
        {
            enteredAt = DateTime.Now;

            game.IsMouseVisible = true;
        }

        public void Update()
        {
            KeyboardState keyboardState = Keyboard.GetState();
            MouseState    mouseState    = Mouse.GetState();
            
            var timeDiff = DateTime.Now.Subtract(enteredAt);
            
            if (timeDiff.Seconds > 3)
            {
                game.CurrentState = game.MenuState;
            }

            // This waits 300 milliseconds after the splash screen state has been entered
            // before processing input.          
            else if ( timeDiff.Milliseconds > 300)
            {
                if (keyboardState.IsKeyDown(Keys.Enter) ||
                    keyboardState.IsKeyDown(Keys.Space) ||
                    keyboardState.IsKeyDown(Keys.Escape))
                {
                    game.CurrentState = game.MenuState;
                }

                if (mouseState.LeftButton == ButtonState.Pressed)
                {
                    game.CurrentState = game.MenuState;
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
            game.IsMouseVisible = false;
        }
    }
}
