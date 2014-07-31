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

    public void LoadContent() { }

    public void EnteringState() {
            /*
            TODO: Convert GameOverState.EnteringState()

            MediaPlayer.IsRepeating = true;
            MediaPlayer.Play(gameData.Songs["GameOver"]);
            */
    }

    public void Update() {
            /*
            TODO: Convert GameOverState.Update

            KeyboardState keys = Keyboard.GetState();

            if (keys.IsKeyDown(Keys.Enter) || keys.IsKeyDown(Keys.Escape))
            {
                gameData.CurrentState = gameData.SplashScreenState;
            }
            */
    }

    public void Draw() {
            /*
            TODO: Convert GameOverState.Draw

            // Draw the game over image
            gameData.SpriteBatch.Draw(gameData.Textures["GameOver"], gameData.Resolution, Color.White);
            gameData.SpriteBatch.Draw(gameData.Textures["GameOver"], gameData.Resolution, Color.White);
            */
    }

    public void ExitingState() {
            /*
            TODO: Convert GameOverState.ExitingState()

            MediaPlayer.Stop();
            */
    }
}
