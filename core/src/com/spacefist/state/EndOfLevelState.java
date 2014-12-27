package com.spacefist.state;

import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

public class EndOfLevelState implements GameState {
    private GameData gameData;

    public EndOfLevelState(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void loadContent() {
    }

    @Override
    public void draw() {
    }

    @Override
    public void update() {
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

    @Override
    public void enteringState() { }

    @Override
    public void exitingState() { }
}
