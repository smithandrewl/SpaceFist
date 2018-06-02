package com.spacefist.weapons

import com.spacefist.GameData
import com.spacefist.entities.Ship
import com.spacefist.managers.ProjectileManager
import com.spacefist.weapons.abst.Weapon

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
