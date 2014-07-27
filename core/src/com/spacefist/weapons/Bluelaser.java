package com.spacefist.weapons;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.weapons.abst.Weapon;

/**
 * Represents a weapon which drops stationary mines when fired.
 */
public class Bluelaser implements Weapon {
    // TODO: Convert ProjectileManager
    // private ProjectileManager projectileManager;
    private GameData gameData;

    /**
     * Creates a new Bluelaser instance.
     *
     * @param gameData Common game data
     */
    public Bluelaser(GameData gameData) {
        this.gameData = gameData;

        // projectileManager = gameData.ProjectileManager;
    }

    /**
     * Drops a mine at the ships location.
     */
    public void fire() {
        Ship ship = gameData.getShip();

        int projectileX = (int) (ship.getX() + (ship.getRectangle().getWidth() / 2) - (20 * gameData.getScreenScale()));
        int projectileY = (int) ((ship.getY() - (35 * gameData.getScreenScale())));

        // projectileManager.fireBluelaser(projectileX, projectileY);
    }
}
