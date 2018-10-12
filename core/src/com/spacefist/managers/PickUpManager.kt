package com.spacefist.managers

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.PickupHandler
import com.spacefist.RoundData
import com.spacefist.entities.Pickup
import com.spacefist.weapons.Missile
import com.spacefist.weapons.SampleWeapon

/**
 * Keeps track of the pickups in the world and provides
 * methods to operate on them.
 *
 * @param gameData Common game data.
 */
class PickUpManager
(gameData: GameData) : Manager<Pickup>(gameData) {
    private val roundData: RoundData = gameData.roundData

    /**
     *  Spawns the specified number of pickups to the screen.
     *  @param count The number of pickups to spawn
     *  @param spawnFunction A function to spawn a specific pickup type
     */
    fun spawnPickups(count: Int, spawnFunction: (Int, Int)-> Unit ) {
        val world = gameData.world

        (0 until count).forEach { i ->
            val randX = MathUtils.random(0f, world.getWidth()).toInt()
            val randY = MathUtils.random(0f, world.getHeight()).toInt()

            spawnFunction(randX, randY)
        }
    }

    /**
     *  Spawns "count" rocket pickups to the world.
     *
     *  @param count The number of pickups to spawn.
     */
    fun spawnExamplePickups(count: Int) {
        spawnPickups(count) { first, second -> spawnExamplePickup(first, second) }
    }

    /**
     *  Spawns a single rocket pickup at the specified location.
     *
     *  @param x The X value of the location
     *  @param y The Y value of the location
     */
    fun spawnExamplePickup(x: Int, y: Int) {
        val pickup = Pickup(
                gameData,
                gameData.textures["WeaponPickup"]!!,
                gameData.soundEffects["WeaponPickup"]!!,
                Vector2(x.toFloat(), y.toFloat()),
                Vector2.Zero,
                PickupHandler { ship ->
                    ship.weapon = SampleWeapon(gameData, gameData.ship)
                    true
                })

        add(pickup)
    }

    /**
     *  Spawns a number of health pickups to the world.
     *
     *  @param count The number of pickups to spawn.
     */
    fun spawnHealthPickups(count: Int) {
        spawnPickups(count) { first, second -> spawnHealthPickup(first, second) }
    }


    fun spawnMissilePickups(count: Int) {
        spawnPickups(count) { first, second -> spawnMissilePickup(first, second) }
    }

    fun spawnMissilePickup(x: Int, y: Int) {
        val pickup = Pickup(
                gameData,
                gameData.textures["MissilePickUp"]!!,
                gameData.soundEffects["WeaponPickup"]!!,
                Vector2(x.toFloat(), y.toFloat()),
                Vector2.Zero,
                PickupHandler { ship ->
                    ship.weapon = Missile(gameData)
                    true
                }
        )

        add(pickup)
    }

    /**
     *  Spawns a single health pickup to the specified
     *  location.
     *
     *  @param x The X value of the location.
     *  @param y The Y value of the location.
     */
    fun spawnHealthPickup(x: Int, y: Int) {
        val pickup = Pickup(
                gameData,
                gameData.textures["HealthPickup"]!!,
                gameData.soundEffects["HealthPickup"]!!,
                Vector2(x.toFloat(), y.toFloat()),
                Vector2.Zero,
                PickupHandler { ship ->
                    if (ship.health < 1) {
                        ship.healthPoints = 100
                        ship.resetState()
                        return@PickupHandler true
                    }

                    false
                })

        add(pickup)
    }

    /**
     *  Spawns extra life pickups to the world.
     *
     *  @param count The number of pickups to spawn.
     */
    fun spawnExtraLifePickups(count: Int) {
        spawnPickups(count) { first, second -> spawnExtraLifePickup(first, second) }
    }

    /**
     *  spawn one extra life pickup at the specified location.
     *
     *  @param x The X value of the location.
     *  @param y The y value of the location.
     */
    fun spawnExtraLifePickup(x: Int, y: Int) {
        val pickup = Pickup(
                gameData,
                gameData.textures["ExtraLifePickup"]!!,
                gameData.soundEffects["ExtraLife"]!!,
                Vector2(x.toFloat(), y.toFloat()),
                Vector2.Zero,
                PickupHandler {
                    roundData.lives = roundData.lives + 1
                    true
                })

        add(pickup)
    }

    /**
     *  Removes all pickups from the world.
     */
    fun reset() {
        clear()
    }
}
