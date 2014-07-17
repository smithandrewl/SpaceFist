using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State
{
    /// <summary>
    /// This state displays the main menu and handles input to 
    /// switch to several other states.
    /// </summary>
    public class MenuState : GameState
    {
        private GameData  gameData;
        private DateTime  enteredAt;
        private Texture2D background;
        private Texture2D menu;
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
            var resolution = gameData.Resolution;

            backgroundRect = resolution;
            background     = gameData.Textures["BackgroundRed"];
            
            menu           = gameData.Textures["Menu"];

            menuRect = new Rectangle(
                (int) ((background.Width / 2f) - (menu.Width)), 
                (int)((background.Height / 2f) - (menu.Height)), 
                menu.Width, menu.Height
            );

            // Calculate and set rectangles for each button since the buttons
            // are part of a single image.
            newGameRect = new Rectangle(menuRect.X + 8, menuRect.Y + 12, 149, 29);
            creditsRect = new Rectangle(menuRect.X + 8, menuRect.Y + 46, 149, 29);
            exitRect    = new Rectangle(menuRect.X + 8, menuRect.Y + 82, 149, 29);
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            gameData.SpriteBatch.Draw(background, backgroundRect, Color.White);
            gameData.SpriteBatch.Draw(menu, menuRect, Color.White);
        }

        public void Update()
        {
            MouseState    mouse = Mouse.GetState();
            KeyboardState keys  = Keyboard.GetState();

            Point mousePos = new Point(mouse.X, mouse.Y);

            if (DateTime.Now.Subtract(enteredAt).Milliseconds > 300)
            {
                if (mouse.LeftButton == ButtonState.Pressed)
                {
                    if (newGameRect.Contains(mousePos))
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
        }

        public void EnteringState()
        {
            enteredAt           = DateTime.Now;

            gameData.IsMouseVisible = true;
            MediaPlayer.IsRepeating = true;
            MediaPlayer.Play(gameData.Songs["TitleScreen"]);

            gameData.LevelManager.Init();
            gameData.LevelManager.LoadLevel(1);
        }

        public void ExitingState()
        {
            gameData.IsMouseVisible = false;
            MediaPlayer.Stop();
        }
    }
}