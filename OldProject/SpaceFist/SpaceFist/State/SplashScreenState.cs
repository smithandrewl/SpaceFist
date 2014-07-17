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
    /// it draws the splash screen and waits for the player to press enter or click the mouse.
    /// 
    /// When the player presses enter the game switchs to the menu state.
    /// </summary>
    public class SplashScreenState : GameState
    {
        GameData  gameData;
        Texture2D overlayTexture;
        Rectangle overlayRect;
        DateTime  enteredAt;

        /// <summary>
        /// Creates a new SplashScreenState instance.
        /// </summary>
        /// <param name="gameData">Common game data</param>
        public SplashScreenState(GameData gameData)
        {
            this.gameData = gameData;
        }

        public void LoadContent()
        {
            var resolution = gameData.Resolution;

            overlayTexture = gameData.Content.Load<Texture2D>(@"Images\Backgrounds\TitleScreen");
            overlayRect    = new Rectangle(0, 0, resolution.Width, resolution.Height);
        }

        public void EnteringState()
        {
            enteredAt = DateTime.Now;
        }

        public void Update()
        {
            KeyboardState keyboardState = Keyboard.GetState();
            MouseState    mouseState    = Mouse.GetState();
            
            var timeDiff = DateTime.Now.Subtract(enteredAt);
            
            if (timeDiff.Seconds > 3)
            {
                gameData.CurrentState = gameData.MenuState;
            }

            // This waits 300 milliseconds after the splash screen state has been entered
            // before processing input.          
            else if ( timeDiff.Milliseconds > 300)
            {
                if (keyboardState.IsKeyDown(Keys.Enter) ||
                    keyboardState.IsKeyDown(Keys.Space) ||
                    keyboardState.IsKeyDown(Keys.Escape))
                {
                    gameData.CurrentState = gameData.MenuState;
                }

                if (mouseState.LeftButton == ButtonState.Pressed)
                {
                    gameData.CurrentState = gameData.MenuState;
                }
            }
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            gameData.SpriteBatch.Draw(gameData.Textures["Background"], gameData.Resolution, Color.White);
            gameData.SpriteBatch.Draw(overlayTexture, overlayRect, Color.White);
        }

        public void ExitingState()
        {
        }
    }
}
