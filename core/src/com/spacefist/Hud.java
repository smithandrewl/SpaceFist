package com.spacefist;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.managers.PlayerManager;

/**
 * Draws information about the ongoing game and the player to the screen.
 */
public class Hud {
    private static final String SCORE_FORMAT = "Score: %d | Health: %d%% | Lives: %d";
    private static final String CONTROLS_MSG = "Controls: WASD to move, SPACE to fire, Q to quit";

    private String scoreDisplay = "";

    private Vector2   controlsPosition;
    private GameData  gameData;
    private RoundData roundData;
    private Vector2   scorePosition;
    private Rectangle BottomRect;
    private Rectangle TopRect;

    @SuppressWarnings("UnnecessaryThis")
    public Hud(GameData gameData, PlayerManager shipManager) {
        this.gameData  = gameData;
        this.roundData = gameData.getRoundData();

        Rectangle resolution = gameData.getResolution();

        controlsPosition = new Vector2(
            (resolution.getWidth() * .5f) - (CONTROLS_MSG.length() * 4.5f),
            resolution.getHeight() * .025f
        );

        BottomRect = new Rectangle(
            0,
            0,
            gameData.getTextures().get("Hud").getWidth(),
            gameData.getTextures().get("Hud").getHeight()
        );

        TopRect = new Rectangle(
            0,
            gameData.getResolution().getHeight() - gameData.getTextures().get("Hud").getHeight(),
            gameData.getTextures().get("Hud").getWidth(),
            gameData.getTextures().get("Hud").getHeight()
        );
    }

    public void Update() {

        scoreDisplay = String.format(
                SCORE_FORMAT,
                roundData.getScore(),
                (int) (gameData.getShip().getHealth() * 100),
                roundData.getLives()
        );

        scorePosition = new Vector2(
            (gameData.getResolution().getWidth() * .5f) - (scoreDisplay.length() * 5),
            gameData.getResolution().getHeight() * .994f
        );

    }

    public void Draw() {
        BitmapFont font = gameData.getFont();

        //Draw the top rectangle
        gameData.getSpriteBatch().draw(gameData.getTextures().get("Hud"), TopRect.x, TopRect.y);

        // Draw the score
        font.draw(gameData.getSpriteBatch(), scoreDisplay, scorePosition.x, scorePosition.y);

        // Draw the bottom rectangle
        gameData.getSpriteBatch().draw(gameData.getTextures().get("Hud"), BottomRect.x, BottomRect.y);


        // Write the controls message to the screen
        font.draw(gameData.getSpriteBatch(), CONTROLS_MSG, controlsPosition.x, controlsPosition.y);
    }
}
