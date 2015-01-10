package com.spacefist.managers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.PickupHandler;
import com.spacefist.RoundData;
import com.spacefist.entities.Pickup;
import com.spacefist.entities.Ship;
import com.spacefist.util.Action;
import com.spacefist.weapons.Bluelaser;
import com.spacefist.weapons.Missile;
import com.spacefist.weapons.SampleWeapon;
import org.jetbrains.annotations.NotNull;

/// <summary>
/// Keeps track of the pickups in the world and provides
/// methods to operate on them.
/// </summary>
public class PickUpManager extends Manager<Pickup>
{
    private RoundData roundData;

    /// <summary>
    /// Creates a new instance of PickupManager.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    public PickUpManager(@NotNull GameData gameData) {
        super(gameData);
        roundData    = gameData.getRoundData();
    }

    /// <summary>
    /// Spawns the specified number of pickups to the screen.
    /// </summary>
    /// <param name="count">The number of pickups to spawn</param>
    /// <param name="spawnFunction">A function to spawn a specific pickup type</param>
    public void spawnPickups(int count, @NotNull Action<Integer, Integer> spawnFunction)
    {
        Rectangle world = gameData.getWorld();

        for (int i = 0; i < count; i++)
        {
            int randX = (int) MathUtils.random(0, world.getWidth());
            int randY = (int) MathUtils.random(0, world.getHeight());

            spawnFunction.execute(randX, randY);
        }
    }

    /// <summary>
    /// Spawns "count" rocket pickups to the world.
    /// </summary>
    /// <param name="count">The number of pickups to spawn</param>
    public void spawnExamplePickups(int count) {
        spawnPickups(count, new Action<Integer, Integer>() {
            @Override
            public void execute(Integer first, Integer second) {
                spawnExamplePickup(first, second);
            }
        });
    }

    /// <summary>
    /// Spawns a single rocket pickup at the specified location.
    /// </summary>
    /// <param name="x">The X value of the location</param>
    /// <param name="y">The Y value of the location</param>
    public void spawnExamplePickup(int x, int y)
    {
        Pickup pickup = new Pickup(
            gameData,
            gameData.getTextures().get("WeaponPickup"),
            gameData.getSoundEffects().get("WeaponPickup"),
            new Vector2(x, y),
            Vector2.Zero,
            new PickupHandler() {
                @Override
                public boolean handle(@NotNull Ship ship) {
                    ship.setWeapon(new SampleWeapon(gameData, gameData.getShip()));
                    return true;
                }
            });

        add(pickup);
    }

    /// <summary>
    /// Spawns a number of health pickups to the world.
    /// </summary>
    /// <param name="count">The number of pickups to spawn</param>
    public void spawnHealthPickups(int count)
    {
        spawnPickups(count, new Action<Integer, Integer>() {
            @Override
            public void execute(Integer first, Integer second) {
                spawnHealthPickup(first, second);
            }
        });
    }

    /****Dongcai***/
    public void spawnLaserbeamPickups(int count)
    {
        spawnPickups(count, new Action<Integer, Integer>() {
            @Override
            public void execute(Integer first, Integer second) {
                spawnLaserbeamPickup(first, second);
            }
        });
    }

    public void spawnLaserbeamPickup(int x, int y)
    {
        Pickup pickup = new Pickup(
            gameData,
            gameData.getTextures().get("MinePickup"),
            gameData.getSoundEffects().get("WeaponPickup"),
            new Vector2(x, y),
            Vector2.Zero,
            new PickupHandler() {
                @Override
                public boolean handle(@NotNull Ship ship) {
                    ship.setWeapon(new Bluelaser(gameData));
                    return true;
                }
            });

        add(pickup);
    }

    public void spawnMissilePickups(int count)
    {
        spawnPickups(count, new Action<Integer, Integer>() {
            @Override
            public void execute(Integer first, Integer second) {
                spawnMissilePickup(first, second);
            }
        });
    }

    public void spawnMissilePickup(int x, int y)
    {
        Pickup pickup = new Pickup(
            gameData,
            gameData.getTextures().get("MissilePickUp"),
            gameData.getSoundEffects().get("WeaponPickup"),
            new Vector2(x, y),
            Vector2.Zero,
            new PickupHandler() {
                @Override
                public boolean handle(@NotNull Ship ship) {
                    ship.setWeapon(new Missile(gameData));
                    return true;
                }
            }
        );

        add(pickup);
    }
    /***********************/

    /// <summary>
    /// Spawns a single health pickup to the specified
    /// location.
    /// </summary>
    /// <param name="x">The X value of the location</param>
    /// <param name="y">The Y value of the location</param>
    public void spawnHealthPickup(int x, int y)
    {
        Pickup pickup = new Pickup(
            gameData,
            gameData.getTextures().get("HealthPickup"),
            gameData.getSoundEffects().get("HealthPickup"),
            new Vector2(x, y),
            Vector2.Zero,
            new PickupHandler() {
                @Override
                public boolean handle(@NotNull Ship ship) {
                    if (ship.getHealth() < 1) {
                        ship.setHealthPoints(100);
                        ship.resetState();
                        return true;
                    }

                    return false;
                }
            });

        add(pickup);
    }

    /// <summary>
    /// Spawns extra life pickups to the world.
    /// </summary>
    /// <param name="count">The number of pickups to spawn</param>
    public void spawnExtraLifePickups(int count)
    {
        spawnPickups(count, new Action<Integer, Integer>() {
            @Override
            public void execute(Integer first, Integer second) {
                spawnExtraLifePickup(first, second);
            }
        });
    }

    /// <summary>
    /// spawn one extra life pickup at the specified location.
    /// </summary>
    /// <param name="x">The X value of the location</param>
    /// <param name="y">The Y value of the location</param>
    public void spawnExtraLifePickup(int x, int y)
    {
        Pickup pickup = new Pickup(
            gameData,
            gameData.getTextures().get("ExtraLifePickup"),
            gameData.getSoundEffects().get("ExtraLife"),
            new Vector2(x, y),
            Vector2.Zero,
            new PickupHandler() {
                @Override
                public boolean handle(Ship ship) {
                    roundData.setLives(roundData.getLives() + 1);
                    return true;
                }
            });

        add(pickup);
    }

    /// <summary>
    /// Removes all pickups from the world.
    /// </summary>
    public void reset()
    {
        clear();
    }
}
