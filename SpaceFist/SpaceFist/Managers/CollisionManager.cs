using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    class CollisionManager
    {
        // These are references to existing managers,
        // this class needs to get information about the explosions, blocks, lasers and the player
        // in order to determine if a collision has occurred.
        private ExplosionManager  explosionManager;
        private BlockManager      blockManager;
        private ProjectileManager laserManager;
        private PlayerManager     shipManager;
        private PickUpManager     pickupManager;
        private EnemyManager      enemyManager;
        private RoundData roundData;

        public CollisionManager(
            BlockManager      blockManager, 
            PlayerManager     shipManager, 
            ProjectileManager laserManager, 
            ExplosionManager  explosionManager,
            PickUpManager     pickupManager,
            EnemyManager      enemyManager,
            RoundData         roundData)
        {
            this.blockManager     = blockManager;
            this.shipManager      = shipManager;
            this.laserManager     = laserManager;
            this.explosionManager = explosionManager;
            this.pickupManager    = pickupManager;
            this.enemyManager     = enemyManager;
            this.roundData        = roundData;
        }
        
        public void Update()
        {
            HandleLaserRockCollisions();
            HandleShipRockCollisions();
            HandleShipPickupCollisions();
            HandleEnemyLaserCollisions();
        }

        public void HandleEnemyLaserCollisions()
        {
            foreach (var laser in laserManager)
            {
                foreach (var enemy in enemyManager.Collisions(laser))
                {
                    explosionManager.add(enemy.X, enemy.Y);
                    enemy.Alive = false;
                    shipManager.Scored();
                    roundData.EnemiesShot++;
                }
            }
        }

        public void HandleShipPickupCollisions()
        {
            foreach(var pickup in pickupManager.Collisions(shipManager.Ship)) {
                if (pickup.PickedUp(shipManager.Ship))
                {
                    pickup.Alive = false;
                }
            }
        }

        public void HandleLaserRockCollisions()
        {
            // Blocks which have collided with a laser
            var blocksToRemove = new List<SpaceBlock>();

            foreach (var laser in laserManager)
            {
                // Only process lasers that are still in play
                if (laser.Alive)
                {
                    // If an alive laser hits a block
                    foreach (var block in blockManager.collisions(laser))
                    {
                        // Create and add a new explosion
                        explosionManager.add(block.X, block.Y);

                        // Update the score
                        shipManager.Scored();
                        roundData.BlocksShot = roundData.BlocksShot++;

                        // Kill the block
                        block.Destroy();
                    }

                }
            }
        }

        public void HandleShipRockCollisions()
        {
            //  Update blocks
            foreach (var block in blockManager.collisions(shipManager.Ship))
            {
                // Ignore the collision if the ship is not alive
                if (shipManager.Alive)
                {
                    roundData.BlocksBumped++;
                    var ship = shipManager.Ship;

                    // Create an explosion at the coordinates of the block
                    explosionManager.add(block.X, block.Y);

                    // Notify the ship manager that the ship has been hit
                    shipManager.ShipHit();

                    // If the ship died, add an explosion where the ship was
                    if (!shipManager.Alive)
                    {
                        explosionManager.add(ship.X, ship.Y);
                    }

                    // Notify the block that it has been hit
                    block.Thump();
                }
            }
        }
    }
}
