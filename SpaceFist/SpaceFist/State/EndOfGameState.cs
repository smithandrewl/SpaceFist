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
        private Game      game;
        private RoundData roundData;

        public EndOfGameState(Game game, RoundData roundData)
        {
            this.game      = game;
            this.roundData = roundData;
        } 

        public void LoadContent()
        {
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            var resolution = game.GraphicsDevice.Viewport;

            game.SpriteBatch.Draw(game.EndOfGameTexture, new Rectangle(0, 0, resolution.Width, resolution.Height), Color.White);
        }

        public void Update()
        {
            KeyboardState keys = Keyboard.GetState();

            if (keys.IsKeyDown(Keys.Enter))
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
