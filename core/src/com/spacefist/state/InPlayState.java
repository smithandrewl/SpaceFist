package com.spacefist.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.spacefist.GameData;
import com.spacefist.Hud;
import com.spacefist.Level;
import com.spacefist.SpawnPoint;
import com.spacefist.SpawnZone;
import com.spacefist.entities.Entity;
import com.spacefist.entities.enemies.Enemy;
import com.spacefist.entities.enemies.EnemyFighter;
import com.spacefist.entities.enemies.EnemyFreighter;
import com.spacefist.managers.BlockManager;
import com.spacefist.managers.CollisionManager;
import com.spacefist.managers.EnemyManager;
import com.spacefist.managers.EnemyMineManager;
import com.spacefist.managers.ExplosionManager;
import com.spacefist.managers.LevelManager;
import com.spacefist.managers.PickUpManager;
import com.spacefist.managers.PlayerManager;
import com.spacefist.managers.ProjectileManager;
import com.spacefist.state.abst.GameState;
import com.spacefist.util.Func;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/*
 * The main state of the game.  All game play occurs in the InPlayState.
 */
public class InPlayState implements GameState {

    // The speed at which the camera scrolls up the map / world
    private static final float SCROLL_SPEED = 1f;

    GameData gameData;

    private Hud hud;

    private long levelLoadedAt;
    private Array<Rectangle> debrisField;

    private boolean titleShown;

    // The portion of the world which is currently visible.
    @NotNull
    public Rectangle getOnScreenWorld() {
        Rectangle resolution = gameData.getResolution();
        Vector2   camera     = gameData.getCamera();

        int screenWidth  = (int) resolution.getWidth();
        int screenHeight = (int) resolution.getHeight();

        return new Rectangle((int) camera.x, (int) camera.y, screenWidth, screenHeight);
    }


    // It is used to measure playtime.
    // TODO: Convert Stopwatch
    //Stopwatch stopwatch = new Stopwatch();

    private Rectangle endOfLevelMarkerPos;

    public Rectangle getEndOfLevelMarkerPos() {
        return endOfLevelMarkerPos;
    }

    // TODO: Load and set end of level marker position
    public void setEndOfLevelMarkerPos(Rectangle endOfLevelMarkerPos) {
        this.endOfLevelMarkerPos = endOfLevelMarkerPos;
    }

