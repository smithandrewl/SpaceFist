package com.spacefist.weapons;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.managers.ProjectileManager;
import com.spacefist.weapons.abst.Weapon;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a weapon which drops stationary mines when fired.
 */
public class Bluelaser implements Weapon {
    private ProjectileManager projectileManager;
    private GameData gameData;

    /**
     * Creates a new Bluelaser instance.
     *
     * @param gameData Common game data
     */
    public Bluelaser(@NotNull GameData gameData) {
        this.gameData = gameData;

        projectileManager = gameData.getProjectileManager();
    }

    /**
     * Drops a mine at the ships location.
     */
    @Override
    public void fire() {
        Ship ship = gameData.getShip();

        float shipWidth = ship.getRectangle().getWidth();
        float shipHeight = ship.getRectangle().getHeight();


        int projectileX = (int) ((ship.getX() + (shipWidth / 2)) - (20 * gameData.getScreenScale()));
        int projectileY = (int) (ship.getY() + (shipHeight * 1.125f));

        projectileManager.fireBluelaser(projectileX, projectileY);
    }
}
