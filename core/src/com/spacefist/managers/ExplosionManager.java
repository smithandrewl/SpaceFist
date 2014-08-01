package com.spacefist.managers;

import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.entities.Explosion;

/// <summary>
/// Keeps track of all of the explosions in the world.
/// </summary>
public class ExplosionManager extends Manager<Explosion>
{
    /// <summary>
    /// Creates a new ExplosionManager instance.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    public ExplosionManager(GameData gameData) {
    super(gameData);
    this.gameData  = gameData;
}

    /// <summary>
    /// Adds a new explosion at the specified location.
    /// </summary>
    /// <param name="x">The X component of the location</param>
    /// <param name="y">The Y component of the location</param>
    public void Add(int x, int y)
    {
        Explosion explosion = new Explosion(gameData, new Vector2(x, y));
        Add(explosion);
    }
}
