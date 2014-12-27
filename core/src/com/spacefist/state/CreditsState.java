package com.spacefist.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

/**
 * This state displays information about the developers of
 * the game.
 */
public class CreditsState implements GameState {
    private GameData gameData;
    private long     enteredAt;

    public CreditsState(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void loadContent() { }

    @Override
    public void draw() {
        Rectangle resolution = gameData.getResolution();

        int y       = ((int) ( resolution.getHeight() - (0.63 * resolution.getHeight())));
        int leftX   = (int) (0.1 * resolution.getWidth());
        int rightX  = (int) (0.7325 * resolution.getWidth());
        int centerX = (int) (0.5 * resolution.getWidth()) - 103;

        gameData.getSpriteBatch().draw(
            gameData.getTextures().get("Credits"),
            0,
            0,
            resolution.getWidth(),
            resolution.getHeight()
        );

        BitmapFont font = gameData.getFont();
        SpriteBatch spriteBatch = gameData.getSpriteBatch();

        font.draw(spriteBatch, "Dongcai Huang", leftX, y);
        font.draw(spriteBatch, "Programming", leftX, y - 30);
        font.draw(spriteBatch, "Art Selection", leftX, y - 45);

        font.draw(spriteBatch, "Tatsuya Takahashi", rightX, y);
        font.draw(spriteBatch, "Programming", rightX + 40, y - 30);

        font.draw(spriteBatch, "Andrew Smith", centerX, y);
        font.draw(spriteBatch, "Programming / AI", centerX - 30, y - 30);
        font.draw(spriteBatch, "Art Selection", centerX, y - 45);
        font.draw(spriteBatch, "Sound Selection", centerX - 10, y - 60);
    }

    @Override
    public void update() {

        if (TimeUtils.millis() - enteredAt > 300)
        {
            enteredAt = TimeUtils.millis();

            boolean mousePressed = Gdx.input.isTouched();
            boolean isEnterDown = Gdx.input.isKeyPressed(Keys.ENTER);
            boolean isEscapeDown = Gdx.input.isKeyPressed(Keys.ESCAPE);

            if (mousePressed || isEnterDown || isEscapeDown) {
                gameData.setCurrentState(gameData.getMenuState());
            }
        }

    }

    @Override
    public void enteringState() {
        enteredAt = TimeUtils.millis();
    }

    @Override
    public void exitingState() { }
}
