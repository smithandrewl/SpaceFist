package com.spacefist.managers

import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.entities.Explosion

/**
 *Keeps track of all of the explosions in the world.
 *
 * @param name gameData Common game data.
 */
class ExplosionManager
(gameData: GameData) : Manager<Explosion>(gameData) {
    /**
     * Adds a new explosion at the specified location.
     *
     * @param x The X component of the location
     * @param y The Y component of the location
     */
    fun add(x: Int, y: Int) {
        add(Explosion(gameData, Vector2(x.toFloat(), y.toFloat())))
    }
}
