using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Media;
using SpaceFist.State.Abstract;
using SpaceFist.Managers;
using SpaceFist.State;
using Microsoft.Xna.Framework.Content;

namespace SpaceFist
{
    public class GameData
    {

        private GameState currentState;
        private Game game;

        public bool IsMouseVisible {
            get
            {
                return game.IsMouseVisible;
            }
            set{
                game.IsMouseVisible = value;
            }       
        } 
        
        public Ship                            Ship         { get; set; }

        public SpriteFont                      Font         { get; set; }
        public Rectangle                       Resolution   { get; set; }
        public float                           ScreenScale  { get; set; }
        public SpriteBatch                     SpriteBatch  { get; set; }
        public ContentManager                  Content      { get; set; }
        public Dictionary<string, Texture2D>   Textures     { get; set; }
        public Dictionary<string, SoundEffect> SoundEffects { get; set; }
        public Dictionary<string, Song>        Songs        { get; set; }
        public RoundData                       RoundData    { get; set; }
        public GraphicsDevice GraphicsDevice { get; set; }

        // Camera
        public Vector2   Camera        { get; set; }
        public Rectangle World         { get; set; }

        public Rectangle OnScreenWorld
        {
            get
            {
                return new Rectangle((int)Camera.X, (int)Camera.Y, Resolution.Width, Resolution.Height);
            }
        }
        // Managers
        // TODO: Add map for Managers
        public ProjectileManager ProjectileManager { get; set; }
        public PlayerManager     PlayerManager     { get; set; }
        public PickUpManager     PickUpManager     { get; set; }
        public ExplosionManager  ExplosionManager  { get; set; }
        public EnemyMineManager  EnemyMineManager  { get; set; }
        public EnemyManager      EnemyManager      { get; set; }
        public BlockManager      BlockManager      { get; set; }
        public CollisionManager  CollisionManager  { get; set; }

        public SplashScreenState SplashScreenState { get; set; }
        public MenuState         MenuState         { get; set; }
        public LogoState         LogoState         { get; set; }
        public InPlayState       InPlayState       { get; set; }
        public GameOverState     GameOverState     { get; set; }
        public EndOfGameState    EndOfGameState    { get; set; }
        public CreditsState      CreditsState      { get; set; }
        
        public GameData(Game game)
        {
            this.game = game;

            RoundData    = new RoundData();
            Textures     = new Dictionary<string, Texture2D>();
            SoundEffects = new Dictionary<string, SoundEffect>();
            Songs        = new Dictionary<string, Song>();

            ProjectileManager = new ProjectileManager(this);
            PlayerManager     = new PlayerManager(this);
            PickUpManager     = new PickUpManager(this);
            ExplosionManager  = new ExplosionManager(this);
            EnemyMineManager  = new EnemyMineManager(this);
            EnemyManager      = new EnemyManager(this);
            BlockManager      = new BlockManager(this);
            CollisionManager  = new CollisionManager(this);
        }


        public GameState CurrentState
        {
            get
            {
                return currentState;
            }
            set
            {
                if (currentState != null)
                {
                    currentState.ExitingState();
                }

                value.EnteringState();
                currentState = value;
            }
        }
    }
}
