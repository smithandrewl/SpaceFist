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
    public class MenuState : GameState
    {
        private Game      game;
        private DateTime  enteredAt;
        private Texture2D background;
        private Texture2D menu;
        private Rectangle backgroundRect;
        private Rectangle menuRect;
        private Rectangle newGameRect;
        private Rectangle creditsRect;
        private Rectangle exitRect;

        public MenuState(Game game)
        {
            this.game  = game;
        }

        public void LoadContent()
        {
            var resolution = game.Resolution;

            backgroundRect = resolution;
            background     = game.BackgroundRed;
            
            menu           = game.Menu;

            menuRect = new Rectangle(
                (int) ((background.Width / 2f) - (menu.Width)), 
                (int)((background.Height / 2f) - (menu.Height)), 
                menu.Width, menu.Height
            );

            newGameRect = new Rectangle(menuRect.X + 8, menuRect.Y + 12, 149, 29);
            creditsRect = new Rectangle(menuRect.X + 8, menuRect.Y + 46, 149, 29);
            exitRect    = new Rectangle(menuRect.X + 8, menuRect.Y + 82, 149, 29);
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            game.SpriteBatch.Draw(background, backgroundRect, Color.White);
            game.SpriteBatch.Draw(menu, menuRect, Color.White);
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
                        game.CurrentState = game.InPlayState;
                    }

                    if (creditsRect.Contains(mousePos))
                    {
                        game.CurrentState = game.CreditsState;
                    }

                    if (exitRect.Contains(mousePos))
                    {
                        game.Exit();
                    }
                }
                else
                {
                    if (keys.IsKeyDown(Keys.Enter))
                    {
                        game.CurrentState = game.InPlayState;

                    }
                    else if(keys.IsKeyDown(Keys.Escape))
                    {
                        game.Exit();
                    }
                }
            }
        }

        public void EnteringState()
        {
            enteredAt           = DateTime.Now;
            game.IsMouseVisible = true;

            MediaPlayer.IsRepeating = true;
            MediaPlayer.Play(game.TitleScreenSong);
        }

        public void ExitingState()
        {
            MediaPlayer.Stop();
            game.IsMouseVisible = false;
        }
    }
}
