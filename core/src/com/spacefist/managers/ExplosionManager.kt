package com.spacefist.managers

import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.entities.Explosion

/// <summary>
/// Keeps track of all of the explosions in the world.
/// </summary>
class ExplosionManager/// <summary>
/// Creates a new ExplosionManager instance.
/// </summary>
/// <param name="gameData">Common game data</param>
(gameData: GameData) : Manager<Explosion>(gameData) {
    /// <summary>
    /// Adds a new explosion at the specified location.
    /// </summary>
    /// <param name="x">The X component of the location</param>
    /// <param name="y">The Y component of the location</param>
    fun add(x: Int, y: Int) {
        add(Explosion(gameData, Vector2(x.toFloat(), y.toFloat())))
    }
}
