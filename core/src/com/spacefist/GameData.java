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
import com.spacefist.managers.*;
import com.spacefist.state.*;
import com.spacefist.state.abst.GameState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Random;

public class GameData {
    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    private Random random;
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

    private HashMap<String, Texture> textures;
    private HashMap<String, Sound>   soundEffects;
    private HashMap<String, Music>   songs;

    private RoundData roundData;

    private Ship       ship;
    private BitmapFont font;
    private BitmapFont titleFont;

    // -------------- Game States --------------
    private SplashScreenState splashScreenState;
    private MenuState         menuState;

    /*
    public GraphicsDevice                  GraphicsDevice { get; set; }
    */

    private Level level;


    // -------------- Managers --------------
    private LevelManager      levelManager;

    private ProjectileManager projectileManager;
    private PlayerManager     playerManager;
    private PickUpManager     pickUpManager;
    private ExplosionManager  explosionManager;
    private EnemyMineManager  enemyMineManager;
    private EnemyManager      enemyManager;
    private BlockManager      blockManager;
    private CollisionManager  collisionManager;

    private LogoState logoState;
    public GameData(SpaceFistGame game) {
        this.game = game;

        random = new Random();

        roundData = new RoundData();

        textures     = new HashMap<String, Texture>();
        soundEffects = new HashMap<String, Sound>();
        songs        = new HashMap<String, Music>();

        levelManager = new LevelManager(this);

        projectileManager = new ProjectileManager(this);
        playerManager     = new PlayerManager(this);
        pickUpManager     = new PickUpManager(this);
        explosionManager  = new ExplosionManager(this);
        enemyMineManager  = new EnemyMineManager(this);
        enemyManager      = new EnemyManager(this);
        blockManager      = new BlockManager(this);
        collisionManager  = new CollisionManager(this);
    }

    /**
     * Called when non-garbage-collected memory should be disposed of
     */
    public void dispose() {

        for (Texture texture : textures.values()) {
            texture.dispose();
        }

        for (Music song : songs.values()) {
            song.dispose();
        }

        for (Sound soundEffect : soundEffects.values()) {
            soundEffect.dispose();
        }
    }


    public Ship getShip() {
        return ship;
    }

    private InPlayState     inPlayState;
    private GameOverState   gameOverState;
    private EndOfGameState  endOfGameState;
    private CreditsState    creditsState;
    private EndOfLevelState endOfLevelState;

    @NotNull
    public Rectangle getOnScreenWorld() {
        return new Rectangle(
            (int) camera.x,
            (int) camera.y,
            resolution.getWidth(),
            resolution.getHeight()
        );
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(@NotNull GameState newState) {
        if (currentState != null) {
            currentState.exitingState();
        }

        newState.enteringState();
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
        return textures;
    }

    public void setTextures(HashMap<String, Texture> textures) {
        this.textures = textures;
    }

    public HashMap<String, Sound> getSoundEffects() {
        return soundEffects;
    }

    public void setSoundEffects(HashMap<String, Sound> soundEffects) {
        this.soundEffects = soundEffects;
    }

    public HashMap<String, Music> getSongs() {
        return songs;
    }

    public void setSongs(HashMap<String, Music> songs) {
        this.songs = songs;
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

    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }

    public void setProjectileManager(ProjectileManager projectileManager) {
        this.projectileManager = projectileManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public PickUpManager getPickUpManager() {
        return pickUpManager;
    }

    public void setPickUpManager(PickUpManager pickUpManager) {
        this.pickUpManager = pickUpManager;
    }

    public ExplosionManager getExplosionManager() {
        return explosionManager;
    }

    public void setExplosionManager(ExplosionManager explosionManager) {
        this.explosionManager = explosionManager;
    }

    public EnemyMineManager getEnemyMineManager() {
        return enemyMineManager;
    }

    public void setEnemyMineManager(EnemyMineManager enemyMineManager) {
        this.enemyMineManager = enemyMineManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void setEnemyManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public void setBlockManager(BlockManager blockManager) {
        this.blockManager = blockManager;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    public void setCollisionManager(CollisionManager collisionManager) {
        this.collisionManager = collisionManager;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }
}
