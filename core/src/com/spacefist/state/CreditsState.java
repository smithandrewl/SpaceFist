package com.spacefist.state;

import com.badlogic.gdx.math.Rectangle;
import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

import java.util.Date;

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

        int y       = (int) (0.63 * resolution.getHeight());
        int leftX   = (int) (0.1 * resolution.getWidth());
        int rightX  = (int) (0.7325 * resolution.getWidth());
        int centerX = (int) (0.5 * resolution.getWidth()) - 103;

        /*
        TODO: Convert CreditsState.Draw

        gameData.getSpriteBatch().draw(
            gameData.getTextures().get("Credits"),
            gameData.Resolution,
            Color.White
        );

        gameData.getSpriteBatch().DrawString(
            gameData.Font,
            "Dongcai Huang",
            new Vector2(leftX, y),
            Color.White
        );

        gameData.getSpriteBatch().DrawString(
            gameData.Font,
            "Programming",
            new Vector2(leftX, y + 30),
            Color.PeachPuff
        );

        gameData.getSpriteBatch().DrawString(
            gameData.Font,
            "Art Selection",
            new Vector2(leftX, y + 45),
            Color.PeachPuff
        );

        gameData.getSpriteBatch().DrawString(
            gameData.Font,
            "Tatsuya Takahashi",
            new Vector2(rightX, y),
            Color.White
        );

        gameData.getSpriteBatch().DrawString(
            gameData.Font,
            "Programming",
            new Vector2(rightX + 40, y + 30),
            Color.PeachPuff
        );

        gameData.getSpriteBatch().DrawString(
            gameData.Font,
            "Andrew Smith",
            new Vector2(centerX, y),
            Color.White
        );

        gameData.getSpriteBatch().DrawString(
            gameData.Font,
            "Programming / AI",
            new Vector2(centerX - 30, y + 30),
            Color.PeachPuff
        );

        gameData.getSpriteBatch().DrawString(
            gameData.Font,
            "Art Selection",
            new Vector2(centerX, y + 45),
            Color.PeachPuff
        );

        gameData.getSpriteBatch().DrawString(
            gameData.Font,
            "Sound Selection",
            new Vector2(centerX - 10, y + 60),
            Color.PeachPuff
        );
        */
    }

    public void update() {
        /*
        TODO: Convert CreditsState.Update

        if (DateTime.Now.Subtract(enteredAt).Milliseconds > 300)
        {
            enteredAt = DateTime.Now;

            MouseState    mouse = Mouse.GetState();
            KeyboardState keys  = Keyboard.GetState();

            if (mouse.LeftButton == ButtonState.Pressed ||
                keys.IsKeyDown(Keys.Enter)              ||
                keys.IsKeyDown(Keys.Escape))
            {
                gameData.CurrentState = gameData.MenuState;
            }
        }
        */
    }

    @Override
    public void enteringState() {
        /*
        TODO: CreditsState.EnteringState()
        enteredAt           = DateTime.Now;
        */
    }

    @Override
    public void exitingState() { }
}
