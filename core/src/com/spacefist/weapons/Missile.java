/***Doncai******/
package com.spacefist.weapons;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.weapons.abst.Weapon;

/**
 * Represents a cluster of three missiles.
 */
class Missile implements Weapon {
    // TODO: Convert ProjectileManager

    // private ProjectileManager projectileManager;
    private GameData gameData;

    public Missile(GameData gameData) {
        this.gameData = gameData;

        //projectileManager = gameData.ProjectileManager;
    }

    /**
     * Fires a missile in the direction the ship is facing.
     */
    public void fire() {
        Ship ship = gameData.getShip();

        float shipWidth = ship.getRectangle().getWidth();

        int projectileX = (int) (ship.getX() + (shipWidth / 2) - 6);
        int projectileY = (int) ((ship.getY() - (35 * gameData.getScreenScale())));

        // projectileManager.fireMissile(projectileX, projectileY);
    }
}
/***Doncai******/