package com.spacefist.weapons

import com.spacefist.GameData
import com.spacefist.entities.Ship
import com.spacefist.managers.ProjectileManager
import com.spacefist.weapons.abst.Weapon

/**
 * The default weapon
 */
class LaserWeapon
/**
 * Creates a new LaserWeapon instance
 *
 * @param gameData Common game data
 */
(private val gameData: GameData) : Weapon {
    private val projectileManager: ProjectileManager

    init {

        this.projectileManager = gameData.projectileManager
    }

    override fun fire() {
        val ship = gameData.ship

        val shipWidth = ship.rectangle.getWidth()
        val shipHeight = ship.rectangle.getHeight()

        val projectileX = (ship.x + shipWidth / 2).toInt()
        val projectileY = (ship.y + shipHeight).toInt()

        projectileManager.fireLaser(projectileX, projectileY)
    }
}
