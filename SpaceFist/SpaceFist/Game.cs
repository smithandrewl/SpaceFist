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
        public SpriteFont  Font             { get; set; }
        public Texture2D   Background       { get; set; }
        public Texture2D   LaserTexture     { get; set; }
        public Texture2D   BlockTexture     { get; set; }
        public Texture2D   GameOverTexture  { get; set; }
        public Texture2D   ShipSheet        { get; set; }
        public Texture2D   EnemySheet       { get; set; }
        public Texture2D   ExplosionTexture { get; set; }
        public Texture2D   HudTexture       { get; set; }
        
        public Rectangle   BackgroundRect   { get; set; }
        
        public SoundEffect LaserSound      { get; set; }
        public SoundEffect ExplosionSound  { get; set; }
        public SoundEffect ThumpSound      { get; set; }
        
        public InPlayState       InPlayState       { get; set; }
        public SplashScreenState SplashScreenState { get; set; }
        public GameOverState     GameOverState     { get; set; }

        private const String SpriteFontAsset         = @"Fonts\SpriteFont";
        private const String LaserImageAsset         = @"Images\Sprites\Laser";
        private const String BackgroundAsset         = @"Images\Backgrounds\Purple";
        private const String GameOverAsset           = @"Images\Backgrounds\GameOver";
        private const String BlockImageAsset         = @"Images\Sprites\Block";
        private const String ShipSheetAsset          = @"Images\Sprites\ShipSheet";
        private const String EnemeySheetAsset        = @"Images\Sprites\EnemySheet";
        private const String ExplosionAnimationAsset = @"Images\Animations\explosion";
        private const String HUDAsset                = @"Images\UI\Hud";

        private const String ExplosionSoundAsset     = @"Sound\explosion";
        private const String ThumpSoundAsset         = @"Sound\thump";
        private const String LaserSoundAsset         = @"Sound\laser";

        public SpriteBatch SpriteBatch {
            get {
                return spriteBatch;
            }
        }

        public Game()
        {
            SplashScreenState = new SplashScreenState(this);
            InPlayState       = new InPlayState(this);
            GameOverState     = new GameOverState(this);

            graphics = new GraphicsDeviceManager(this);
            
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
            Font             = Content.Load<SpriteFont>(SpriteFontAsset);
            
            Background       = Content.Load<Texture2D>(BackgroundAsset);
            GameOverTexture  = Content.Load<Texture2D>(GameOverAsset);
            LaserTexture     = Content.Load<Texture2D>(LaserImageAsset);
            BlockTexture     = Content.Load<Texture2D>(BlockImageAsset);
            ShipSheet        = Content.Load<Texture2D>(ShipSheetAsset);
            EnemySheet       = Content.Load<Texture2D>(EnemeySheetAsset);
            ExplosionTexture = Content.Load<Texture2D>(ExplosionAnimationAsset);
            HudTexture       = Content.Load<Texture2D>(HUDAsset);

            ExplosionSound = Content.Load<SoundEffect>(ExplosionSoundAsset);
            ThumpSound     = Content.Load<SoundEffect>(ThumpSoundAsset);
            LaserSound     = Content.Load<SoundEffect>(LaserSoundAsset);

            SplashScreenState.LoadContent();
            InPlayState.LoadContent();

            currentState.EnteringState();
        }

        protected override void UnloadContent()
        {
            
        }

        protected override void Update(GameTime gameTime)
        {
            currentState.Update();
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
