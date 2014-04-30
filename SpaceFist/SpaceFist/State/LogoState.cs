﻿using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Input;

namespace SpaceFist.State
{
    public class LogoState : GameState
    {
        Texture2D background;
        DateTime  enteredAt;

        private const int loadTime = 3;
        
        private Game game;

        public LogoState(Game game)
        {
            this.game = game;
        }

        public void LoadContent()
        {
            background = game.LogoTexture;
        }

        public void Draw(Microsoft.Xna.Framework.GameTime time)
        {
            game.SpriteBatch.Draw(
                background, 
                game.GraphicsDevice.Viewport.Bounds, 
                Color.White
            );
        }

        public void Update()
        {
            KeyboardState keys = Keyboard.GetState();

            if (DateTime.Now.Subtract(enteredAt).Seconds > loadTime || 
                keys.IsKeyDown(Keys.Enter)                          || 
                keys.IsKeyDown(Keys.Escape))
            {
                game.CurrentState = game.SplashScreenState;
            }
        }

        public void EnteringState()
        {
            enteredAt = DateTime.Now;
        }

        public void ExitingState()
        {
        }
    }
}
