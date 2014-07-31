package com.spacefist.state;

import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

public class EndOfLevelState implements GameState {
    private GameData gameData;

    public EndOfLevelState(GameData gameData) {
        this.gameData = gameData;
    }

    public void LoadContent() {
    }

    public void Draw() {
    }

    public void Update() {
            /*
            TODO: Convert EndOfLevelState.Update

            var mouse = Mouse.GetState();

            if (mouse.LeftButton == ButtonState.Pressed || mouse.RightButton == ButtonState.Pressed)
            {
                gameData.LevelManager.LoadLevel(gameData.Level.LevelId + 1);
                gameData.CurrentState = gameData.InPlayState;
            }
            */
    }

    public void EnteringState() { }

    public void ExitingState() { }
}
