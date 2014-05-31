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

        // ========================== The game assets ===============
        public SpriteFont  Font { get; set; }

        public Dictionary<string, Texture2D>   Textures     { get; set; }
        public Dictionary<string, SoundEffect> SoundEffects { get; set; }
        public Dictionary<string, Song>        Songs        { get; set; }
        // ==================== End game assets ====================

        //---------------- The paths to the game assets -----------------
        private const String SpriteFontAsset         = @"Fonts\Raised";

        private const String ExplosionSoundAsset     = @"Sound\Explosion";
        private const String ThumpSoundAsset         = @"Sound\Thump";
        private const String LaserSoundAsset         = @"Sound\Laser";

        private const String ExtraLifeSoundAsset    = @"Sound\ExtraLife";
        private const String HealthPickupSoundAsset = @"Sound\HealthPickup";
        private const String WeaponPickupSoundAsset = @"Sound\WeaponPickup";

        private const String TitleScreenSongAsset = @"Sound\TitleScreen";
        private const String InPlaySongAsset      = @"Sound\InPlay";
        private const String GameOverSongAsset    = @"Sound\GameOver";

        private const String EnemyExplosionAsset = @"Sound\EnemyExplosion";
        private const String PlayerDeathAsset    = @"Sound\PlayerDeath";
        private const String PlayerSpawnAsset    = @"Sound\PlayerSpawn";

        private const String EndOfGameSongAsset = @"Sound\EndOfGame";

        public GameData gameData { get; set; }

        // -------------------------------------------------------------

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

            // Sounds
            SoundEffects["Explosion"]    = Content.Load<SoundEffect>(ExplosionSoundAsset);
            SoundEffects["Thump"]        = Content.Load<SoundEffect>(ThumpSoundAsset);
            SoundEffects["Laser"]        = Content.Load<SoundEffect>(LaserSoundAsset);
            SoundEffects["ExtraLife"]    = Content.Load<SoundEffect>(ExtraLifeSoundAsset);
            SoundEffects["HealthPickup"] = Content.Load<SoundEffect>(HealthPickupSoundAsset);
            SoundEffects["WeaponPickup"] = Content.Load<SoundEffect>(WeaponPickupSoundAsset);
            SoundEffects["EnemyExplosion"]    = Content.Load<SoundEffect>(EnemyExplosionAsset);
            SoundEffects["PlayerDeath"]       = Content.Load<SoundEffect>(PlayerDeathAsset);
            SoundEffects["PlayerSpawn"]       = Content.Load<SoundEffect>(PlayerSpawnAsset);

             // Songs
            Songs["TitleScreen"] = Content.Load<Song>(TitleScreenSongAsset);
            Songs["InPlay"]      = Content.Load<Song>(InPlaySongAsset);
            Songs["GameOver"]    = Content.Load<Song>(GameOverSongAsset);
            Songs["EndOfGame"]   = Content.Load<Song>(EndOfGameSongAsset);

            SplashScreenState.LoadContent();
            InPlayState.LoadContent();
            GameOverState.LoadContent();
            MenuState.LoadContent();
            LogoState.LoadContent();

            currentState.EnteringState();
        }

        protected override void UnloadContent()
        {
            
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
