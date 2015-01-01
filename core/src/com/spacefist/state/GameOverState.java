package com.spacefist.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
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
        Music song = gameData.getSongs().get("GameOver");

        song.setLooping(true);
        song.play();
    }

    @Override
    public void update() {
        if(Gdx.input.isKeyPressed(Keys.ENTER) || Gdx.input.isKeyPressed(Keys.ESCAPE))
        {
            gameData.setCurrentState(gameData.getSplashScreenState());
        }
    }

    @Override
    public void draw() {
        Texture gameOver = gameData.getTextures().get("GameOver");


        // FIXME: Bug: GameOver message is drawn off-center

        // Draw the game over image
        gameData.getSpriteBatch().draw(gameOver, 0, 0);
    }

    @Override
    public void exitingState() {
        Music song = gameData.getSongs().get("GameOver");

        song.stop();
    }
}
