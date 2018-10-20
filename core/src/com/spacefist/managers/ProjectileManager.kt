package com.spacefist.managers

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.spacefist.GameData
import com.spacefist.entities.Entity
import com.spacefist.entities.Projectile

/**
 *  Keeps track of the projectiles in the world.
 *
 *  Creates a new ProjectileManager instance
 *
 *  @param name gameData Common game data
 */
class ProjectileManager

(gameData: GameData) : Manager<Projectile>(gameData) {

    override fun update() {
        entities
            .filter  { entity -> entity.isAlive }

            .forEach { projectile ->
                val resolution = gameData.resolution

                val rect = Rectangle(
                        gameData.camera.x.toInt().toFloat(),
                        gameData.camera.y.toInt().toFloat(),
                        resolution.getWidth(),
                        resolution.getHeight()
                )

                if (rect.contains(projectile.rectangle)) {
                    projectile.update()
                } else {
                    projectile.isAlive = false
                }
            }
        }


    /**
     *  Fires a laser from the specified location in the specified direction.
     * 
     *  @param x The X value of the location
     *  @param y The Y value of the location
     *  @param direction The direction to send the projectile
     *  @param enemyLaser Whether this laser belongs to an enemy
     */
    @JvmOverloads
    fun fireLaser(x: Int, y: Int, direction: Vector2 = Vector2(0f, -1f), enemyLaser: Boolean = false) {
        gameData.roundData.shotsFired = gameData.roundData.shotsFired + 1

        val rotation = Math.toDegrees(Math.atan2(direction.y.toDouble(), direction.x.toDouble()).toFloat().toDouble()).toFloat()

        // Place a new active laser at x, y
        val projectile = Projectile(
                gameData,
                gameData.textures["Laser"]!!,
                Vector2(x.toFloat(), y.toFloat()),
                direction,
                9,
                enemyLaser
        )

        projectile.rotation = Math.toRadians(rotation.toDouble()).toFloat()

        add(projectile)

    }

   

    fun fireMissile(x: Int, y: Int) {
        gameData.roundData.shotFired()

        val projectile = Projectile(
                gameData,
                gameData.textures["Missile"]!!,
                Vector2(x.toFloat(), y.toFloat()),
                Vector2(0f, -1f),
                20,
                false
        )

        add(projectile)

        val projectile1 = Projectile(
                gameData,
                gameData.textures["Missile"]!!,
                Vector2((x + 50).toFloat(), y.toFloat()),
                Vector2(0f, -1f),
                10,
                false
        )

        add(projectile1)

        val projectile2 = Projectile(
                gameData,
                gameData.textures["Missile"]!!,
                Vector2((x - 50).toFloat(), y.toFloat()),
                Vector2(0f, -1f),
                10,
                false
        )

        add(projectile2)

    }

    /**
     *  @return Returns a collection of all of the live projectiles
     *  fired by the player.
     */
    fun playerProjectiles(): Iterable<Projectile> {
        val playerProjs = Array<Projectile>(false, 16)

        entities
                .filter  { !it.isEnemyProjectile && it.isAlive }
                .forEach { playerProjs.add(it)                 }

        return playerProjs
    }

    /**
     *  Returns a collection of all of the live projectiles
     *  fired by an enemy.
     * 
     */
    fun enemyProjectiles(): Iterable<Projectile> {
        val enemyProjs = Array<Projectile>(false, 16)

        entities
                .filter  { it.isEnemyProjectile && it.isAlive }
                .forEach { enemyProjs.add(it)                 }

        return enemyProjs
    }
}