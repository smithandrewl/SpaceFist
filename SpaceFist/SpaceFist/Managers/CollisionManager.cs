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
        
        public void Update(GameTime gameTime)
        {
            HandleLaserRockCollisions();
            HandleShipRockCollisions();
        }

        public void Draw(GameTime gameTime)
        {
        }

        public void HandleLaserRockCollisions()
        {
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
            blocksToRemove.ForEach(block => blockManager.Remove(block));
        }

        public void HandleShipRockCollisions()
        {
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

                    shipManager.ShipHit();

                    if (!shipManager.Alive)
                    {
                        explosionManager.add(ship.X, ship.Y);
                    }

                    // Notify the block that it has been hit
                    block.Thump();
                    blocksToRemove.Add(block);
                }
            }

            blocksToRemove.ForEach(block => blockManager.Remove(block));
        }
    }
}
