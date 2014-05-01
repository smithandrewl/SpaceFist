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
    public class EndOfGameState : GameState
    {
        private Game game;

        public EndOfGameState(Game game, RoundData roundData)
        {
            this.game = game;
        } 

        public void LoadContent()
        {
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            var resolution = game.Resolution;

            game.SpriteBatch.Draw(
                game.EndOfGameTexture, 
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
                game.CurrentState = game.SplashScreenState;
            }
        }

        public void EnteringState()
        {
            MediaPlayer.IsRepeating = true;
            MediaPlayer.Play(game.EndOfGameSong);
        }

        public void ExitingState()
        {
            MediaPlayer.Stop();
        }
    }
}
