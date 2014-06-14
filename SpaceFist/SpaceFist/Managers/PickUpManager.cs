using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using SpaceFist.Weapons;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    /// <summary>
    /// Keeps track of the pickups in the world and provides
    /// methods to operate on them.
    /// </summary>
    public class PickUpManager : Manager<Pickup>
    {
        private RoundData    roundData;

        private Random rand = new Random();

        /// <summary>
        /// Creates a new instance of PickupManager.
        /// </summary>
        /// <param name="gameData">Common game data</param>
        public PickUpManager(GameData gameData): base(gameData)
        {
            roundData    = gameData.RoundData;
        }

        /// <summary>
        /// Spawns the specified number of pickups to the screen.
        /// </summary>
        /// <param name="count">The number of pickups to spawn</param>
        /// <param name="spawnFunction">A function to spawn a specific pickup type</param>
        public void SpawnPickups(int count, Action<int, int> spawnFunction)
        {
            var world = gameData.World;

            for (int i = 0; i < count; i++)
            {
                int randX = rand.Next(0, world.Width);
                int randY = rand.Next(0, world.Height);

                spawnFunction(randX, randY);
            }
        }

        /// <summary>
        /// Spawns "count" rocket pickups to the world.
        /// </summary>
        /// <param name="count">The number of pickups to spawn</param>
        public void SpawnExamplePickups(int count) {
            SpawnPickups(count, SpawnExamplePickup);
        }

        /// <summary>
        /// Spawns a single rocket pickup at the specified location.
        /// </summary>
        /// <param name="x">The X value of the location</param>
        /// <param name="y">The Y value of the location</param>
        public void SpawnExamplePickup(int x, int y)
        {
            var pickup =
                new Pickup(
                    gameData,
                    gameData.Textures["WeaponPickup"],
                    gameData.SoundEffects["WeaponPickup"],
                    new Vector2(x, y),
                    Vector2.Zero,
                    (ship) => {
                        ship.Weapon = new SampleWeapon(gameData, ship);
                        return true;
                    });

            Add(pickup);
        }

        /// <summary>
        /// Spawns a number of health pickups to the world.
        /// </summary>
        /// <param name="count">The number of pickups to spawn</param>
        public void SpawnHealthPickups(int count)
        {
            SpawnPickups(count, SpawnHealthPickup);
        }

        /****Dongcai***/
        public void SpawnLaserbeamPickups(int count)
        {
            SpawnPickups(count, SpawnLaserbeamPickup);
        }

        public void SpawnLaserbeamPickup(int x, int y)
        {
            var pickup =
                new Pickup(
                    gameData,
                    gameData.Textures["MinePickup"],
                    gameData.SoundEffects["WeaponPickup"],
                    new Vector2(x, y),
                    Vector2.Zero,
                    (ship) =>
                    {
                        ship.Weapon = new Bluelaser(gameData);
                        return true;
                    });

            Add(pickup);
        }

        public void SpawnMissilePickups(int count)
        {
            SpawnPickups(count, SpawnMissilePickup);
        }

        public void SpawnMissilePickup(int x, int y)
        {
            var pickup = new Pickup(
                gameData,
                gameData.Textures["MissilePickUp"],
                gameData.SoundEffects["WeaponPickup"],
                new Vector2(x, y),
                Vector2.Zero,
                (ship) =>
                {
                    ship.Weapon = new Missile(gameData);

                    return true;
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
            var pickup =
                new Pickup(
                    gameData,
                    gameData.Textures["HealthPickup"],
                    gameData.SoundEffects["HealthPickup"],
                    new Vector2(x, y),
                    Vector2.Zero,
                    (ship) => {
                        if(ship.Health < 1) {
                            ship.HealthPoints = 100;
                            ship.ResetState();
                            return true;
                        }

                        return false;
                    });

            Add(pickup);
        }

        /// <summary>
        /// Spawns extra life pickups to the world.
        /// </summary>
        /// <param name="count">The number of pickups to spawn</param>
        public void SpawnExtraLifePickups(int count)
        {
            SpawnPickups(count, SpawnExtraLifePickup);
        }

        /// <summary>
        /// Spawn one extra life pickup at the specified location.
        /// </summary>
        /// <param name="x">The X value of the location</param>
        /// <param name="y">The Y value of the location</param>
        public void SpawnExtraLifePickup(int x, int y)
        {
            var pickup =
                new Pickup(
                    gameData,
                    gameData.Textures["ExtraLifePickup"],
                    gameData.SoundEffects["ExtraLife"],
                    new Vector2(x, y),
                    Vector2.Zero,
                    (ship) =>
                    {               
                        roundData.Lives++;
                        return true;
                    });

            Add(pickup);
        }

        /// <summary>
        /// Removes all pickups from the world.
        /// </summary>
        internal void Reset()
        {
            Clear();
        }
    }
}
