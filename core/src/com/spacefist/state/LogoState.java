package com.spacefist.state;

import com.badlogic.gdx.graphics.Texture;
import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

/// <summary>
/// This state displays the team logo before switching
/// to the splash screen.
/// </summary>
public class LogoState implements GameState {
    Texture background;
    //DateTime  enteredAt;

    private static final int loadTime = 3;

    private GameData gameData;

    public LogoState(GameData gameData)
    {
        this.gameData = gameData;
    }

    public void LoadContent()
    {
        //background = gameData.Textures["Logo"];
    }

    public void Draw()
    {
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
        //enteredAt = DateTime.Now;
    }

    public void ExitingState()
    {
    }
}
