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

import java.util.Random;

/// <summary>
/// Keeps track of the pickups in the world and provides
/// methods to operate on them.
/// </summary>
public class PickUpManager extends Manager<Pickup>
{
    private RoundData roundData;

    private Random rand = new Random();

    /// <summary>
    /// Creates a new instance of PickupManager.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    public PickUpManager(GameData gameData) {
        super(gameData);
        roundData    = gameData.getRoundData();
    }

    /// <summary>
    /// Spawns the specified number of pickups to the screen.
    /// </summary>
    /// <param name="count">The number of pickups to spawn</param>
    /// <param name="spawnFunction">A function to spawn a specific pickup type</param>
    public void SpawnPickups(int count, Action<Integer, Integer> spawnFunction)
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
    public void SpawnExamplePickups(int count) {
        SpawnPickups(count, new Action<Integer, Integer> () {
            public void execute(Integer first, Integer second) {
                SpawnExamplePickup(first, second);
            }
        });
    }

    /// <summary>
    /// Spawns a single rocket pickup at the specified location.
    /// </summary>
    /// <param name="x">The X value of the location</param>
    /// <param name="y">The Y value of the location</param>
    public void SpawnExamplePickup(int x, int y)
    {
        Pickup pickup = new Pickup(
            gameData,
            gameData.getTextures().get("WeaponPickup"),
            gameData.getSoundEffects().get("WeaponPickup"),
            new Vector2(x, y),
            Vector2.Zero,
            new PickupHandler() {
                @Override
                public boolean handle(Ship ship) {
                    ship.setWeapon(new SampleWeapon(gameData, gameData.getShip()));
                    return true;
                }
            });

        Add(pickup);
    }

    /// <summary>
    /// Spawns a number of health pickups to the world.
    /// </summary>
    /// <param name="count">The number of pickups to spawn</param>
    public void SpawnHealthPickups(int count)
    {
        SpawnPickups(count,  new Action<Integer, Integer>() {
            public void execute(Integer first, Integer second) {
                SpawnHealthPickup(first, second);
            }
        });
    }

    /****Dongcai***/
    public void SpawnLaserbeamPickups(int count)
    {
        SpawnPickups(count, new Action<Integer, Integer>() {
            public void execute(Integer first, Integer second) {
                SpawnLaserbeamPickup(first, second);
            }
        });
    }

    public void SpawnLaserbeamPickup(int x, int y)
    {
        Pickup pickup = new Pickup(
            gameData,
            gameData.getTextures().get("MinePickup"),
            gameData.getSoundEffects().get("WeaponPickup"),
            new Vector2(x, y),
            Vector2.Zero,
            new PickupHandler() {
                @Override
                public boolean handle(Ship ship) {
                    ship.setWeapon(new Bluelaser(gameData));
                    return true;
                }
            });

        Add(pickup);
    }

    public void SpawnMissilePickups(int count)
    {
        SpawnPickups(count, new Action<Integer, Integer>() {
            public void execute(Integer first, Integer second) {
                SpawnMissilePickup(first, second);
            }
        });
    }

    public void SpawnMissilePickup(int x, int y)
    {
        Pickup pickup = new Pickup(
            gameData,
            gameData.getTextures().get("MissilePickUp"),
            gameData.getSoundEffects().get("WeaponPickup"),
            new Vector2(x, y),
            Vector2.Zero,
            new PickupHandler() {
                @Override
                public boolean handle(Ship ship) {
                    ship.setWeapon(new Missile(gameData));
                    return true;
                }
            }
        );

        Add(pickup);
    }
    /***********************/

    /// <summary>
    /// Spawns a single health pickup to the specified
    /// location.
    /// </summary>
    /// <param name="x">The X value of the location</param>
    /// <param name="y">The Y value of the location</param>
    public void SpawnHealthPickup(int x, int y)
    {
        Pickup pickup = new Pickup(
            gameData,
            gameData.getTextures().get("HealthPickup"),
            gameData.getSoundEffects().get("HealthPickup"),
            new Vector2(x, y),
            Vector2.Zero,
            new PickupHandler() {
                public boolean handle(Ship ship) {
                    if (ship.getHealth() < 1) {
                        ship.setHealthPoints(100);
                        ship.resetState();
                        return true;
                    }

                    return false;
                }
            });

        Add(pickup);
    }

    /// <summary>
    /// Spawns extra life pickups to the world.
    /// </summary>
    /// <param name="count">The number of pickups to spawn</param>
    public void SpawnExtraLifePickups(int count)
    {
        SpawnPickups(count, new Action<Integer, Integer> () {
            public void execute(Integer first, Integer second) {
                SpawnExtraLifePickup(first, second);
            }
        });
    }

    /// <summary>
    /// Spawn one extra life pickup at the specified location.
    /// </summary>
    /// <param name="x">The X value of the location</param>
    /// <param name="y">The Y value of the location</param>
    public void SpawnExtraLifePickup(int x, int y)
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

        Add(pickup);
    }

    /// <summary>
    /// Removes all pickups from the world.
    /// </summary>
    public void Reset()
    {
        Clear();
    }
}
