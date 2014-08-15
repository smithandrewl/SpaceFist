package com.spacefist.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

import java.util.Date;
import java.util.HashMap;

/**
 * This state displays the main menu and handles input to
 * switch to several other states.
 */
public class MenuState implements GameState {
    private GameData  gameData;
    private long      enteredAt;
    private Texture   background;
    private Texture   menu;
    private Rectangle backgroundRect;
    private Rectangle menuRect;
    private Rectangle newGameRect;
    private Rectangle creditsRect;
    private Rectangle exitRect;

    public MenuState(GameData gameData) {
        this.gameData = gameData;
    }

    public void loadContent() {

        backgroundRect = gameData.getResolution();
        HashMap<String, Texture> textures = gameData.getTextures();

        background     = textures.get("BackgroundRed");
        menu           = textures.get("Menu");

        menuRect = new Rectangle(
                (int) ((background.getWidth()  / 2.0f) - (menu.getWidth())),
                (int) ((background.getHeight() / 2.0f) - (menu.getHeight())),
                menu.getWidth(), menu.getHeight()
        );

        // Calculate and set rectangles for each button since the buttons
        // are part of a single image.
        newGameRect = new Rectangle(menuRect.x + 8, menuRect.y + 12, 149, 29);
        creditsRect = new Rectangle(menuRect.x + 8, menuRect.y + 46, 149, 29);
        exitRect    = new Rectangle(menuRect.x + 8, menuRect.y + 82, 149, 29);
    }

    public void draw() {
        SpriteBatch spriteBatch = gameData.getSpriteBatch();

        spriteBatch.draw(
            background,
            backgroundRect.x,
            backgroundRect.y,
            background.getWidth(),
            backgroundRect.getHeight()
        );

        spriteBatch.draw(
            menu,
            menuRect.x,
            menuRect.y,
            menuRect.getWidth(),
            menuRect.getHeight()
        );

        BitmapFont font = gameData.getFont();
    }

    public void update() {
        // TODO: Convert Input handling code in MenuState.update

        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());

        if (TimeUtils.millis() - enteredAt > 300) {
            if (Gdx.input.isTouched()) {
                if (newGameRect.contains(mousePos)) {
                    // TODO: convert InPlayState
                    //gameData.setCurrentState(gameData.getInPlayState());
                }

                if (creditsRect.contains(mousePos)) {
                    // TODO: convert CreditsState
                    // gameData.setCurrentState(gameData.getCreditsState());
                }

                if (exitRect.contains(mousePos)) {
                    Gdx.app.exit();
                }
            } else {
                if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                    // TODO: convert InPlayState
                    // gameData.setCurrentState(gameData.getInPlayState());

                } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                    Gdx.app.exit();
                }
            }
        }
    }

    public void enteringState() {
        // TODO: Convert MenuState.enteringState
        enteredAt = TimeUtils.millis();

        // gameData.IsMouseVisible = true;
        // MediaPlayer.IsRepeating = true;
        // MediaPlayer.Play(gameData.Songs["TitleScreen"]);

        // gameData.LevelManager.Init();
        // gameData.LevelManager.LoadLevel(1);
    }

    public void exitingState() {
        // TODO: Convert MenuState.exitingState
        // TODO: Get mouse visibility working
        // gameData.IsMouseVisible = false;

        // TODO: Convert audio to libGDX
        //MediaPlayer.Stop();
    }
}
