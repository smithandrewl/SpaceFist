package com.spacefist.managers

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.spacefist.GameData
import com.spacefist.ai.projectilebehaviors.SeekingBehavior
import com.spacefist.entities.Entity
import com.spacefist.entities.Projectile

/// <summary>
/// Keeps track of the projectiles in the world.
/// </summary>
class ProjectileManager/// <summary>
/// Creates a new ProjectileManager instance
/// </summary>
/// <param name="gameData">Common game data</param>
(gameData: GameData) : Manager<Projectile>(gameData) {

    override fun update() {

        for (projectile in entities) {
            val resolution = gameData.resolution

            val rect = Rectangle(
                    gameData.camera.x.toInt().toFloat(),
                    gameData.camera.y.toInt().toFloat(),
                    resolution.getWidth(),
                    resolution.getHeight()
            )

            // Mark offscreen live projectiles as dead and
            // only update onscreen projectiles.
            if (projectile.isAlive) {
                if (rect.contains(projectile.rectangle)) {
                    projectile.update()
                } else {
                    projectile.isAlive = false
                }
            }
        }
    }

    /// <summary>
    /// Fires a laser from the specified location in the specified direction.
    /// </summary>
    /// <param name="x">The X value of the location</param>
    /// <param name="y">The Y value of the location</param>
    /// <param name="direction">The direction to send the projectile</param>
    /// <param name="enemyLaser">Whether this laser belongs to an enemy</param>
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

    /// <summary>
    /// Fires a rocket cluster from the specified location
    /// </summary>
    /// <param name="x">The X component of the location</param>
    /// <param name="y">The Y component of the location</param>
    fun fireSampleWeapon(x: Int, y: Int) {
        val visibleEnemies = gameData.enemyManager.visibleEnemies
        val visibleBlocks = gameData.blockManager.visibleBlocks

        val onScreen = Array<Entity>(false, 16)

        onScreen.addAll(visibleEnemies as Array<out Entity>)
        onScreen.addAll(visibleBlocks as Array<out Entity>)


        // TODO: fireSampleWeapon: ConcurrentModification Bug
        for (entity in onScreen) {
            if (entity.y >= y) {
                onScreen.removeValue(entity, true)
            }
        }

        if (onScreen.size != 0) {
            // TODO: fireSampleWeapon: IndexOutOfBounds Bug
            // Mark several onscreen entities as targets
            // and send rockets to intercept them.
            for (i in 0..3) {
                val idx = MathUtils.random(onScreen.size)

                val target = onScreen.get(idx)

                // Convert tinting code in ProjectileManager
                // Mark targeted entities by tinting them red
                // target.Tint = Color.Crimson;

                gameData.roundData.shotFired()


                val projectile = Projectile(
                        gameData,
                        gameData.textures["SampleWeapon"]!!,
                        Vector2(x.toFloat(), y.toFloat()),
                        Vector2(0f, -1f),
                        10,
                        false
                )

                projectile.behavior = SeekingBehavior(
                        Vector2(0f, -1f),
                        Vector2(x.toFloat(), y.toFloat()),
                        target
                )

                add(projectile)
            }
        }
    }

    /*******Dongcai */
    fun fireBluelaser(x: Int, y: Int) {
        val rand = gameData.random

        gameData.roundData.shotFired()

        val projectile = Projectile(
                gameData,
                gameData.textures["Mine"]!!,
                Vector2(x.toFloat(), y.toFloat()),
                Vector2(0f, -1f),
                1,
                false
        )


        var angVel = rand.nextInt(3) + 1

        if (rand.nextBoolean()) {
            angVel = angVel * -1
        }

        projectile.angularVelocity = angVel

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

    /** */

    /// <returns>
    /// Returns a collection of all of the live projectiles
    /// fired by the player.
    /// </returns>
    fun playerProjectiles(): Iterable<Projectile> {
        val playerProjs = Array<Projectile>(false, 16)

        for (projectile in entities) {
            if (!projectile.isEnemyProjectile && projectile.isAlive) {
                playerProjs.add(projectile)
            }
        }

        return playerProjs
    }

    /// <summary>
    /// Returns a collection of all of the live projectiles
    /// fired by an enemy.
    /// </summary>
    /// <returns></returns>
    fun enemyProjectiles(): Iterable<Projectile> {
        val enemyProjs = Array<Projectile>(false, 16)

        for (projectile in entities) {
            if (projectile.isEnemyProjectile && projectile.isAlive) {
                enemyProjs.add(projectile)
            }
        }

        return enemyProjs
    }
}/// <summary>
/// Places a laser at the specified location and fires it.
/// </summary>
/// <param name="x">The X value of the location</param>
/// <param name="y">The Y value of the location</param>