    public InPlayState(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void loadContent() {
        LevelManager levelManager = gameData.getLevelManager();
        Level        level        = gameData.getLevel();

        levelManager.init();
        levelManager.loadLevel(1);

        debrisField    = new Array<Rectangle>(false, level.getDebrisParticleCount());
        gameData.setWorld(new Rectangle(0, 0, level.getWidth(), level.getHeight()));

        hud = new Hud(gameData, gameData.getPlayerManager());
    }

    @Override
    public void enteringState() {
        // reset the round statistics
        gameData.getRoundData().reset();

        Level level = gameData.getLevel();
        HashMap<String, Music> songs = gameData.getSongs();

        Music song  = songs.get(level.getSong());

        song.setLooping(true);
        song.play();

        // Position the camera at the bottom of the world
        gameData.setCamera(new Vector2(0, 0));

        PlayerManager playerManager = gameData.getPlayerManager();
        // Tell the ship manager to spawn the ship
        playerManager.spawn();

        // Since the game states are reused, clear the score and lives
        playerManager.resetLives();
        playerManager.resetScore();
        playerManager.resetWeapon();

        int numBlocks = level.getBlockCount();

        EnemyManager     enemyManager     = gameData.getEnemyManager();
        BlockManager     blockManager     = gameData.getBlockManager();
        EnemyMineManager enemyMineManager = gameData.getEnemyMineManager();
        PickUpManager    pickUpManager    = gameData.getPickUpManager();

        // spawn blocks to the world
        blockManager.spawnBlocks(numBlocks);

        enemyManager.clear();
        enemyMineManager.clear();
        pickUpManager.reset();

        //spawn fighters
        for (SpawnZone zone : level.getFighters())
        {

            if(zone.getCount() > 1) {
                enemyManager.spawnEnemies(
                        zone.getCount(),
                        zone.getLeft(),
                        zone.getRight(),
                        zone.getTop(),
                        zone.getBottom(),
                        new Func<Vector2, Enemy>() {
                            @NotNull
                            @Override
                            public Enemy call(Vector2 position) {
                                return new EnemyFighter(gameData, position);
                            }
                        }
                );
            }
            else
            {
                enemyManager.spawnEnemy(
                        (int) zone.getCenter().x,
                        (int) zone.getCenter().y,
                        new Func<Vector2, Enemy>() {
                            @NotNull
                            @Override
                            public Enemy call(Vector2 position) {
                                return new EnemyFighter(gameData, position);
                            }
                        }
                );
            }
        }

        // spawn freighters
        for (SpawnZone zone : level.getFreighters())
        {

            if (zone.getCount() > 1)
            {
                enemyManager.spawnEnemies(
                        zone.getCount(),
                        zone.getLeft(),
                        zone.getRight(),
                        zone.getTop(),
                        zone.getBottom(),
                        new Func<Vector2, Enemy>() {
                            @NotNull
                            @Override
                            public Enemy call(Vector2 position) {
                                return new EnemyFreighter(gameData, position);
                            }
                        }
                );
            }
            else
            {
                enemyManager.spawnEnemy(
                        (int) zone.getCenter().x,
                        (int) zone.getCenter().y,
                        new Func<Vector2, Enemy>() {
                            @NotNull
                            @Override
                            public Enemy call(Vector2 position) {
                                return new EnemyFreighter(gameData, position);
                            }
                        }
                );
            }
        }

        //spawn mines
        for (SpawnPoint point : level.getMines())
        {
            enemyMineManager.spawnEnemyMine(point.getX(), point.getY());
        }


        // spawn the players ship
        playerManager.initialize();


        // spawn the different pickups to the world
        pickUpManager.reset();
        pickUpManager.spawnExtraLifePickups(3);

        // TODO: Enable spawning of example pickups
        //pickUpManager.spawnExamplePickups(4);

        pickUpManager.spawnHealthPickups(4);



        /***Dongcai*/
        pickUpManager.spawnMissilePickups(3);
        /**********/

        debrisField.clear();

        int    minScale      = level.getDebrisParticleMinScale();
        int    maxScale      = level.getDebrisParticleMaxScale();
        String particleImage = level.getDebrisParticleImage();
        int    debrisCount   = level.getDebrisParticleCount();

        Rectangle world = gameData.getWorld();
        HashMap<String, Texture> textures = gameData.getTextures();

        // init debris field
        for (int i = 0; i < debrisCount; i++)
        {
            float maxX  = world.getWidth();
            float maxY  = world.getHeight();
            float scale = MathUtils.random(minScale * 10, maxScale * 10) * .01f;

            Texture texture = textures.get(particleImage);

            Rectangle rect = new Rectangle(
                    MathUtils.random(0, maxX),
                    MathUtils.random(0, maxY),
                    (int) (texture.getWidth()  * scale),
                    (int) (texture.getHeight() * scale)
            );

            debrisField.add(rect);
        }

        /*
        TODO: Convert stopwatch code

        stopwatch.reset();
        stopwatch.Start();

        */

        levelLoadedAt = TimeUtils.millis();
        titleShown    = false;
    }

    @Override
    public void update() {
        PlayerManager playerManager = gameData.getPlayerManager();

        if (playerManager.isAlive())
        {
            if (Gdx.input.isKeyPressed(Keys.Q) || Gdx.input.isKeyPressed(Keys.ESCAPE))
            {
                gameData.setCurrentState(gameData.getMenuState());
            }

            keepOnScreen(gameData.getShip());

            // Tell the entity managers to update
            ProjectileManager projectileManager = gameData.getProjectileManager();
            BlockManager      blockManager      = gameData.getBlockManager();
            ExplosionManager  explosionManager  = gameData.getExplosionManager();
            CollisionManager  collisionManager  = gameData.getCollisionManager();
            EnemyManager      enemyManager      = gameData.getEnemyManager();
            PickUpManager     pickUpManager     = gameData.getPickUpManager();
            EnemyMineManager  enemyMineManager  = gameData.getEnemyMineManager();

            projectileManager.update();
            blockManager.update();
            explosionManager.update();
            collisionManager.update();
            playerManager.update();
            enemyManager.update();
            pickUpManager.update();
            enemyMineManager.update();

            Rectangle world  = gameData.getWorld();
            Vector2   camera = gameData.getCamera();

            // Until the end of the world is reached, move the camera up the world
            if (camera.y <= world.getHeight()) {
                gameData.setCamera(new Vector2(camera.x, camera.y + SCROLL_SPEED));
            }

            // When the ship reaches the end of game marker, switch to the
            // end of level state.

            // TODO: Convert the end of level marker code
                /*
                if (gameData.getShip().getRectangle().overlaps(getEndOfLevelMarkerPos()));
                {
                    if (gameData.getLevel().isLastLevel()) {
                        gameData.setCurrentState(gameData.getEndOfGameState());
                    }
                    else {
                        gameData.setCurrentState(gameData.getEndOfLevelState());
                    }
                }
                */
        }
        else {
            // If the player has been killed, switch to the game over state
            gameData.setCurrentState(gameData.getGameOverState());
        }

        hud.update();

    }

    @Override
    public void draw() {

        HashMap<String, Texture> textures = gameData.getTextures();

        Level  level         = gameData.getLevel();
        String background    = level.getBackgroundImage();
        String particleImage = level.getDebrisParticleImage();

        Rectangle   resolution  = gameData.getResolution();
        SpriteBatch spriteBatch = gameData.getSpriteBatch();

        // draw the background
        spriteBatch.draw(
                textures.get(background),
                resolution.getX(),
                resolution.getY(),
                resolution.getWidth(),
                resolution.getHeight()
        );

        // draw debris
        for(Rectangle rect : debrisField)
        {
            spriteBatch.draw(
                    textures.get(particleImage),
                    rect.x - (int) gameData.getCamera().x,
                    rect.y - (int) gameData.getCamera().y,
                    rect.getWidth(),
                    rect.getHeight()
            );
        }

        // draw the entities
        ExplosionManager  explosionManager  = gameData.getExplosionManager();
        BlockManager      blockManager      = gameData.getBlockManager();
        ProjectileManager projectileManager = gameData.getProjectileManager();
        PickUpManager     pickUpManager     = gameData.getPickUpManager();
        EnemyManager      enemyManager      = gameData.getEnemyManager();
        EnemyMineManager  enemyMineManager  = gameData.getEnemyMineManager();
        PlayerManager     playerManager     = gameData.getPlayerManager();

        explosionManager.draw();
        blockManager.draw();
        projectileManager.draw();
        pickUpManager.draw();
        enemyManager.draw();
        pickUpManager.draw();
        enemyMineManager.draw();
        playerManager.draw();

            /*
            TODO: Convert DrawLevelMaarkers
            drawLevelMarkers();
            */

        hud.draw();

    }

    private void drawLevelMarkers() {
            /*
            TODO: Convert InPlayState.drawLevelMarkers
            int halfWidth  = (int)((gameData.World.Width / 2)    - gameData.Camera.X);
            int nearBottom = (int)((gameData.World.Bottom * .98) - gameData.Camera.Y);
            int nearTop    = (int)((gameData.World.Top * .02)    - gameData.Camera.Y);


            if (!titleShown)
            {
                var delta = (DateTime.Now - levelLoadedAt).TotalSeconds;

                // The title takes two seconds to fade in and then
                // is displayed for another second
                Color color = Color.White * ((float)(delta / 2));

                if (delta < 3)
                {
                    gameData.SpriteBatch.DrawString(
                        gameData.TitleFont,
                        gameData.Level.Title,
                        new Vector2(
                            (gameData.Resolution.Width / 2) - (gameData.Level.Title.Length * 10),
                            gameData.Resolution.Height / 2  - 48
                        ),
                        color,
                        0,
                        Vector2.Zero,
                        1f,
                        SpriteEffects.None,
                        0
                    );
                }
                else
                {
                    titleShown = true;
                }

            }
      
            EndOfLevelMarkerPos = new Rectangle(
                (int)halfWidth - (gameData.Textures["LevelEnd"].Width / 2),
                (int)((gameData.Level.Height / 8) - gameData.Camera.Y),
                gameData.Textures["LevelEnd"].Width,
                gameData.Textures["LevelEnd"].Height
            );

            gameData.SpriteBatch.draw(
                gameData.Textures["LevelEnd"],
                EndOfLevelMarkerPos,
                Color.White
            );

            */
    }

    @Override
    public void exitingState() {
        Level level = gameData.getLevel();

        HashMap<String, Music> songs = gameData.getSongs();

        Music song = songs.get(level.getSong());

        song.setLooping(false);
        song.stop();
    }

    // Keep the player on the screen
    private void keepOnScreen(@NotNull Entity obj) {
        Rectangle resolution = gameData.getResolution();
        Rectangle rectangle  = obj.getRectangle();

        int farRight   = (int) (gameData.getCamera().x + resolution.getWidth());
        int bottom     = (int) (gameData.getCamera().y + resolution.getHeight());

        int halfHeight = (int) (rectangle.getHeight() / 2);

        float velDecrease = .125f;

        boolean offScreenRight  = obj.getX() > farRight;
        boolean offScreenLeft   = obj.getX() < gameData.getCamera().x;
        boolean offscreenTop    = (obj.getY() + halfHeight) > bottom;
        boolean offscreenBottom = obj.getY() < gameData.getCamera().y;

        boolean offScreen = offScreenRight ||
                offScreenLeft ||
                offscreenTop ||
                offscreenBottom;

        if (offScreen) {
            if (offScreenRight) {
                obj.setX((int) (farRight - rectangle.getWidth()));
            } else if (offScreenLeft) {
                obj.setX((int) gameData.getCamera().x);
            } else if (offscreenTop) {
                obj.setY((int) (gameData.getCamera().y - (resolution.getHeight() * .9)));
            } else if (offscreenBottom) {
                obj.setY((int) (gameData.getCamera().y + (rectangle.getHeight() * 2)));
            }

            float mult = -1 * velDecrease;

            obj.setVelocity(new Vector2(obj.getVelocity().x * mult, obj.getVelocity().y * mult));
        }
    }
}
