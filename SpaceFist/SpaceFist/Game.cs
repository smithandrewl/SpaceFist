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
    public class Game : Microsoft.Xna.Framework.Game, StateMachine<GameState>
    {
        GraphicsDeviceManager graphics;
        SpriteBatch           spriteBatch;
        GameState             currentState;

        public float       ScreenScale      { get; set; }

        // This Rectangle holds the dimensions of the screen
        public Rectangle BackgroundRect { get; set; }

        // The states of the game
        // these properties are used when switching states to avoid creating 
        // a new instance of the state (the old splash screen can be reused for example).
        public InPlayState       InPlayState       { get; set; }
        public SplashScreenState SplashScreenState { get; set; }
        public GameOverState     GameOverState     { get; set; }
        public MenuState         MenuState         { get; set; }
        public LogoState         LogoState         { get; set; }
        public CreditsState      CreditsState      { get; set; }

        public SpriteFont  Font { get; set; }

        public Dictionary<string, Texture2D>   Textures     { get; set; }
        public Dictionary<string, SoundEffect> SoundEffects { get; set; }
        public Dictionary<string, Song>        Songs        { get; set; }
        
        private const String SpriteFontAsset = @"Fonts\Raised";

        public GameData gameData { get; set; }

        public SpriteBatch SpriteBatch {
            get {
                return spriteBatch;
            }
        }

        public Game()
        {
            // Creates the game states when the game starts.
            // only one state is active at any given time.
            SplashScreenState = new SplashScreenState(this);
            InPlayState       = new InPlayState(this);
            GameOverState     = new GameOverState(this);
            MenuState         = new MenuState(this);
            LogoState         = new LogoState(this);
            CreditsState      = new CreditsState(this);

            graphics          = new GraphicsDeviceManager(this);
            
            // Set the first state of the game to be displaying the splash screen.
            currentState = LogoState;
           
            Content.RootDirectory = "Content";
            gameData = new GameData();

            Textures     = new Dictionary<string, Texture2D>();
            SoundEffects = new Dictionary<string, SoundEffect>();
            Songs        = new Dictionary<string, Song>();
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

                    Textures[asset] = Content.Load<Texture2D>("Images/" + dir + "/" + asset);
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

                Songs[asset] = Content.Load<Song>("Sound/Songs/" + asset);
            }
        }

        private void LoadSoundEffects()
        {
            String root = Content.RootDirectory;

            DirectoryInfo inf = new DirectoryInfo(root + "/Sound/SoundEffects");

            foreach(var file in inf.EnumerateFiles())
            {
                string asset = file.Name.Substring(0, file.Name.IndexOf('.'));

                SoundEffects[asset] = Content.Load<SoundEffect>("Sound/SoundEffects/" + asset);
            }
        }

        protected override void LoadContent()
        {
            this.Resolution = GraphicsDevice.Viewport.TitleSafeArea;

            // Draw the background image with the dimensions of the screen
            BackgroundRect = new Rectangle(0, 0, Resolution.Width, Resolution.Height);

            ScreenScale = .5f;
            
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);

            // ----------------------------- Load the games assets -----------
            Font = Content.Load<SpriteFont>(SpriteFontAsset);

            LoadTextures();
            LoadSongs();
            LoadSoundEffects();

            SplashScreenState.LoadContent();
            InPlayState.LoadContent();
            GameOverState.LoadContent();
            MenuState.LoadContent();
            LogoState.LoadContent();

            currentState.EnteringState();
        }

        protected override void Update(GameTime gameTime)
        {
            // Tell the current state to update itself
            currentState.Update();
            base.Update(gameTime);
        }

        protected override void Draw(GameTime gameTime)
        {
            GraphicsDevice.Clear(Color.Black);
            spriteBatch.Begin();

            // Tell the current state to draw itself
            currentState.Draw(gameTime);
            
            spriteBatch.End();

            base.Draw(gameTime);
        }

        // The current state of the game
        //
        // Setting the state first exits from the previous state
        // and then enters the new state.
        public GameState CurrentState
        {
            get
            {
                return currentState;
            }
            set
            {
                currentState.ExitingState();
                value.EnteringState();
                currentState = value;
            }
        }

        public Rectangle Resolution { get; set; }
    }
}
