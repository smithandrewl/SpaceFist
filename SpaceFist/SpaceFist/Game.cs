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
        public InPlayState InPlayState { get; set; }
        public SplashScreenState SplashScreenState { get; set; }
        public GameOverState GameOverState { get; set; }

        // ========================== The game assets ===============
        // -------------- ----------- Font(s) -----------------------
        public SpriteFont  Font             { get; set; }

        // -------------------------- Textures ----------------------
        public Texture2D   Background       { get; set; }
        public Texture2D   LaserTexture     { get; set; }
        public Texture2D   BlockTexture     { get; set; }
        public Texture2D   GameOverTexture  { get; set; }
        public Texture2D   ShipSheet        { get; set; }
        public Texture2D   EnemySheet       { get; set; }
        public Texture2D   ExplosionTexture { get; set; }
        public Texture2D   HudTexture       { get; set; } // The image of the
                                                          // purple transparent window where the score is drawn.
        public Texture2D SamplePickup { get; set; }
        public Texture2D SampleProjectileTexture { get; set; }
        // ----------------------- Sounds -----------------------
        public SoundEffect LaserSound      { get; set; }
        public SoundEffect ExplosionSound  { get; set; }
        public SoundEffect ThumpSound      { get; set; }
        // ==================== End game assets ====================

        //---------------- The paths to the game assets -----------------
        private const String SpriteFontAsset         = @"Fonts\SpriteFont";
        private const String LaserImageAsset         = @"Images\Sprites\Laser";
        private const String BackgroundAsset         = @"Images\Backgrounds\Purple";
        private const String GameOverAsset           = @"Images\Backgrounds\GameOver";
        private const String BlockImageAsset         = @"Images\Sprites\Block";
        private const String ShipSheetAsset          = @"Images\Sprites\ShipSheet";
        private const String EnemeySheetAsset        = @"Images\Sprites\ShipSheet";
        private const String ExplosionAnimationAsset = @"Images\Animations\explosion";
        private const String HUDAsset                = @"Images\UI\Hud";
        private const String SampleWeaponAsset = @"Images\Sprites\SampleProjectile";
        private const String SamplePickupAsset = @"Images\Sprites\SamplePickup";

        private const String ExplosionSoundAsset     = @"Sound\explosion";
        private const String ThumpSoundAsset         = @"Sound\thump";
        private const String LaserSoundAsset         = @"Sound\laser";
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

            var viewPort = GraphicsDevice.Viewport;

            ScreenScale = .60f;
            
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch      = new SpriteBatch(GraphicsDevice);

            // ----------------------------- Load the games assets -----------
            Font             = Content.Load<SpriteFont>(SpriteFontAsset);
            
            // Textures
            Background       = Content.Load<Texture2D>(BackgroundAsset);
            GameOverTexture  = Content.Load<Texture2D>(GameOverAsset);
            LaserTexture     = Content.Load<Texture2D>(LaserImageAsset);
            BlockTexture     = Content.Load<Texture2D>(BlockImageAsset);
            ShipSheet        = Content.Load<Texture2D>(ShipSheetAsset);
            EnemySheet       = Content.Load<Texture2D>(EnemeySheetAsset);
            ExplosionTexture = Content.Load<Texture2D>(ExplosionAnimationAsset);
            HudTexture       = Content.Load<Texture2D>(HUDAsset);
            SampleProjectileTexture = Content.Load<Texture2D>(SampleWeaponAsset);

            SamplePickup = Content.Load<Texture2D>(SamplePickupAsset);

            // Sounds
            ExplosionSound = Content.Load<SoundEffect>(ExplosionSoundAsset);
            ThumpSound     = Content.Load<SoundEffect>(ThumpSoundAsset);
            LaserSound     = Content.Load<SoundEffect>(LaserSoundAsset);

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
