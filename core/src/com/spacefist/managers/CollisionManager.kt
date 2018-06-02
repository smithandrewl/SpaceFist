package com.spacefist.managers

import com.badlogic.gdx.math.Rectangle
import com.spacefist.GameData
import com.spacefist.RoundData
import com.spacefist.entities.EnemyMine
import com.spacefist.entities.Pickup
import com.spacefist.entities.Projectile
import com.spacefist.entities.Ship
import com.spacefist.entities.SpaceBlock
import com.spacefist.entities.enemies.Enemy

/// <summary>
/// Contains methods to handle the different types of entity and player collisions.
/// </summary>
class CollisionManager(
        private val gameData: GameData) {
    // These are references to existing managers,
    // this class needs to get information about the explosions, blocks, lasers and the player
    // in order to determine if a collision has occurred.
    private val explosionManager: ExplosionManager
    private val blockManager: BlockManager
    private val laserManager: ProjectileManager
    private val shipManager: PlayerManager
    private val pickupManager: PickUpManager
    private val enemyManager: EnemyManager
    private val enemyMineManager: EnemyMineManager

    private val roundData: RoundData

    init {

        blockManager = gameData.blockManager
        shipManager = gameData.playerManager
        laserManager = gameData.projectileManager
        explosionManager = gameData.explosionManager
        pickupManager = gameData.pickUpManager
        enemyManager = gameData.enemyManager
        enemyMineManager = gameData.enemyMineManager
        roundData = gameData.roundData
    }

    fun update() {
        handleLaserRockCollisions()
        handleShipRockCollisions()
        handleShipPickupCollisions()
        handleEnemyLaserCollisions()
        handleEnemyShipCollisions()
        handleEnemyRockCollisions()
        handleProjectileShipCollisions()
        handleShipEnemyMineCollisions()
    }

    private fun handleShipEnemyMineCollisions() {
        assert(gameData.ship != null)

        for (mine in enemyMineManager.collisions(gameData.ship)) {
            mine.isAlive = false
            mine.hit()
            explosionManager.add(mine.x, mine.y)
            shipManager.shipHit()
        }
    }

    /// <summary>
    /// Handles collisions between the ship and projectiles.
    /// </summary>
    fun handleProjectileShipCollisions() {
        for (projectile in laserManager.enemyProjectiles()) {
            if (projectile.rectangle.overlaps(gameData.ship.rectangle)) {
                projectile.isAlive = false
                // FIXME: Bug: Explosions draw off-center
                explosionManager.add(gameData.ship.x, gameData.ship.y)
                shipManager.shipHit()
            }
        }
    }

    /// <summary>
    /// Handles collisions between the ship and enemies.
    /// </summary>
    fun handleEnemyShipCollisions() {
        for (enemy in enemyManager.collisions(gameData.ship)) {
            explosionManager.add(enemy.x, enemy.y)
            enemy.isAlive = false
            enemy.onDeath()
            shipManager.shipHit()
        }
    }

    /// <summary>
    /// Handles collisions between the enemy and projectiles.
    /// </summary>
    fun handleEnemyLaserCollisions() {
        for (laser in laserManager.playerProjectiles()) {
            if (laser.isAlive) {
                for (enemy in enemyManager.collisions(laser)) {
                    laser.isAlive = false
                    explosionManager.add(enemy.x, enemy.y)
                    enemy.isAlive = false
                    enemy.onDeath()
                    shipManager.scored()
                    roundData.enemiesShot
                }
            }
        }
    }

    /// <summary>
    /// Handles collisions between the ship and weapon and health pickups.
    /// </summary>
    fun handleShipPickupCollisions() {
        for (pickup in pickupManager.collisions(gameData.ship)) {
            if (pickup.pickedUp(gameData.ship)) {
                pickup.isAlive = false
            }
        }
    }

    /// <summary>
    /// Handles collisions between the enemy and space blocks.
    /// </summary>
    fun handleEnemyRockCollisions() {
        val resolution = gameData.resolution

        val cameraRect = Rectangle(
                gameData.camera.x.toInt().toFloat(),
                gameData.camera.y.toInt().toFloat(),
                resolution.getWidth(),
                resolution.getHeight()
        )

        // Only process onscreen collisions
        for (enemy in enemyManager) {
            if (enemy.isAlive && cameraRect.contains(enemy.rectangle)) {
                for (block in blockManager.collisions(enemy)) {

                    if (cameraRect.contains(block.rectangle)) {
                        enemy.isAlive = false
                        enemy.onDeath()

                        explosionManager.add(block.x, block.y)
                        explosionManager.add(enemy.x, enemy.y)

                        block.destroy()
                    }
                }
            }
        }
    }

    /// <summary>
    /// Handles collisions between projectiles and space blocks.
    /// </summary>
    fun handleLaserRockCollisions() {
        for (laser in laserManager) {
            // Only process lasers that are still in play
            if (laser.isAlive) {
                // If an alive laser hits a block
                for (block in blockManager.collisions(laser)) {
                    laser.isAlive = false
                    // Create and add a new explosion
                    explosionManager.add(block.x, block.y)

                    // update the score
                    shipManager.scored()
                    roundData.blocksShot = roundData.blocksShot + 1

                    // Kill the block
                    block.destroy()
                }
            }
        }
    }

    /// <summary>
    /// Handles collisions between the ship and space blocks.
    /// </summary>
    fun handleShipRockCollisions() {
        //  update blocks
        for (block in blockManager.collisions(gameData.ship)) {
            // Ignore the collision if the ship is not alive
            if (shipManager.isAlive) {
                roundData.blocksBumped = roundData.blocksBumped + 1
                val ship = gameData.ship

                // Create an explosion at the coordinates of the block
                explosionManager.add(block.x, block.y)

                // Notify the ship manager that the ship has been hit
                shipManager.shipHit()

                // If the ship died, add an explosion where the ship was
                if (!shipManager.isAlive) {
                    explosionManager.add(ship.x, ship.y)
                }

                // Notify the block that it has been hit
                block.thump()
            }
        }
    }
}
