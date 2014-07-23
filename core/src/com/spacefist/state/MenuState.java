package com.spacefist.state;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
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
            // TODO: Convert MenuState.Draw()

            /*
            gameData.getSpriteBatch().draw(background, backgroundRect, Color.WHITE);
            gameData.getSpriteBatch().draw(menu, menuRect, Color.WHITE);
            */
        }

        public void Update()
        {
            // TODO: Convert Input handling code in MenuState.Update

            /*
            MouseState    mouse = Mouse.GetState();
            KeyboardState keys  = Keyboard.GetState();

            Point mousePos = new Point(mouse.X, mouse.Y);

            if (DateTime.Now.Subtract(enteredAt).Milliseconds > 300)
            {
                if (mouse.LeftButton == ButtonState.Pressed)
                {
                    if (newGameRect.contains(mousePos))
                    {
                        gameData.CurrentState = gameData.InPlayState;
                    }

                    if (creditsRect.Contains(mousePos))
                    {
                        gameData.CurrentState = gameData.CreditsState;
                    }

                    if (exitRect.Contains(mousePos))
                    {
                        System.Environment.Exit(0);
                    }
                }
                else
                {
                    if (keys.IsKeyDown(Keys.Enter))
                    {
                        gameData.CurrentState = gameData.InPlayState;

                    }
                    else if(keys.IsKeyDown(Keys.Escape))
                    {
                        System.Environment.Exit(0);
                    }
                }
            }
            */
        }

        public void EnteringState()
        {
            // TODO: Convert MenuState.EnteringState
            // enteredAt           = DateTime.Now;

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
