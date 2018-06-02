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

        val shipWidth = ship.rectangle.getWidth()
        val shipHeight = ship.rectangle.getWidth()

        val projectileX = (ship.x + shipWidth / 2 - 6).toInt()
        val projectileY = (ship.y + shipHeight * 1.5f).toInt()

        projectileManager.fireMissile(projectileX, projectileY)
    }
}
/***Doncai */

/**
 * Represents a weapon which fires intercepting rockets.
 */
class SampleWeapon
/**
 * Creates a new SampleWeapon instance.
 *
 * @param gameData Common game data
 * @param ship The players ship
 */
(private val gameData: GameData, private val ship: Ship) : Weapon {
    private val projectileManager: ProjectileManager

    init {

        projectileManager = gameData.projectileManager
    }

    /**
     * Fires a rocket cluster
     */
    override fun fire() {
        val projectileX = (ship.x.toFloat() + ship.rectangle.getWidth() / 2 + 2f).toInt()
        val projectileY = (ship.y - 35 * gameData.screenScale).toInt()

        projectileManager.fireSampleWeapon(projectileX, projectileY)
    }
}
