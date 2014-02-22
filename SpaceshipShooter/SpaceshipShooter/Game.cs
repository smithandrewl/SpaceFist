using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;
using SpaceshipShooter.Entities;
using SpaceshipShooter.State.Abstract;
using SpaceshipShooter.State;

namespace SpaceshipShooter
{
    public class Game : Microsoft.Xna.Framework.Game
    {
        GraphicsDeviceManager graphics;
        SpriteBatch           spriteBatch;
        GameState             currentState;

        public float       ScreenScale    { get; set; }
        public SpriteFont  Font           { get; set; }
        public Texture2D   Background     { get; set; }
        public Rectangle   BackgroundRect { get; set; }
        
        public InPlayState       InPlayState       { get; set; }
        public SplashScreenState SplashScreenState { get; set; }

        private const String SpriteFontAsset = @"Fonts\SpriteFont";
        private const String BackgroundAsset = @"Images\Backgrounds\Purple";

        public SpriteBatch SpriteBatch {
            get {
                return spriteBatch;
            }
        }

        public void SetState(GameState state)
        {
            currentState.ExitingState();
            state.EnteringState();

            currentState = state;
        }

        public Game()
        {
            SplashScreenState = new SplashScreenState(this);
            InPlayState       = new InPlayState(this);

            graphics = new GraphicsDeviceManager(this);
            
            currentState = SplashScreenState;
            
            Content.RootDirectory = "Content";
        }
       
        protected override void Initialize()
        {
            // Set the preferred size to 1280 x 800
            graphics.PreferredBackBufferWidth = 1366;
            graphics.PreferredBackBufferHeight = 768;
            graphics.ApplyChanges();
   
            base.Initialize();
        }

        protected override void LoadContent()
        {
            var viewport  = GraphicsDevice.Viewport;
            var titleSafe = GraphicsDevice.Viewport.TitleSafeArea;

            // Draw the background image with the dimensions of the screen
            BackgroundRect = new Rectangle(0, 0, titleSafe.Width, titleSafe.Height);
            
            var viewPort = GraphicsDevice.Viewport;

            // Written to run at 1680x1050
            // This is used to scale the sprites for other resolution
            ScreenScale = ((float)viewPort.Width) / 1680;
            
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);
            Font        = Content.Load<SpriteFont>(SpriteFontAsset);
            Background  = Content.Load<Texture2D>(BackgroundAsset);

            SplashScreenState.LoadContent();
            InPlayState.LoadContent();

            currentState.EnteringState();
        }

        protected override void UnloadContent()
        {
            
        }

        protected override void Update(GameTime gameTime)
        {
            currentState.Update(gameTime);
            base.Update(gameTime);
        }

        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(Color.CornflowerBlue);
            spriteBatch.Begin();

            currentState.Draw(gameTime);
            
            spriteBatch.End();

            base.Draw(gameTime);
        }
    }
}
