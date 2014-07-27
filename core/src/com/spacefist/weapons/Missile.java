/***Doncai******/
package com.spacefist.weapons;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.weapons.abst.Weapon;

/// <summary>
/// Represents a cluster of three missiles.
/// </summary>
class Missile implements Weapon {
    // TODO: Convert ProjectileManager

    // private ProjectileManager projectileManager;
    private GameData gameData;

    public Missile(GameData gameData) {
        this.gameData = gameData;

        //projectileManager = gameData.ProjectileManager;
    }

    /// <summary>
    /// Fires a missile in the direction the ship is facing.
    /// </summary>
    public void fire() {
        Ship ship = gameData.getShip();

        int projectileX = (int) (ship.getX() + (ship.getRectangle().getWidth() / 2) - 6);
        int projectileY = (int) ((ship.getY() - (35 * gameData.getScreenScale())));

        // projectileManager.fireMissile(projectileX, projectileY);
    }
}
/***Doncai******/