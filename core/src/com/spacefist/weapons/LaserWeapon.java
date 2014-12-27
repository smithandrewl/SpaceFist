package com.spacefist.weapons;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.managers.ProjectileManager;
import com.spacefist.weapons.abst.Weapon;
import org.jetbrains.annotations.NotNull;

/**
 * The default weapon
 */
public class LaserWeapon implements Weapon {
    private ProjectileManager projectileManager;

    private GameData gameData;

    /**
      * Creates a new LaserWeapon instance
      *
      * @param gameData Common game data
      */
    @SuppressWarnings("UnnecessaryThis")
    public LaserWeapon(@NotNull GameData gameData) {
        this.gameData = gameData;

        this.projectileManager = gameData.getProjectileManager();
    }

    @Override
    public void fire() {
        Ship ship = gameData.getShip();

        float shipWidth = ship.getRectangle().getWidth();
        float shipHeight = ship.getRectangle().getHeight();

        int projectileX = (int) (ship.getX() + (shipWidth / 2));
        int projectileY = (int) ((ship.getY() + shipHeight));

        projectileManager.fireLaser(projectileX, projectileY);
    }
}
