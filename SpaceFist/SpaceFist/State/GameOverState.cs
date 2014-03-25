using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Input;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State
{
    public class GameOverState : GameState
    {
        private Game game;

        public GameOverState(Game game)
        {
            this.game = game;
        }

        public void LoadContent()
        {

        }

        public void EnteringState()
        {

        }

        public void Update()
        {
            KeyboardState keys = Keyboard.GetState();

            if (keys.IsKeyDown(Keys.Enter))
            {
                game.State = game.SplashScreenState;
            }
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            game.SpriteBatch.Draw(game.GameOverTexture, game.BackgroundRect, Color.White);
        }

        public void ExitingState()
        {

        }
    }
}
