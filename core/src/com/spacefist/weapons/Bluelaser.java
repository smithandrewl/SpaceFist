package com.spacefist.weapons;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.weapons.abst.Weapon;

/// <summary>
/// Represents a weapon which drops stationary mines when fired.
/// </summary>
public class Bluelaser implements Weapon
{
    // TODO: Convert ProjectileManager
    // private ProjectileManager projectileManager;
    private GameData gameData;

    /// <summary>
    /// Creates a new Bluelaser instance.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    /// <param name="ship">The players ship</param>
    public Bluelaser(GameData gameData)
    {
        this.gameData = gameData;

        // projectileManager = gameData.ProjectileManager;
    }

    /// <summary>
    /// Drops a mine at the ships location.
    /// </summary>
    public void fire()
    {
        Ship ship = gameData.getShip();

        int projectileX = (int)(ship.getX() + (ship.getRectangle().getWidth() / 2) - (20 * gameData.getScreenScale()));
        int projectileY = (int)((ship.getY() - (35 * gameData.getScreenScale())));

        // projectileManager.fireBluelaser(projectileX, projectileY);
    }
}
