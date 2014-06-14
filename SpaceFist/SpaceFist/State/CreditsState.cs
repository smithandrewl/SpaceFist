using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State
{
    /// <summary>
    /// This state displays information about the developers of
    /// the game.
    /// </summary>
    public class CreditsState : GameState
    {
        private GameData     gameData;
        private DateTime     enteredAt;

        public CreditsState(GameData gameData)
        {
            this.gameData = gameData;
        }

        public void LoadContent()
        {
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            var resolution = gameData.Resolution;

            var y       = (int) (0.63   * resolution.Height);
            var leftX   = (int) (0.1    * resolution.Width);
            var rightX  = (int) (0.7325 * resolution.Width);
            var centerX = (int) (0.5    * resolution.Width) - 103;

            gameData.SpriteBatch.Draw(
                gameData.Textures["Credits"], 
                gameData.Resolution, 
                Color.White
            );

            gameData.SpriteBatch.DrawString(
                gameData.Font, 
                "Dongcai Huang",     
                new Vector2(leftX, y),  
                Color.White
            );
            
            gameData.SpriteBatch.DrawString(
                gameData.Font, 
                "Programming", 
                new Vector2(leftX, y + 30), 
                Color.PeachPuff
            );
            
            gameData.SpriteBatch.DrawString(
                gameData.Font, 
                "Art Selection", 
                new Vector2(leftX, y + 45), 
                Color.PeachPuff
            );

            gameData.SpriteBatch.DrawString(
                gameData.Font, 
                "Tatsuya Takahashi", 
                new Vector2(rightX, y), 
                Color.White
            );
            
            gameData.SpriteBatch.DrawString(
                gameData.Font, 
                "Programming", 
                new Vector2(rightX + 40, y + 30), 
                Color.PeachPuff
            );
            
            gameData.SpriteBatch.DrawString(
                gameData.Font, 
                "Andrew Smith", 
                new Vector2(centerX, y), 
                Color.White
            );
            
            gameData.SpriteBatch.DrawString(
                gameData.Font, 
                "Programming / AI", 
                new Vector2(centerX - 30, y + 30), 
                Color.PeachPuff
            );
            
            gameData.SpriteBatch.DrawString(
                gameData.Font, 
                "Art Selection", 
                new Vector2(centerX, y + 45), 
                Color.PeachPuff
            );
            
            gameData.SpriteBatch.DrawString(
                gameData.Font, 
                "Sound Selection", 
                new Vector2(centerX - 10, y + 60), 
                Color.PeachPuff
            );
        }

        public void Update()
        {
            if (DateTime.Now.Subtract(enteredAt).Milliseconds > 300)
            {
                enteredAt = DateTime.Now;

                MouseState    mouse = Mouse.GetState();
                KeyboardState keys  = Keyboard.GetState();

                if (mouse.LeftButton == ButtonState.Pressed || 
                    keys.IsKeyDown(Keys.Enter)              || 
                    keys.IsKeyDown(Keys.Escape))
                {
                    gameData.CurrentState = gameData.MenuState;
                }
            }
        }

        public void EnteringState()
        {
            enteredAt           = DateTime.Now;
        }

        public void ExitingState()
        {
        }
    }
}
