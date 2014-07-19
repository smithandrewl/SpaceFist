package com.spacefist.state;

import com.badlogic.gdx.graphics.Texture;
import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;
import java.util.Date;

/**
 * This state displays the team logo before switching
 * to the splash screen.
 */
public class LogoState implements GameState {
    Texture background;
    Date    enteredAt;

    private static final int loadTime = 3;

    private GameData gameData;

    public LogoState(GameData gameData)
    {
        this.gameData = gameData;
    }

    public void LoadContent()
    {
        background = gameData.getTextures().get("Logo");
    }

    public void Draw()
    {
        float height = gameData.getResolution().getHeight();
        float width = gameData.getResolution().getWidth();

        gameData.getSpriteBatch().draw(background, 0, 0, width, height);
        /*
        gameData.SpriteBatch.Draw(
                background,
                gameData.Resolution,
                Color.White
        );
        */
    }

    public void Update()
    {
        /*
        KeyboardState keys = Keyboard.GetState();

        if (DateTime.Now.Subtract(enteredAt).Seconds > loadTime ||
                keys.IsKeyDown(Keys.Enter)                          ||
                keys.IsKeyDown(Keys.Escape))
        {
            gameData.CurrentState = gameData.SplashScreenState;
        }
        */
    }

    public void EnteringState()
    {
        enteredAt = new Date();
    }

    public void ExitingState()
    {
    }
}
