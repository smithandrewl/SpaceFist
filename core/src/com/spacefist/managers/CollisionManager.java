package com.spacefist.managers;

import com.badlogic.gdx.math.Rectangle;
import com.spacefist.GameData;
import com.spacefist.RoundData;
import com.spacefist.entities.*;
import com.spacefist.entities.enemies.Enemy;
import org.jetbrains.annotations.NotNull;

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
    private GameData gameData;

    public CollisionManager(
        @NotNull GameData          gameData)
    {
        this.gameData = gameData;

        blockManager     = gameData.getBlockManager();
        shipManager      = gameData.getPlayerManager();
        laserManager     = gameData.getProjectileManager();
        explosionManager = gameData.getExplosionManager();
        pickupManager    = gameData.getPickUpManager();
        enemyManager     = gameData.getEnemyManager();
        enemyMineManager = gameData.getEnemyMineManager();
        roundData        = gameData.getRoundData();
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
        assert gameData.getShip() != null;

        for (EnemyMine mine : enemyMineManager.Collisions(gameData.getShip())) {
            mine.setAlive(false);
            mine.hit();
            explosionManager.Add(mine.getX(), mine.getY());
            shipManager.ShipHit();
        }
    }

    /// <summary>
    /// Handles collisions between the ship and projectiles.
    /// </summary>
    public void HandleProjectileShipCollisions()
    {
        for (Projectile projectile : laserManager.EnemyProjectiles())
        {
            if(projectile.getRectangle().overlaps(gameData.getShip().getRectangle())) {
                projectile.setAlive(false);
                // FIXME: Bug: Explosions draw off-center
                explosionManager.Add(gameData.getShip().getX(), gameData.getShip().getY());
                shipManager.ShipHit();
            }
        }
    }

    /// <summary>
    /// Handles collisions between the ship and enemies.
    /// </summary>
    public void HandleEnemyShipCollisions()
    {
        for (Enemy enemy : enemyManager.Collisions(gameData.getShip()))
        {
            explosionManager.Add(enemy.getX(), enemy.getY());
            enemy.setAlive(false);
            enemy.onDeath();
            shipManager.ShipHit();
        }
    }

    /// <summary>
    /// Handles collisions between the enemy and projectiles.
    /// </summary>
    public void HandleEnemyLaserCollisions()
    {
        for (Projectile laser : laserManager.PlayerProjectiles())
        {
            if (laser.isAlive())
            {
                for (Enemy enemy : enemyManager.Collisions(laser))
                {
                    laser.setAlive(false);
                    explosionManager.Add(enemy.getX(), enemy.getY());
                    enemy.setAlive(false);
                    enemy.onDeath();
                    shipManager.Scored();
                    roundData.getEnemiesShot();
                }
            }
        }
    }

    /// <summary>
    /// Handles collisions between the ship and weapon and health pickups.
    /// </summary>
    public void HandleShipPickupCollisions()
    {
        for (Pickup pickup : pickupManager.Collisions(gameData.getShip())) {
            if (pickup.pickedUp(gameData.getShip())) {
                pickup.setAlive(false);
            }
        }
    }

    /// <summary>
    /// Handles collisions between the enemy and space blocks.
    /// </summary>
    public void HandleEnemyRockCollisions()
    {
        Rectangle resolution = gameData.getResolution();

        Rectangle cameraRect = new Rectangle(
            (int) gameData.getCamera().x,
            (int) gameData.getCamera().y,
            resolution.getWidth(),
            resolution.getHeight()
        );

        // Only process onscreen collisions
        for (Enemy enemy : enemyManager) {
            if (enemy.isAlive() && cameraRect.contains(enemy.getRectangle()))
            {
                for (SpaceBlock block : blockManager.Collisions(enemy))
                {

                    if (cameraRect.contains(block.getRectangle()))
                    {
                        enemy.setAlive(false);
                        enemy.onDeath();

                        explosionManager.Add(block.getX(), block.getY());
                        explosionManager.Add(enemy.getX(), enemy.getY());

                        block.destroy();
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
        for (Projectile laser : laserManager)
        {
            // Only process lasers that are still in play
            if (laser.isAlive())
            {
                // If an alive laser hits a block
                for (SpaceBlock block : blockManager.Collisions(laser))
                {
                    laser.setAlive(false);
                    // Create and add a new explosion
                    explosionManager.Add(block.getX(), block.getY());

                    // Update the score
                    shipManager.Scored();
                    roundData.setBlocksShot(roundData.getBlocksShot() + 1);

                    // Kill the block
                    block.destroy();
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
        for (SpaceBlock block : blockManager.Collisions(gameData.getShip()))
        {
            // Ignore the collision if the ship is not alive
            if (shipManager.isAlive())
            {
                roundData.setBlocksBumped(roundData.getBlocksBumped() + 1);
                Ship ship = gameData.getShip();

                // Create an explosion at the coordinates of the block
                explosionManager.Add(block.getX(), block.getY());

                // Notify the ship manager that the ship has been hit
                shipManager.ShipHit();

                // If the ship died, add an explosion where the ship was
                if (!shipManager.isAlive())
                {
                    explosionManager.Add(ship.getX(), ship.getY());
                }

                // Notify the block that it has been hit
                block.thump();
            }
        }
    }
}
