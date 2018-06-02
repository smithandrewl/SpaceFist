package com.spacefist.state

import com.spacefist.GameData
import com.spacefist.state.abst.GameState

class EndOfLevelState(private val gameData: GameData) : GameState {

    override fun loadContent() {}

    override fun draw() {}

    override fun update() {
        /*
            TODO: Convert EndOfLevelState.update

            var mouse = Mouse.GetState();

            if (mouse.LeftButton == ButtonState.Pressed || mouse.RightButton == ButtonState.Pressed)
            {
                gameData.LevelManager.loadLevel(gameData.Level.LevelId + 1);
                gameData.CurrentState = gameData.InPlayState;
            }
            */
    }

    override fun enteringState() {}

    override fun exitingState() {}
}
