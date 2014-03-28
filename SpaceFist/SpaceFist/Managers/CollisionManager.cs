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
        private ExplosionManager explosionManager;
        private BlockManager     blockManager;
        private LaserManager     laserManager;
        private PlayerManager      shipManager;

        public CollisionManager(
            BlockManager     blockManager, 
            PlayerManager      shipManager, 
            LaserManager     laserManager, 
            ExplosionManager explosionManager)
        {
            this.blockManager     = blockManager;
            this.shipManager      = shipManager;
            this.laserManager     = laserManager;
            this.explosionManager = explosionManager;
        }
        
        public void Update()
        {
            HandleLaserRockCollisions();
            HandleShipRockCollisions();
        }

        public void Draw()
        {
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

                        // Kill the block
                        block.Destroy();
                        blocksToRemove.Add(block);
                    }

                }
            }
            // Tell the block manager to remove the blocks involved in the collision.
            blocksToRemove.ForEach(block => blockManager.Remove(block));
        }

        public void HandleShipRockCollisions()
        {
            // Blocks which have collided with the ship
            var blocksToRemove = new List<SpaceBlock>();

            //  Update blocks
            foreach (var block in blockManager.collisions(shipManager.Ship))
            {
                // Ignore the collision if the ship is not alive
                if (shipManager.Alive)
                {
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
                    blocksToRemove.Add(block);
                }
            }

            // Tell the block manager to remove the blocks involved in the collision
            blocksToRemove.ForEach(block => blockManager.Remove(block));
        }
    }
}
