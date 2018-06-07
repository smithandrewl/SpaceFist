package com.spacefist.state

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.TimeUtils
import com.spacefist.GameData
import com.spacefist.Hud
import com.spacefist.entities.Entity
import com.spacefist.entities.enemies.EnemyFighter
import com.spacefist.entities.enemies.EnemyFreighter
import com.spacefist.state.abst.GameState
import com.spacefist.util.Func

/*
 * The main state of the game.  All game play occurs in the InPlayState.
 */
class InPlayState(internal var gameData: GameData) : GameState {

    private var hud: Hud? = null

    private var levelLoadedAt: Long = 0
    private var debrisField: Array<Rectangle>? = null

    private var titleShown: Boolean = false

    // The portion of the world which is currently visible.
    val onScreenWorld: Rectangle
        get() {
            val resolution = gameData.resolution
            val camera = gameData.camera

            val screenWidth = resolution.getWidth().toInt()
            val screenHeight = resolution.getHeight().toInt()

            return Rectangle(camera.x.toInt().toFloat(), camera.y.toInt().toFloat(), screenWidth.toFloat(), screenHeight.toFloat())
        }


    // It is used to measure playtime.
    // TODO: Convert Stopwatch
    //Stopwatch stopwatch = new Stopwatch();

    // TODO: Load and set end of level marker position
    var endOfLevelMarkerPos: Rectangle? = null

    override fun loadContent() {
        val levelManager = gameData.levelManager
        val level = gameData.level

        levelManager.init()
        levelManager.loadLevel(1)

        debrisField = Array(false, level.debrisParticleCount)
        gameData.world = Rectangle(0f, 0f, level.width.toFloat(), level.height.toFloat())

        hud = Hud(gameData, gameData.playerManager)
    }

    override fun enteringState() {
        // reset the round statistics
        gameData.roundData.reset()

        val level = gameData.level
        val songs = gameData.songs

        val song = songs[level.song]

        song!!.setLooping(true)
        song!!.play()

        // Position the camera at the bottom of the world
        gameData.camera = Vector2(0f, 0f)

        val playerManager = gameData.playerManager
        // Tell the ship manager to spawn the ship
        playerManager.spawn()

        // Since the game states are reused, clear the score and lives
        playerManager.resetLives()
        playerManager.resetScore()
        playerManager.resetWeapon()

        val numBlocks = level.blockCount

        val enemyManager = gameData.enemyManager
        val blockManager = gameData.blockManager
        val enemyMineManager = gameData.enemyMineManager
        val pickUpManager = gameData.pickUpManager

        // spawn blocks to the world
        blockManager.spawnBlocks(numBlocks)

        enemyManager.clear()
        enemyMineManager.clear()
        pickUpManager.reset()

        //spawn fighters
        for (zone in level.getFighters()) {

            if (zone.count > 1) {
                enemyManager.spawnEnemies(
                        zone.count,
                        zone.left,
                        zone.right,
                        zone.top,
                        zone.bottom,
                        Func { position -> EnemyFighter(gameData, position) }
                )
            } else {
                enemyManager.spawnEnemy(
                        zone.center.x.toInt(),
                        zone.center.y.toInt(),
                        Func { position -> EnemyFighter(gameData, position) }
                )
            }
        }

        // spawn freighters
        for (zone in level.getFreighters()) {

            if (zone.count > 1) {
                enemyManager.spawnEnemies(
                        zone.count,
                        zone.left,
                        zone.right,
                        zone.top,
                        zone.bottom,
                        Func { position -> EnemyFreighter(gameData, position) }
                )
            } else {
                enemyManager.spawnEnemy(
                        zone.center.x.toInt(),
                        zone.center.y.toInt(),
                        Func { position -> EnemyFreighter(gameData, position) }
                )
            }
        }

        //spawn mines
        for (point in level.getMines()) {
            enemyMineManager.spawnEnemyMine(point.x, point.y)
        }


        // spawn the players ship
        playerManager.initialize()


        // spawn the different pickups to the world
        pickUpManager.reset()
        pickUpManager.spawnExtraLifePickups(3)

        // TODO: Enable spawning of example pickups
        //pickUpManager.spawnExamplePickups(4);

        pickUpManager.spawnHealthPickups(4)


        /***Dongcai */
        pickUpManager.spawnMissilePickups(3)
        /** */

        debrisField!!.clear()

        val minScale = level.debrisParticleMinScale
        val maxScale = level.debrisParticleMaxScale
        val particleImage = level.debrisParticleImage
        val debrisCount = level.debrisParticleCount

        val world = gameData.world
        val textures = gameData.textures

        // init debris field
        for (i in 0 until debrisCount) {
            val maxX = world.getWidth()
            val maxY = world.getHeight()
            val scale = MathUtils.random(minScale * 10, maxScale * 10) * .01f

            val texture = textures[particleImage]

            val rect = Rectangle(
                    MathUtils.random(0f, maxX),
                    MathUtils.random(0f, maxY),
                    (texture!!.getWidth() * scale).toInt().toFloat(),
                    (texture!!.getHeight() * scale).toInt().toFloat()
            )

            debrisField!!.add(rect)
        }

        /*
        TODO: Convert stopwatch code

        stopwatch.reset();
        stopwatch.Start();

        */

        levelLoadedAt = TimeUtils.millis()
        titleShown = false
    }

