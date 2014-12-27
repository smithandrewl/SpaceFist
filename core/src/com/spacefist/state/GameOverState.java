package com.spacefist.state;

import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

/**
 * GameOverState draws the game over message until the player
 * hits enter.  When the player hits enter, the player is taken to to the
 * main menu.
 */
public class GameOverState implements GameState {
    private GameData gameData;

    public GameOverState(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void loadContent() { }

    @Override
    public void enteringState() {
            /*
            TODO: Convert GameOverState.EnteringState()

            MediaPlayer.IsRepeating = true;
            MediaPlayer.Play(gameData.Songs["GameOver"]);
            */
    }

    @Override
    public void update() {
            /*
            TODO: Convert GameOverState.Update

            KeyboardState keys = Keyboard.GetState();

            if (keys.IsKeyDown(Keys.Enter) || keys.IsKeyDown(Keys.Escape))
            {
                gameData.CurrentState = gameData.SplashScreenState;
            }
            */
    }

    @Override
    public void draw() {
            /*
            TODO: Convert GameOverState.Draw

            // Draw the game over image
            gameData.SpriteBatch.Draw(gameData.Textures["GameOver"], gameData.Resolution, Color.White);
            gameData.SpriteBatch.Draw(gameData.Textures["GameOver"], gameData.Resolution, Color.White);
            */
    }

    @Override
    public void exitingState() {
            /*
            TODO: Convert GameOverState.ExitingState()

            MediaPlayer.Stop();
            */
    }
}
