package com.spacefist.weapons;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.weapons.abst.Weapon;

/**
 * Represents a weapon which fires intercepting rockets.
 */
public class SampleWeapon implements Weapon {
    // TODO: Convert ProjectileManager
    // private ProjectileManager projectileManager;

    private Ship     ship;
    private GameData gameData;

    /**
      * Creates a new SampleWeapon instance.
      *
      * @param gameData Common game data
      * @param ship The players ship
      */
    public SampleWeapon(GameData gameData, Ship ship) {
        this.gameData = gameData;
        this.ship     = ship;

        // projectileManager = gameData.ProjectileManager;
    }

    /**
     * Fires a rocket cluster
     */
    public void fire() {
        int projectileX = (int) (ship.getX() + (ship.getRectangle().getWidth() / 2) + 2);
        int projectileY = (int) ((ship.getY() - (35 * gameData.getScreenScale())));

        // projectileManager.fireSampleWeapon(projectileX, projectileY);
    }
}
