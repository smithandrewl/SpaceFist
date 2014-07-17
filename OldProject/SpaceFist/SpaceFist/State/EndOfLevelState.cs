using Microsoft.Xna.Framework.Input;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State
{
    public class EndOfLevelState : GameState
    {
        private GameData gameData;

        public EndOfLevelState(GameData gameData)
        {
            this.gameData = gameData;
        }

        public void LoadContent()
        {
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
        }

        public void Update()
        {
            var mouse = Mouse.GetState();

            if (mouse.LeftButton == ButtonState.Pressed || mouse.RightButton == ButtonState.Pressed)
            {
                gameData.LevelManager.LoadLevel(gameData.Level.LevelId + 1);
                gameData.CurrentState = gameData.InPlayState;
            }
        }

        public void EnteringState()
        {

        }

        public void ExitingState()
        {

        }
    }
}
