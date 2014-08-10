package com.spacefist.weapons;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.weapons.abst.Weapon;

// TODO: Uncomment projectile manager references when ProjectileManager has been ported
/**
 * The default weapon
 */
public class LaserWeapon implements Weapon {
    //private ProjectileManager projectileManager;

    private GameData gameData;

    /**
      * Creates a new LaserWeapon instance
      *
      * @param gameData Common game data
      */
    public LaserWeapon(GameData gameData) {
        this.gameData = gameData;

        //this.projectileManager = gameData.ProjectileManager;
    }

    public void fire() {
        Ship ship = gameData.getShip();

        float shipWidth = ship.getRectangle().getWidth();

        int projectileX = (int) (ship.getX() + (shipWidth / 2));
        int projectileY = (int) ((ship.getY() - (35 * gameData.getScreenScale())));

        //projectileManager.fireLaser(projectileX, projectileY);
    }
}
