using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    /// <summary>
    /// Contains methods to handle the different types of entity and player collisions.
    /// </summary>
    public class CollisionManager
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
        private EnemyMineManager  enemyMineManager;

        private RoundData roundData;
        private GameData      gameData;

        public CollisionManager(
            GameData          gameData)
        {
            this.gameData         = gameData;
            this.blockManager     = gameData.BlockManager;
            this.shipManager      = gameData.PlayerManager;
            this.laserManager     = gameData.ProjectileManager;
            this.explosionManager = gameData.ExplosionManager;
            this.pickupManager    = gameData.PickUpManager;
            this.enemyManager     = gameData.EnemyManager;
            this.enemyMineManager = gameData.EnemyMineManager;
            this.roundData        = gameData.RoundData;
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
            HandleShipEnemyMineCollisions();
        }

        private void HandleShipEnemyMineCollisions()
        {
            foreach (var mine in enemyMineManager.Collisions(gameData.Ship))
            {
                mine.Alive = false;
                mine.Hit();
                explosionManager.Add(mine.X, mine.Y);
                shipManager.ShipHit();
            }
        }

        /// <summary>
        /// Handles collisions between the ship and projectiles.
        /// </summary>
        public void HandleProjectileShipCollisions()
        {
            foreach (Projectile projectile in laserManager.EnemyProjectiles())
            {
                if(projectile.Rectangle.Intersects(gameData.Ship.Rectangle))
                {
                    projectile.Alive = false;
                    explosionManager.Add(gameData.Ship.X, gameData.Ship.Y);
                    shipManager.ShipHit();
                }
            }
        }

        /// <summary>
        /// Handles collisions between the ship and enemies.
        /// </summary>
        public void HandleEnemyShipCollisions()
        {
            foreach (Enemy enemy in enemyManager.Collisions(gameData.Ship))
            {
                explosionManager.Add(enemy.X, enemy.Y);
                enemy.Alive = false;
                enemy.OnDeath();
                shipManager.ShipHit();
            }
        }
     
        /// <summary>
        /// Handles collisions between the enemy and projectiles.
        /// </summary>
        public void HandleEnemyLaserCollisions()
        {
            foreach (var laser in laserManager.PlayerProjectiles())
            {
                if (laser.Alive)
                {
                    foreach (Enemy enemy in enemyManager.Collisions(laser))
                    {
                        laser.Alive = false;
                        explosionManager.Add(enemy.X, enemy.Y);
                        enemy.Alive = false;
                        enemy.OnDeath();
                        shipManager.Scored();
                        roundData.EnemiesShot++;
                    }
                }
            }
        }

        /// <summary>
        /// Handles collisions between the ship and weapon and health pickups.
        /// </summary>
        public void HandleShipPickupCollisions()
        {
            foreach(var pickup in pickupManager.Collisions(gameData.Ship)) {
                if (pickup.PickedUp(gameData.Ship))
                {
                    pickup.Alive = false;
                }
            }
        }

        /// <summary>
        /// Handles collisions between the enemy and space blocks.
        /// </summary>
        public void HandleEnemyRockCollisions()
        {
            var resolution = gameData.Resolution;
            
            var cameraRect = new Rectangle(
                (int) gameData.Camera.X, 
                (int) gameData.Camera.Y, 
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

                            explosionManager.Add(block.X, block.Y);
                            explosionManager.Add(enemy.X, enemy.Y);

                            block.Destroy();
                        }
                    }
                }
            }
        }

        /// <summary>
        /// Handles collisions between projectiles and space blocks.
        /// </summary>
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
                        explosionManager.Add(block.X, block.Y);

                        // Update the score
                        shipManager.Scored();
                        roundData.BlocksShot = roundData.BlocksShot++;

                        // Kill the block
                        block.Destroy();
                    }
                }
            }
        }

        /// <summary>
        /// Handles collisions between the ship and space blocks.
        /// </summary>
        public void HandleShipRockCollisions()
        {
            //  Update blocks
            foreach (var block in blockManager.Collisions(gameData.Ship))
            {
                // Ignore the collision if the ship is not alive
                if (shipManager.Alive)
                {
                    roundData.BlocksBumped++;
                    var ship = gameData.Ship;

                    // Create an explosion at the coordinates of the block
                    explosionManager.Add(block.X, block.Y);

                    // Notify the ship manager that the ship has been hit
                    shipManager.ShipHit();

                    // If the ship died, add an explosion where the ship was
                    if (!shipManager.Alive)
                    {
                        explosionManager.Add(ship.X, ship.Y);
                    }

                    // Notify the block that it has been hit
                    block.Thump();
                }
            }
        }
    }
}