    override fun update() {
        val playerManager = gameData.playerManager

        if (playerManager.isAlive) {
            if (Gdx.input.isKeyPressed(Keys.Q) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
                gameData.currentState = gameData.menuState
            }

            keepOnScreen(gameData.ship)

            // Tell the entity managers to update
            val projectileManager = gameData.projectileManager
            val blockManager = gameData.blockManager
            val explosionManager = gameData.explosionManager
            val collisionManager = gameData.collisionManager
            val enemyManager = gameData.enemyManager
            val pickUpManager = gameData.pickUpManager
            val enemyMineManager = gameData.enemyMineManager

            projectileManager.update()
            blockManager.update()
            explosionManager.update()
            collisionManager.update()
            playerManager.update()
            enemyManager.update()
            pickUpManager.update()
            enemyMineManager.update()

            val world = gameData.world
            val camera = gameData.camera

            // Until the end of the world is reached, move the camera up the world
            if (camera.y <= world.getHeight()) {
                gameData.camera = Vector2(camera.x, camera.y + SCROLL_SPEED)
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
        } else {
            // If the player has been killed, switch to the game over state
            gameData.currentState = gameData.gameOverState
        }

        hud!!.update()

    }

    override fun draw() {

        val textures = gameData.textures

        val level = gameData.level
        val background = level.backgroundImage
        val particleImage = level.debrisParticleImage

        val resolution = gameData.resolution
        val spriteBatch = gameData.spriteBatch

        // draw the background
        spriteBatch.draw(
                textures[background],
                resolution.getX(),
                resolution.getY(),
                resolution.getWidth(),
                resolution.getHeight()
        )

        // draw debris
        for (rect in debrisField!!) {
            spriteBatch.draw(
                    textures[particleImage],
                    rect.x - gameData.camera.x.toInt(),
                    rect.y - gameData.camera.y.toInt(),
                    rect.getWidth(),
                    rect.getHeight()
            )
        }

        // draw the entities
        val explosionManager = gameData.explosionManager
        val blockManager = gameData.blockManager
        val projectileManager = gameData.projectileManager
        val pickUpManager = gameData.pickUpManager
        val enemyManager = gameData.enemyManager
        val enemyMineManager = gameData.enemyMineManager
        val playerManager = gameData.playerManager

        explosionManager.draw()
        blockManager.draw()
        projectileManager.draw()
        pickUpManager.draw()
        enemyManager.draw()
        pickUpManager.draw()
        enemyMineManager.draw()
        playerManager.draw()

        /*
            TODO: Convert DrawLevelMaarkers
            drawLevelMarkers();
            */

        hud!!.draw()

    }

    private fun drawLevelMarkers() {
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

    override fun exitingState() {
        val level = gameData.level

        val songs = gameData.songs

        val song = songs[level.song]

        song!!.setLooping(false)
        song!!.stop()
    }

    // Keep the player on the screen
    private fun keepOnScreen(obj: Entity) {
        val resolution = gameData.resolution
        val rectangle = obj.rectangle

        val farRight = (gameData.camera.x + resolution.getWidth()).toInt()
        val bottom = (gameData.camera.y + resolution.getHeight()).toInt()

        val halfHeight = (rectangle!!.getHeight() / 2).toInt()

        val velDecrease = .125f

        val offScreenRight = obj.x > farRight
        val offScreenLeft = obj.x < gameData.camera.x
        val offscreenTop = obj.y + halfHeight > bottom
        val offscreenBottom = obj.y < gameData.camera.y

        val offScreen = offScreenRight ||
                offScreenLeft ||
                offscreenTop ||
                offscreenBottom

        if (offScreen) {
            if (offScreenRight) {
                obj.x = (farRight - rectangle.getWidth()).toInt()
            } else if (offScreenLeft) {
                obj.x = gameData.camera.x.toInt()
            } else if (offscreenTop) {
                obj.y = (gameData.camera.y - resolution.getHeight() * .9).toInt()
            } else if (offscreenBottom) {
                obj.y = (gameData.camera.y + rectangle.getHeight() * 2).toInt()
            }

            val mult = -1 * velDecrease

            obj.velocity = Vector2(obj.velocity!!.x * mult, obj.velocity!!.y * mult)
        }
    }

    companion object {

        // The speed at which the camera scrolls up the map / world
        private val SCROLL_SPEED = 1f
    }
}
