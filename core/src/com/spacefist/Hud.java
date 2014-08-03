package com.spacefist;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.managers.PlayerManager;

/**
 * Draws information about the ongoing game and the player to the screen.
 */
public class Hud {
    private static final String ScoreFormat = "Score: {0} | Health: {1:P0} | Lives: {2}";
    private static final String controlsMsg = "Controls: WASD to move, SPACE to fire, Q to quit";

    private String scoreDisplay = "";

    private Vector2       controlsPosition;
    private GameData      gameData;
    private RoundData     roundData;
    private PlayerManager shipManager;
    private Vector2       scorePosition;
    private Rectangle     TopRect;
    private Rectangle     BottomRect;

    private Color color           = Color.YELLOW;
    private Color semiTransparent = new Color(255, 255, 255, .8f);

    public Hud(GameData gameData, PlayerManager shipManager) {
        this.gameData  = gameData;
        this.roundData = gameData.getRoundData();

        this.shipManager = shipManager;

        Rectangle resolution = gameData.getResolution();

        controlsPosition = new Vector2(
            (resolution.getWidth() * .5f) - (controlsMsg.length() * 5),
            resolution.getHeight() * .975f
        );

        TopRect = new Rectangle(
            0,
            0,
            gameData.getTextures().get("Hud").getWidth(),
            gameData.getTextures().get("Hud").getHeight()
        );

        BottomRect = new Rectangle(
            0,
            gameData.getResolution().getHeight() - gameData.getTextures().get("Hud").getHeight(),
            gameData.getTextures().get("Hud").getWidth(),
            gameData.getTextures().get("Hud").getHeight()
        );
    }

    public void Update() {
        /*
        TODO: Convert Hud.Update

        scoreDisplay = String.Format(
            ScoreFormat,
            roundData.Score,
            gameData.Ship.Health,
            roundData.Lives
        );

        scorePosition = new Vector2(
            (gameData.Resolution.Width * .5f) - (scoreDisplay.Length * 5),
            gameData.Resolution.Height * .001f
        );
        */
    }

    public void Draw() {
        /*
        TODO: Hud.Draw

        //Draw the top rectangle
        gameData.SpriteBatch.Draw(gameData.Textures["Hud"], TopRect, semiTransparent);

        // Write the score to the screen
        gameData.SpriteBatch.DrawString(
            gameData.Font,
            scoreDisplay,
            scorePosition,
            color,
            0f,
            Vector2.Zero,
            gameData.ScreenScale,
            SpriteEffects.None,
            0
        );

        // Draw the bottom rectangle
        gameData.SpriteBatch.Draw(gameData.Textures["Hud"], BottomRect, semiTransparent);

        // Write the controls message to the screen
        gameData.SpriteBatch.DrawString(
            gameData.Font,
            controlsMsg,
            controlsPosition,
            color,
            0f,
            Vector2.Zero,
            gameData.ScreenScale,
            SpriteEffects.None,
            0
        );
        */
    }
}
