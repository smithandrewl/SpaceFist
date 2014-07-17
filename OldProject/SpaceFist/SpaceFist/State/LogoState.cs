using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Input;

namespace SpaceFist.State
{
    /// <summary>
    /// This state displays the team logo before switching
    /// to the splash screen.
    /// </summary>
    public class LogoState : GameState
    {
        Texture2D background;
        DateTime  enteredAt;

        private const int loadTime = 3;
        
        private GameData gameData;

        public LogoState(GameData gameData)
        {
            this.gameData = gameData;
        }

        public void LoadContent()
        {
            background = gameData.Textures["Logo"];
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            gameData.SpriteBatch.Draw(
                background, 
                gameData.Resolution, 
                Color.White
            );
        }

        public void Update()
        {
            KeyboardState keys = Keyboard.GetState();

            if (DateTime.Now.Subtract(enteredAt).Seconds > loadTime || 
                keys.IsKeyDown(Keys.Enter)                          || 
                keys.IsKeyDown(Keys.Escape))
            {
                gameData.CurrentState = gameData.SplashScreenState;
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
