package com.spacefist;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.entities.Ship;
import com.spacefist.game.SpaceFistGame;
import com.spacefist.state.*;
import com.spacefist.state.abst.GameState;

import java.util.HashMap;

public class GameData {
    private GameState     currentState;
    private SpaceFistGame game;

    /*
    public bool IsMouseVisible {
    get
    {
        return game.IsMouseVisible;
    }
    set{
        game.IsMouseVisible = value;
    }
    }
    */

    private float ScreenScale;
    private int   LevelCount;

    private Vector2   camera;
    private Rectangle world;

    private SpriteBatch spriteBatch;
    private Rectangle   resolution;

    private HashMap<String, Texture> Textures;
    private HashMap<String, Sound>   SoundEffects;
    private HashMap<String, Music>   Songs;

    private RoundData roundData;

    private Ship       ship;
    private BitmapFont font;
    private BitmapFont titleFont;
    // -------------- Game States --------------
    private SplashScreenState splashScreenState;
    private MenuState         menuState;

    /*
    public GraphicsDevice                  GraphicsDevice { get; set; }
    public Level                           Level          { get; set; }
    */

    /*
    // -------------- Managers --------------
    public LevelManager      LevelManager      { get; set; }
    public ProjectileManager ProjectileManager { get; set; }
    public PlayerManager     PlayerManager     { get; set; }
    public PickUpManager     PickUpManager     { get; set; }
    public ExplosionManager  ExplosionManager  { get; set; }
    public EnemyMineManager  EnemyMineManager  { get; set; }
    public EnemyManager      EnemyManager      { get; set; }
    public BlockManager      BlockManager      { get; set; }
    public CollisionManager  CollisionManager  { get; set; }

    */
    private LogoState logoState;
    public GameData(SpaceFistGame game) {
        this.setGame(game);

        setRoundData(new RoundData());

        setTextures(new HashMap<String, Texture>());
        setSoundEffects(new HashMap<String, Sound>());
        setSongs(new HashMap<String, Music>());

        /*
        LevelManager      = new LevelManager(this);
        ProjectileManager = new ProjectileManager(this);
        PlayerManager     = new PlayerManager(this);
        PickUpManager     = new PickUpManager(this);
        ExplosionManager  = new ExplosionManager(this);
        EnemyMineManager  = new EnemyMineManager(this);
        EnemyManager      = new EnemyManager(this);
        BlockManager      = new BlockManager(this);
        CollisionManager  = new CollisionManager(this);
        */
    }

    public Ship getShip() {
        return ship;
    }

    private InPlayState     inPlayState;
    private GameOverState   gameOverState;
    private EndOfGameState  endOfGameState;
    private CreditsState    creditsState;
    private EndOfLevelState endOfLevelState;

    public Rectangle getOnScreenWorld() {
        return new Rectangle(
            (int)getCamera().x,
            (int)getCamera().y,
            getResolution().getWidth(),
            getResolution().getHeight()
        );
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState newState) {
        if (currentState != null) {
            currentState.ExitingState();
        }

        newState.EnteringState();
        currentState = newState;
    }

    public SpaceFistGame getGame() {
        return game;
    }

    public void setGame(SpaceFistGame game) {
        this.game = game;
    }

    public float getScreenScale() {
        return ScreenScale;
    }

    public void setScreenScale(float screenScale) {
        ScreenScale = screenScale;
    }

    public int getLevelCount() {
        return LevelCount;
    }

    public void setLevelCount(int levelCount) {
        LevelCount = levelCount;
    }

    public Vector2 getCamera() {
        return camera;
    }

    public void setCamera(Vector2 camera) {
        this.camera = camera;
    }

    public Rectangle getWorld() {
        return world;
    }

    public void setWorld(Rectangle world) {
        this.world = world;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public Rectangle getResolution() {
        return resolution;
    }

    public void setResolution(Rectangle resolution) {
        this.resolution = resolution;
    }

    public HashMap<String, Texture> getTextures() {
        return Textures;
    }

    public void setTextures(HashMap<String, Texture> textures) {
        Textures = textures;
    }

    public HashMap<String, Sound> getSoundEffects() {
        return SoundEffects;
    }

    public void setSoundEffects(HashMap<String, Sound> soundEffects) {
        SoundEffects = soundEffects;
    }

    public HashMap<String, Music> getSongs() {
        return Songs;
    }

    public void setSongs(HashMap<String, Music> songs) {
        Songs = songs;
    }

    public RoundData getRoundData() {
        return roundData;
    }

    public void setRoundData(RoundData roundData) {
        this.roundData = roundData;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public BitmapFont getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(BitmapFont titleFont) {
        this.titleFont = titleFont;
    }

    public LogoState getLogoState() {
        return logoState;
    }

    public void setLogoState(LogoState logoState) {
        this.logoState = logoState;
    }

    public SplashScreenState getSplashScreenState() {
        return splashScreenState;
    }

    public void setSplashScreenState(SplashScreenState splashScreenState) {
        this.splashScreenState = splashScreenState;
    }

    public MenuState getMenuState() {
        return menuState;
    }

    public void setMenuState(MenuState menuState) {
        this.menuState = menuState;
    }

    public InPlayState getInPlayState() {
        return inPlayState;
    }

    public void setInPlayState(InPlayState inPlayState) {
        this.inPlayState = inPlayState;
    }

    public GameOverState getGameOverState() {
        return gameOverState;
    }

    public void setGameOverState(GameOverState gameOverState) {
        this.gameOverState = gameOverState;
    }

    public EndOfGameState getEndOfGameState() {
        return endOfGameState;
    }

    public void setEndOfGameState(EndOfGameState endOfGameState) {
        this.endOfGameState = endOfGameState;
    }

    public CreditsState getCreditsState() {
        return creditsState;
    }

    public void setCreditsState(CreditsState creditsState) {
        this.creditsState = creditsState;
    }

    public EndOfLevelState getEndOfLevelState() {
        return endOfLevelState;
    }

    public void setEndOfLevelState(EndOfLevelState endOfLevelState) {
        this.endOfLevelState = endOfLevelState;
    }
}
