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
        private Game      game;

        public CollisionManager(
            Game game,
            BlockManager      blockManager, 
            PlayerManager     shipManager, 
            ProjectileManager laserManager, 
            ExplosionManager  explosionManager,
            PickUpManager     pickupManager,
            EnemyManager      enemyManager,
            RoundData         roundData)
        {
            this.game             = game;
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
            HandleEnemyShipCollisions();
            HandleEnemyRockCollisions();
            HandleProjectileShipCollisions();
        }

        public void HandleProjectileShipCollisions()
        {
            foreach (Projectile projectile in laserManager.EnemyProjectiles())
            {
                if(projectile.Rectangle.Intersects(shipManager.Ship.Rectangle))
                {
                    projectile.Alive = false;
                    explosionManager.add(shipManager.Ship.X, shipManager.Ship.Y);
                    shipManager.ShipHit();
                }
            }
        }

        public void HandleEnemyShipCollisions()
        {
            foreach (Enemy enemy in enemyManager.Collisions(shipManager.Ship))
            {
                explosionManager.add(enemy.X, enemy.Y);
                enemy.Alive = false;
                enemy.OnDeath();
                shipManager.ShipHit();
            }
        }
     

        public void HandleEnemyLaserCollisions()
        {
            foreach (var laser in laserManager.PlayerProjectiles())
            {
                if (laser.Alive)
                {
                    foreach (Enemy enemy in enemyManager.Collisions(laser))
                    {
                        laser.Alive = false;
                        explosionManager.add(enemy.X, enemy.Y);
                        enemy.Alive = false;
                        enemy.OnDeath();
                        shipManager.Scored();
                        roundData.EnemiesShot++;
                    }
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

        public void HandleEnemyRockCollisions()
        {
            var resolution = game.Resolution;
            
            var cameraRect = new Rectangle(
                (int) game.InPlayState.Camera.X, 
                (int)game.InPlayState.Camera.Y, 
                resolution.Width, 
                resolution.Height
            );

            // Only process onscreen collisions
            foreach (var enemy in enemyManager)
            {
                if (enemy.Alive && cameraRect.Contains(enemy.Rectangle))
                {
                    foreach (var block in blockManager.Collisions(enemy))
                    {

                        if (cameraRect.Contains(block.Rectangle))
                        {
                            enemy.Alive = false;
                            enemy.OnDeath();

                            explosionManager.add(block.X, block.Y);
                            explosionManager.add(enemy.X, enemy.Y);

                            block.Destroy();
                        }
                    }
                }
            }
        }

        public void HandleLaserRockCollisions()
        {
            foreach (var laser in laserManager)
            {
                // Only process lasers that are still in play
                if (laser.Alive)
                {
                    // If an alive laser hits a block
                    foreach (var block in blockManager.Collisions(laser))
                    {
                        laser.Alive = false;
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
            foreach (var block in blockManager.Collisions(shipManager.Ship))
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
