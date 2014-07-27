package com.spacefist.weapons;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.weapons.abst.Weapon;

/// <summary>
/// Represents a weapon which fires intercepting rockets.
/// </summary>
public class SampleWeapon implements Weapon {
    // TODO: Convert ProjectileManager
    // private ProjectileManager projectileManager;

    private Ship     ship;
    private GameData gameData;

    /// <summary>
    /// Creates a new SampleWeapon instance.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    /// <param name="ship">The players ship</param>
    public SampleWeapon(GameData gameData, Ship ship) {
        this.gameData = gameData;
        this.ship     = ship;

        // projectileManager = gameData.ProjectileManager;
    }

    /// <summary>
    /// Fires a rocket cluster
    /// </summary>
    public void fire() {
        int projectileX = (int) (ship.getX() + (ship.getRectangle().getWidth() / 2) + 2);
        int projectileY = (int) ((ship.getY() - (35 * gameData.getScreenScale())));

        // projectileManager.fireSampleWeapon(projectileX, projectileY);
    }
}
