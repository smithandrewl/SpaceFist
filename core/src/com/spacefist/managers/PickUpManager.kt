package com.spacefist.managers

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.PickupHandler
import com.spacefist.RoundData
import com.spacefist.entities.Pickup
import com.spacefist.util.Action
import com.spacefist.weapons.Missile
import com.spacefist.weapons.SampleWeapon

/// <summary>
/// Keeps track of the pickups in the world and provides
/// methods to operate on them.
/// </summary>
class PickUpManager/// <summary>
/// Creates a new instance of PickupManager.
/// </summary>
/// <param name="gameData">Common game data</param>
(gameData: GameData) : Manager<Pickup>(gameData) {
    private val roundData: RoundData

    init {
        roundData = gameData.roundData
    }

    /// <summary>
    /// Spawns the specified number of pickups to the screen.
    /// </summary>
    /// <param name="count">The number of pickups to spawn</param>
    /// <param name="spawnFunction">A function to spawn a specific pickup type</param>
    fun spawnPickups(count: Int, spawnFunction: Action<Int, Int>) {
        val world = gameData.world

        for (i in 0 until count) {
            val randX = MathUtils.random(0f, world.getWidth()).toInt()
            val randY = MathUtils.random(0f, world.getHeight()).toInt()

            spawnFunction.execute(randX, randY)
        }
    }

    /// <summary>
    /// Spawns "count" rocket pickups to the world.
    /// </summary>
    /// <param name="count">The number of pickups to spawn</param>
    fun spawnExamplePickups(count: Int) {
        spawnPickups(count, Action { first, second -> spawnExamplePickup(first!!, second!!) })
    }

    /// <summary>
    /// Spawns a single rocket pickup at the specified location.
    /// </summary>
    /// <param name="x">The X value of the location</param>
    /// <param name="y">The Y value of the location</param>
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

    /// <summary>
    /// Spawns a number of health pickups to the world.
    /// </summary>
    /// <param name="count">The number of pickups to spawn</param>
    fun spawnHealthPickups(count: Int) {
        spawnPickups(count, Action { first, second -> spawnHealthPickup(first!!, second!!) })
    }


    fun spawnMissilePickups(count: Int) {
        spawnPickups(count, Action { first, second -> spawnMissilePickup(first!!, second!!) })
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

    /** */

    /// <summary>
    /// Spawns a single health pickup to the specified
    /// location.
    /// </summary>
    /// <param name="x">The X value of the location</param>
    /// <param name="y">The Y value of the location</param>
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

    /// <summary>
    /// Spawns extra life pickups to the world.
    /// </summary>
    /// <param name="count">The number of pickups to spawn</param>
    fun spawnExtraLifePickups(count: Int) {
        spawnPickups(count, Action { first, second -> spawnExtraLifePickup(first!!, second!!) })
    }

    /// <summary>
    /// spawn one extra life pickup at the specified location.
    /// </summary>
    /// <param name="x">The X value of the location</param>
    /// <param name="y">The Y value of the location</param>
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

    /// <summary>
    /// Removes all pickups from the world.
    /// </summary>
    fun reset() {
        clear()
    }
}
