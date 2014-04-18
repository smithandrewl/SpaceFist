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

        // ========================== The game assets ===============
        // -------------- ----------- Font(s) -----------------------
        public SpriteFont  Font             { get; set; }

        // -------------------------- Textures ----------------------
        public Texture2D   Background       { get; set; }
        public Texture2D   LaserTexture     { get; set; }
        public Texture2D   BlockTexture     { get; set; }
        public Texture2D   GameOverTexture  { get; set; }
        public Texture2D   ShipSheet        { get; set; }
        public Texture2D   EnemyTexture     { get; set; }
        public Texture2D   ExplosionTexture { get; set; }
        public Texture2D   HudTexture       { get; set; } // The image of the
                                                          // purple transparent window where the score is drawn.
        
        public Texture2D SampleProjectileTexture { get; set; }
        public Texture2D HealthPickupTexture     { get; set; }
        public Texture2D ExtraLifePickupTexture  { get; set; }
        public Texture2D WeaponPickupTexture     { get; set; }

        public Texture2D LevelStartTexture { get; set; }
        public Texture2D LevelEndTexture { get; set; }


        // ----------------------- Sounds -----------------------
        public SoundEffect LaserSound      { get; set; }
        public SoundEffect ExplosionSound  { get; set; }
        public SoundEffect ThumpSound      { get; set; }

        public SoundEffect ExtraLifeSound    { get; set; }
        public SoundEffect HealthPickupSound { get; set; }
        public SoundEffect WeaponPickupSound { get; set; }

        public SoundEffect EnemyExplosion { get; set; }
        public SoundEffect PlayerDeath    { get; set; }
        public SoundEffect PlayerSpawn    { get; set; }

        public Song TitleScreenSong { get; set; }
        public Song InPlaySong      { get; set; }
        public Song GameOverSong    { get; set; }

        // ==================== End game assets ====================

        //---------------- The paths to the game assets -----------------
        private const String SpriteFontAsset         = @"Fonts\SpriteFont";
        private const String LaserImageAsset         = @"Images\Sprites\Laser";
        private const String BackgroundAsset         = @"Images\Backgrounds\Purple";
        private const String GameOverAsset           = @"Images\Backgrounds\GameOver";
        private const String HealthPickupAsset       = @"Images\Sprites\HealthPickup";
        private const String ExtraLifePickupAsset    = @"Images\Sprites\ExtraLifePickup";
        private const String WeaponPickupAsset       = @"Images\Sprites\WeaponPickup";
        private const String BlockImageAsset         = @"Images\Sprites\Block";
        private const String ShipSheetAsset          = @"Images\Sprites\ShipSheet";
        private const String EnemyAsset              = @"Images\Sprites\Enemy";
        private const String ExplosionAnimationAsset = @"Images\Animations\explosion";
        private const String HUDAsset                = @"Images\UI\Hud";
        private const String SampleWeaponAsset       = @"Images\Sprites\SampleProjectile";
        private const String SamplePickupAsset       = @"Images\Sprites\SamplePickup";
        private const String ExplosionSoundAsset     = @"Sound\explosion";
        private const String ThumpSoundAsset         = @"Sound\thump";
        private const String LaserSoundAsset         = @"Sound\laser";
        private const String LevelStartAsset         = @"Images\Backgrounds\LevelStart";
        private const String LevelEndAsset           = @"Images\Backgrounds\LevelEnd";

        private const String ExtraLifeSoundAsset    = @"Sound\ExtraLife";
        private const String HealthPickupSoundAsset = @"Sound\HealthPickup";
        private const String WeaponPickupSoundAsset = @"Sound\WeaponPickup";

        private const String TitleScreenSongAsset = @"Sound\Space 1990-B";
        private const String InPlaySongAsset      = @"Sound\Blown Away";
        private const String GameOverSongAsset    = @"Sound\Local Forecast";

        private const String EnemyExplosionAsset = @"Sound\EnemyExplosion";
        private const String PlayerDeathAsset    = @"Sound\PlayerDeath";
        private const String PlayerSpawnAsset         = @"Sound\Spawn";

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

            graphics = new GraphicsDeviceManager(this);
            
            // Set the first state of the game to be displaying the splash screen.
            currentState = SplashScreenState;
            
            Content.RootDirectory = "Content";
            gameData = new GameData();
        }
       
        protected override void Initialize()
        {
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

            ScreenScale = .60f;
            
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);

            // ----------------------------- Load the games assets -----------
            Font = Content.Load<SpriteFont>(SpriteFontAsset);
            
            // Textures
            Background         = Content.Load<Texture2D>(BackgroundAsset);
            GameOverTexture    = Content.Load<Texture2D>(GameOverAsset);
            LaserTexture       = Content.Load<Texture2D>(LaserImageAsset);
            BlockTexture       = Content.Load<Texture2D>(BlockImageAsset);
            ShipSheet          = Content.Load<Texture2D>(ShipSheetAsset);
            EnemyTexture       = Content.Load<Texture2D>(EnemyAsset);
            ExplosionTexture   = Content.Load<Texture2D>(ExplosionAnimationAsset);
            HudTexture         = Content.Load<Texture2D>(HUDAsset);

            HealthPickupTexture    = Content.Load<Texture2D>(HealthPickupAsset);
            ExtraLifePickupTexture = Content.Load<Texture2D>(ExtraLifePickupAsset);
            WeaponPickupTexture    = Content.Load<Texture2D>(WeaponPickupAsset);

            SampleProjectileTexture = Content.Load<Texture2D>(SampleWeaponAsset);

            LevelStartTexture = Content.Load<Texture2D>(LevelStartAsset);
            LevelEndTexture = Content.Load<Texture2D>(LevelEndAsset);

            // Sounds
            ExplosionSound    = Content.Load<SoundEffect>(ExplosionSoundAsset);
            ThumpSound        = Content.Load<SoundEffect>(ThumpSoundAsset);
            LaserSound        = Content.Load<SoundEffect>(LaserSoundAsset);
            ExtraLifeSound    = Content.Load<SoundEffect>(ExtraLifeSoundAsset);
            HealthPickupSound = Content.Load<SoundEffect>(HealthPickupSoundAsset);
            WeaponPickupSound = Content.Load<SoundEffect>(WeaponPickupSoundAsset);
            EnemyExplosion    = Content.Load<SoundEffect>(EnemyExplosionAsset);
            PlayerDeath       = Content.Load<SoundEffect>(PlayerDeathAsset);
            PlayerSpawn       = Content.Load<SoundEffect>(PlayerSpawnAsset);

            TitleScreenSong = Content.Load<Song>(TitleScreenSongAsset);
            InPlaySong      = Content.Load<Song>(InPlaySongAsset);
            GameOverSong    = Content.Load<Song>(GameOverSongAsset);

            SplashScreenState.LoadContent();
            InPlayState.LoadContent();
            GameOverState.LoadContent();

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
            GraphicsDevice.Clear(Color.CornflowerBlue);
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
    }
}
