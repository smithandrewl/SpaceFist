package com.spacefist.managers

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.spacefist.GameData
import com.spacefist.entities.enemies.Enemy
import com.spacefist.entities.enemies.EnemyFighter
import com.spacefist.entities.enemies.EnemyFreighter

/**
 * Keeps track of all of the enemies in the world.
 *
 * Creates a new EnemyManager instance.
 *
 * @param gameData Common game data
 */
class EnemyManager

(gameData: GameData) : Manager<Enemy>(gameData) {

    /**
     * Returns a collection of all of the live enemies
     * which are visible to the player.
     */
    val visibleEnemies: Iterable<Enemy>
        get() {
            val camera = gameData.camera
            val backgroundRect = gameData.resolution

            val screenRect = Rectangle(
                    camera.x.toInt().toFloat(),
                    camera.y.toInt().toFloat(),
                    backgroundRect.getWidth(),
                    backgroundRect.getHeight()
            )

            val visibleEnemies = Array<Enemy>(false, 16)

            this
                .filter  { it.isAlive && screenRect.overlaps(it.rectangle) }
                .forEach { visibleEnemies.add(it)                          }

            return visibleEnemies
        }

    /**
     *  Spawns a number of enemy fighters to the world.
     * 
     *  @param count The number of fighters to spawn
     */
    fun spawnEnemyFighters(count: Int) {
        spawnEnemies(count,  { position: Vector2 -> EnemyFighter(gameData, position) })
    }

    /**
     *  Spawns the specified number of enemyFreighters to the world.
     * 
     *  @param count The number of freighters to spawn
     */
    fun spawnEnemyFreighters(count: Int) {
        spawnEnemies(count, { position:Vector2 -> EnemyFreighter(gameData, position) })
    }

    fun spawnEnemy(x: Int, y: Int, func: (Vector2) -> Enemy) {
        val rotation = Math.toRadians(180.0).toFloat()
        val enemy    = func(Vector2(x.toFloat(), y.toFloat()))

        enemy.rotation = rotation
        add(enemy)
    }

    fun spawnEnemy(lowX: Int, highX: Int, lowY: Int, highY: Int, func: (Vector2) -> Enemy) {
        val randX = MathUtils.random(lowX, highX)
        val randY = MathUtils.random(lowY, highY)

        spawnEnemy(randX, randY, func)
    }

    /**
     *  Spawns a number of enemies to random locations on the screen
     *  given an enemy placement method.
     * 
     *  @param count The number of enemies to spawn
     *  @param func A function to spawn a particular type of enemy
     */
    private fun spawnEnemies(count: Int, func: (Vector2) -> Enemy) {
        assert(count >= 0)

        spawnEnemies(
                count,
                0,
                gameData.world.getWidth().toInt(),
                0,
                Math.max(
                        gameData.world.getHeight() * .9f,
                        gameData.resolution.getHeight() / 2
                ).toInt(),
                func
        )
    }

    fun spawnEnemies(count: Int, lowX: Int, highX: Int, lowY: Int, highY: Int, func: (Vector2) -> Enemy) {
        (0 until count).forEach { i -> spawnEnemy(lowX, highX, lowY, highY, func) }
    }
}

