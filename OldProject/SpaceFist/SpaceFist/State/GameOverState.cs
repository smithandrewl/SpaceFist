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
    /// GameOverState draws the game over message until the player
    /// hits enter.  When the player hits enter, the player is taken to to the
    /// main menu.
    /// </summary>
    public class GameOverState : GameState
    {
        private GameData gameData;

        public GameOverState(GameData gameData)
        {
            this.gameData = gameData;
        }

        public void LoadContent()
        {

        }

        public void EnteringState()
        {
            MediaPlayer.IsRepeating = true;
            MediaPlayer.Play(gameData.Songs["GameOver"]);
        }

        public void Update()
        {
            KeyboardState keys = Keyboard.GetState();

            if (keys.IsKeyDown(Keys.Enter) || keys.IsKeyDown(Keys.Escape))
            {
                gameData.CurrentState = gameData.SplashScreenState;
            }
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            // Draw the game over image
            gameData.SpriteBatch.Draw(gameData.Textures["GameOver"], gameData.Resolution, Color.White);
            gameData.SpriteBatch.Draw(gameData.Textures["GameOver"], gameData.Resolution, Color.White);
        }

        public void ExitingState()
        {
            MediaPlayer.Stop();
        }
    }
}
