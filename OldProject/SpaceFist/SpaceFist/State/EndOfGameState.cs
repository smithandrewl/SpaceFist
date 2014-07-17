using Microsoft.Xna.Framework;
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
    /// This state is shown when the player has survived to the end of the game.
    /// </summary>
    public class EndOfGameState : GameState
    {
        private GameData gameData;

        public EndOfGameState(GameData gameData)
        {
            this.gameData = gameData;
        } 

        public void LoadContent()
        {
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            var resolution = gameData.Resolution;

            gameData.SpriteBatch.Draw(
                gameData.Textures["EndOfGame"], 
                new Rectangle(
                    0, 
                    0, 
                    resolution.Width, 
                    resolution.Height
                ), 
                Color.White
            );
        }

        public void Update()
        {
            KeyboardState keys = Keyboard.GetState();

            if (keys.IsKeyDown(Keys.Enter) || keys.IsKeyDown(Keys.Escape))
            {
                gameData.CurrentState = gameData.SplashScreenState;
            }
        }

        public void EnteringState()
        {
            MediaPlayer.IsRepeating = true;
            MediaPlayer.Play(gameData.Songs["EndOfGame"]);
        }

        public void ExitingState()
        {
            MediaPlayer.Stop();
        }
    }
}
