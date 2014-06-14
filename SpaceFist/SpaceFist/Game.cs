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
using SpaceFist.Entities;
using SpaceFist.State.Abstract;
using SpaceFist.State;
using System.IO;

namespace SpaceFist
{
    public class Game : Microsoft.Xna.Framework.Game
    {
        GraphicsDeviceManager graphics;
        private const String SpriteFontAsset = @"Fonts\Raised";

        public GameData GameData { get; set; }

        public Game()
        {

            GameData = new GameData(this);

            // Creates the game states when the game starts.
            // only one state is active at any given time.
            GameData. SplashScreenState = new SplashScreenState(GameData);
            GameData.InPlayState       = new InPlayState(GameData);
            GameData.GameOverState     = new GameOverState(GameData);
            GameData.MenuState         = new MenuState(GameData);
            GameData.LogoState         = new LogoState(GameData);
            GameData.CreditsState      = new CreditsState(GameData);
          
            graphics          = new GraphicsDeviceManager(this);
            
            // Set the first state of the game to be displaying the splash screen.
            GameData.CurrentState = GameData.LogoState;
            GameData.Content      = Content;
            Content.RootDirectory = "Content";
        }
       
        
        protected override void Initialize()
        {
            graphics.PreferredBackBufferWidth  = 1366;
            graphics.PreferredBackBufferHeight = 768;
            graphics.ApplyChanges();
   
            base.Initialize();
        }

        private void LoadTextures()
        {
            String root = Content.RootDirectory;

            DirectoryInfo inf = new DirectoryInfo(root + "/Images");

            foreach (var dir in inf.EnumerateDirectories())
            {
                foreach (var file in dir.EnumerateFiles())
                {
                    string asset = file.Name.Substring(0, file.Name.IndexOf('.'));

                     GameData.Textures[asset] = Content.Load<Texture2D>("Images/" + dir + "/" + asset);
                }
            }
        }

        private void LoadSongs()
        {
            String root = Content.RootDirectory;

            DirectoryInfo inf = new DirectoryInfo(root + "/Sound/Songs");

            foreach (var file in inf.EnumerateFiles())
            {
                string asset = file.Name.Substring(0, file.Name.IndexOf('.'));

                GameData.Songs[asset] = Content.Load<Song>("Sound/Songs/" + asset);
            }
        }

        private void LoadSoundEffects()
        {
            String root = Content.RootDirectory;

            DirectoryInfo inf = new DirectoryInfo(root + "/Sound/SoundEffects");

            foreach(var file in inf.EnumerateFiles())
            {
                string asset = file.Name.Substring(0, file.Name.IndexOf('.'));

                GameData.SoundEffects[asset] = Content.Load<SoundEffect>("Sound/SoundEffects/" + asset);
            }
        }

        protected override void LoadContent()
        {
            GameData.Resolution = GraphicsDevice.Viewport.TitleSafeArea;

            GameData.ScreenScale = .5f;
            
            // Create a new SpriteBatch, which can be used to draw textures.
            GameData.SpriteBatch = new SpriteBatch(GraphicsDevice);

            // ----------------------------- Load the games assets -----------
            GameData.Font = Content.Load<SpriteFont>(SpriteFontAsset);

            LoadTextures();
            LoadSongs();
            LoadSoundEffects();

            GameData.SplashScreenState.LoadContent();
            GameData.InPlayState.LoadContent();
            GameData.GameOverState.LoadContent();
            GameData.MenuState.LoadContent();
            GameData.LogoState.LoadContent();


            GameData.CurrentState.EnteringState();
        }

        protected override void Update(GameTime gameTime)
        {
            // Tell the current state to update itself
            GameData.CurrentState.Update();
            base.Update(gameTime);
        }

        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(Color.Black);
            GameData.SpriteBatch.Begin();

            // Tell the current state to draw itself
            GameData.CurrentState.Draw(gameTime);
            
            GameData.SpriteBatch.End();

            base.Draw(gameTime);
        }
    }
}
