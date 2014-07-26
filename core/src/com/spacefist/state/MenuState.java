package com.spacefist.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

import java.util.Date;

/// <summary>
/// This state displays the main menu and handles input to
/// switch to several other states.
/// </summary>
public class MenuState implements GameState
{
    private GameData  gameData;
    private Date      enteredAt;
    private Texture   background;
    private Texture   menu;
    private Rectangle backgroundRect;
    private Rectangle menuRect;
    private Rectangle newGameRect;
    private Rectangle creditsRect;
    private Rectangle exitRect;

    public MenuState(GameData gameData)
    {
        this.gameData  = gameData;
    }

    public void LoadContent()
    {
        Rectangle resolution = gameData.getResolution();

        backgroundRect = resolution;
        background     = gameData.getTextures().get("BackgroundRed");
        menu           = gameData.getTextures().get("Menu");

        menuRect = new Rectangle(
                (int) ((background.getWidth() / 2f) - (menu.getWidth())),
                (int)((background.getHeight() / 2f) - (menu.getHeight())),
                menu.getWidth(), menu.getHeight()
        );

        // Calculate and set rectangles for each button since the buttons
        // are part of a single image.
        newGameRect = new Rectangle(menuRect.x + 8, menuRect.y + 12, 149, 29);
        creditsRect = new Rectangle(menuRect.x + 8, menuRect.y + 46, 149, 29);
        exitRect    = new Rectangle(menuRect.x + 8, menuRect.y + 82, 149, 29);
    }

    public void Draw()
    {
        gameData.getSpriteBatch().draw(background, backgroundRect.x, backgroundRect.y, background.getWidth(), backgroundRect.getHeight());
        gameData.getSpriteBatch().draw(menu, menuRect.x, menuRect.y, menuRect.getWidth(), menuRect.getHeight());

    }

    public void Update()
    {
        // TODO: Convert Input handling code in MenuState.Update


        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());

        if (new Date().getTime() - enteredAt.getTime() > 300)
        {
            if (Gdx.input.isTouched())
            {
                if (newGameRect.contains(mousePos))
                {
                    // TODO: convert InPlayState
                    //gameData.setCurrentState(gameData.getInPlayState());
                }

                if (creditsRect.contains(mousePos))
                {
                    // TODO: convert CreditsState
                    // gameData.setCurrentState(gameData.getCreditsState());
                }

                if (exitRect.contains(mousePos))
                {
                    Gdx.app.exit();
                }
            }
            else
            {
                if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
                {
                    // TODO: convert InPlayState
                    // gameData.setCurrentState(gameData.getInPlayState());

                }
                else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                {
                    Gdx.app.exit();
                }
            }
        }
    }

    public void EnteringState()
    {
        // TODO: Convert MenuState.EnteringState
        enteredAt = new Date();

        // gameData.IsMouseVisible = true;
        // MediaPlayer.IsRepeating = true;
        // MediaPlayer.Play(gameData.Songs["TitleScreen"]);

        // gameData.LevelManager.Init();
        // gameData.LevelManager.LoadLevel(1);
    }

    public void ExitingState()
    {
        // TODO: Convert MenuState.ExitingState
        // TODO: Get mouse visibility working
        // gameData.IsMouseVisible = false;

        // TODO: Convert audio to libGDX
        //MediaPlayer.Stop();
    }
}
