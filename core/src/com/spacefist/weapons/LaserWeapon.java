package com.spacefist.weapons;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.weapons.abst.Weapon;

// TODO: Uncomment projectile manager references when ProjectileManager has been ported
/// <summary>
/// The default weapon
/// </summary>
public class LaserWeapon implements Weapon {
    //private ProjectileManager projectileManager;

    private GameData gameData;

    /// <summary>
    /// Creates a new LaserWeapon instance
    /// </summary>
    /// <param name="gameData">Common game data</param>
    public LaserWeapon(GameData gameData) {
        this.gameData = gameData;

        //this.projectileManager = gameData.ProjectileManager;
    }

    public void fire() {
        Ship ship = gameData.getShip();

        int projectileX = (int) (ship.getX() + (ship.getRectangle().getWidth() / 2));
        int projectileY = (int) ((ship.getY() - (35 * gameData.getScreenScale())));

        //projectileManager.fireLaser(projectileX, projectileY);
    }
}
