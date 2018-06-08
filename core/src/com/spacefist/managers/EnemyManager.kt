package com.spacefist.managers

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.spacefist.GameData
import com.spacefist.entities.enemies.Enemy
import com.spacefist.entities.enemies.EnemyFighter
import com.spacefist.entities.enemies.EnemyFreighter
import com.spacefist.util.Func

/// <summary>
/// Keeps track of all of the enemies in the world.
/// </summary>
class EnemyManager/// <summary>
/// Creates a new EnemyManager instance.
/// </summary>
/// <param name="gameData">Common game data</param>
(gameData: GameData) : Manager<Enemy>(gameData) {

    /// <summary>
    /// Returns a collection of all of the live enemies
    /// which are visible to the player.
    /// </summary>
    /// <returns></returns>
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

    /// <summary>
    /// Spawns a number of enemy fighters to the world.
    /// </summary>
    /// <param name="count">The number of fighters to spawn</param>
    fun spawnEnemyFighters(count: Int) {
        spawnEnemies(count,  Func { position -> EnemyFighter(gameData, position) })
    }

    /// <summary>
    /// Spawns the specified number of enemyFreighters to the world.
    /// </summary>
    /// <param name="count">The number of freighters to spawn</param>
    fun spawnEnemyFreighters(count: Int) {
        spawnEnemies(count, Func { position:Vector2 -> EnemyFreighter(gameData, position) })
    }

    fun spawnEnemy(x: Int, y: Int, func: Func<Vector2, Enemy>) {
        val rotation = Math.toRadians(180.0).toFloat()
        val enemy    = func.call(Vector2(x.toFloat(), y.toFloat()))

        enemy.rotation = rotation
        add(enemy)
    }

    fun spawnEnemy(lowX: Int, highX: Int, lowY: Int, highY: Int, func: Func<Vector2, Enemy>) {
        val randX = MathUtils.random(lowX, highX)
        val randY = MathUtils.random(lowY, highY)

        spawnEnemy(randX, randY, func)
    }

    /// <summary>
    /// Spawns a number of enemies to random locations on the screen
    /// given an enemy placement method.
    /// </summary>
    /// <param name="count">The number of enemies to spawn</param>
    /// <param name="func">A function to spawn a particular type of enemy</param>
    private fun spawnEnemies(count: Int, func: Func<Vector2, Enemy>) {
        assert(count >= 0)
        assert(func != null)

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

    fun spawnEnemies(count: Int, lowX: Int, highX: Int, lowY: Int, highY: Int, func: Func<Vector2, Enemy>) {
        (0 until count).forEach { i -> spawnEnemy(lowX, highX, lowY, highY, func) }
    }
}

