package com.spacefist.weapons

import com.spacefist.GameData
import com.spacefist.entities.Ship
import com.spacefist.managers.ProjectileManager
import com.spacefist.weapons.abst.Weapon

/**
 * The default weapon
 *
 * @param gameData Common game data
 */
class LaserWeapon(private val gameData: GameData) : Weapon {
    private val projectileManager: ProjectileManager

    init {
        this.projectileManager = gameData.projectileManager
    }

    override fun fire() {
        val ship = gameData.ship

        val shipWidth  = ship.rectangle!!.getWidth()
        val shipHeight = ship.rectangle!!.getHeight()

        val projectileX = (ship.x + shipWidth / 2).toInt()
        val projectileY = (ship.y + shipHeight).toInt()

        projectileManager.fireLaser(projectileX, projectileY)
    }
}

/**
 * Represents a cluster of three missiles.
 */
class Missile(private val gameData: GameData) : Weapon {
    private val projectileManager: ProjectileManager

    init {
        projectileManager = gameData.projectileManager
    }

    /**
     * Fires a missile in the direction the ship is facing.
     */
    override fun fire() {
        val ship = gameData.ship

        val shipWidth = ship.rectangle!!.getWidth()
        val shipHeight = ship.rectangle!!.getWidth()

        val projectileX = (ship.x + shipWidth  / 2 - 6).toInt()
        val projectileY = (ship.y + shipHeight * 1.5f).toInt()

        projectileManager.fireMissile(projectileX, projectileY)
    }
}
/***Doncai */



