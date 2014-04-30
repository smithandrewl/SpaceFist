using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using SpaceFist.Weapons;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    // Keeps track of the pickups in the world
    public class PickUpManager : IEnumerable<Pickup>
    {
        private Game         game;
        private List<Pickup> pickups;
        private RoundData    roundData;

        private Random rand = new Random();

        public PickUpManager(Game game, Rectangle screen)
        {
            this.game    = game;
            this.pickups = new List<Pickup>();
            roundData    = game.InPlayState.RoundData;
        }

        public void Update()
        {
            pickups.ForEach(pickup => pickup.Update());
        }

        public void Remove(Pickup pickup)
        {
            pickups.Remove(pickup);
        }

        public IEnumerable<Pickup> Collisions(Entity entity)
        {
            var collisions =
                from   pickup in pickups
                where  pickup.Alive && pickup.Rectangle.Intersects(entity.Rectangle)
                select pickup;

            return collisions;
        }

        public void Draw()
        {
            pickups.ForEach(pickup => pickup.Draw());
        }

        public IEnumerator<Pickup> GetEnumerator()
        {
            return pickups.GetEnumerator();
        }
       
        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return this.GetEnumerator();
        }

        public void SpawnPickups(int count, Action<int, int> spawnFunction)
        {
            var world = game.InPlayState.World;

            for (int i = 0; i < count; i++)
            {
                int randX = rand.Next(0, world.Width);
                int randY = rand.Next(0, world.Height);

                spawnFunction(randX, randY);
            }
        }

        public void SpawnExamplePickups(int count) {
            SpawnPickups(count, SpawnExamplePickup);
        }

        public void SpawnExamplePickup(int x, int y)
        {
            var pickup =
                new Pickup(
                    game,
                    game.WeaponPickupTexture,
                    game.WeaponPickupSound,
                    new Vector2(x, y),
                    Vector2.Zero,
                    (ship) => {
                        ship.Weapon = new SampleWeapon(game, ship);
                        return true;
                    });

            pickups.Add(pickup);
        }

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
                    game,
                    game.MinePickupTexture,
                    game.WeaponPickupSound,
                    new Vector2(x, y),
                    Vector2.Zero,
                    (ship) =>
                    {
                        ship.Weapon = new Bluelaser(game, ship);
                        return true;
                    });

            pickups.Add(pickup);
        }

        public void SpawnMissilePickups(int count)
        {
            SpawnPickups(count, SpawnMissilePickup);
        }

        public void SpawnMissilePickup(int x, int y)
        {
            var pickup = new Pickup(
                game,
                game.MissilePickupTexture,
                game.WeaponPickupSound,
                new Vector2(x, y),
                Vector2.Zero,
                (ship) =>
                {
                    ship.Weapon = new Missile(game, ship);

                    return true;
                }
            );

            pickups.Add(pickup);
        }
        /***********************/

        public void SpawnHealthPickup(int x, int y)
        {
            var pickup =
                new Pickup(
                    game,
                    game.HealthPickupTexture,
                    game.HealthPickupSound,
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

            pickups.Add(pickup);
        }

        public void SpawnExtraLifePickups(int count)
        {
            SpawnPickups(count, SpawnExtraLifePickup);
        }

        public void SpawnExtraLifePickup(int x, int y)
        {
            var pickup =
                new Pickup(
                    game,
                    game.ExtraLifePickupTexture,
                    game.ExtraLifeSound,
                    new Vector2(x, y),
                    Vector2.Zero,
                    (ship) =>
                    {               
                        roundData.Lives++;
                        return true;
                    });

            pickups.Add(pickup);
        }

        internal void Reset()
        {
            pickups.Clear();
        }
    }
}
