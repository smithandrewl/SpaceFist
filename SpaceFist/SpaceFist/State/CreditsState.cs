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
        private Game     game;
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
            var resolution = game.Resolution;

            var y       = (int) (0.63   * resolution.Height);
            var leftX   = (int) (0.1    * resolution.Width);
            var rightX  = (int) (0.7325 * resolution.Width);
            var centerX = (int) (0.5    * resolution.Width) - 103;

            game.SpriteBatch.Draw(
                game.Textures["CreditsTexture"], 
                game.Resolution, 
                Color.White
            );

            game.SpriteBatch.DrawString(
                game.Font, 
                "Dongcai Huang",     
                new Vector2(leftX, y),  
                Color.White
            );
            
            game.SpriteBatch.DrawString(
                game.Font, 
                "Programming", 
                new Vector2(leftX, y + 30), 
                Color.PeachPuff
            );
            
            game.SpriteBatch.DrawString(
                game.Font, 
                "Art Selection", 
                new Vector2(leftX, y + 45), 
                Color.PeachPuff
            );

            game.SpriteBatch.DrawString(
                game.Font, 
                "Tatsuya Takahashi", 
                new Vector2(rightX, y), 
                Color.White
            );
            
            game.SpriteBatch.DrawString(
                game.Font, 
                "Programming", 
                new Vector2(rightX + 40, y + 30), 
                Color.PeachPuff
            );
            
            game.SpriteBatch.DrawString(
                game.Font, 
                "Andrew Smith", 
                new Vector2(centerX, y), 
                Color.White
            );
            
            game.SpriteBatch.DrawString(
                game.Font, 
                "Programming / AI", 
                new Vector2(centerX - 30, y + 30), 
                Color.PeachPuff
            );
            
            game.SpriteBatch.DrawString(
                game.Font, 
                "Art Selection", 
                new Vector2(centerX, y + 45), 
                Color.PeachPuff
            );
            
            game.SpriteBatch.DrawString(
                game.Font, 
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
                    game.CurrentState = game.MenuState;
                }
            }
        }

        public void EnteringState()
        {
            game.IsMouseVisible = true;
            enteredAt           = DateTime.Now;
        }

        public void ExitingState()
        {
            game.IsMouseVisible = false;
        }
    }
}
