package com.spacefist.managers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.spacefist.GameData;
import com.spacefist.entities.Entity;
import com.spacefist.entities.SpaceBlock;

/// <summary>
/// Keeps track of the space blocks in the world and
/// provides methods to operate on them.
///
/// This class is enumerable and instances can be used
/// in foreach loops to iterate the live blocks in the world.
/// </summary>
public class BlockManager extends Manager<SpaceBlock>
{

    /// <summary>
    /// Creates a new BlockManager instance.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    public BlockManager(GameData gameData) {
        super(gameData);
    }

    /// <summary>
    /// Repositions the existing blocks around the world with random
    /// velocities marking them as alive.
    /// </summary>
    public void RespawnBlocks()
    {
        for (SpaceBlock block : this)
        {
            Vector2 position = randomPos();

            block.setX((int)position.x);
            block.setY((int) position.y);
            block.setVelocity(randomVel());
            block.setAlive(true);
        }
    }

    /// <returns>A random point in the world</returns>
    private Vector2 randomPos()
    {
        int randX = (int) MathUtils.random(0, gameData.getWorld().getWidth());
        int randY = MathUtils.random(0, (int) gameData.getWorld().getHeight());

        return new Vector2(randX, randY);
    }

    /// <returns>A velocity with a random x between -2 and 2</returns>
    private Vector2 randomVel()
    {
        return new Vector2(MathUtils.random(4) - 2, MathUtils.random(4));
    }

    /// <summary>
    /// Spawns a specified number of blocks to random
    /// locations in the game world.
    /// </summary>
    /// <param name="count">The number of blocks to spawn</param>
    public void SpawnBlocks(int count)
    {
        Clear();

        // Spawn space blocks
        for (int i = 0; i < count; i++)
        {
            // Construct the block
            SpaceBlock block = new SpaceBlock(
                gameData,
                gameData.getTextures().get("Block"),
                randomPos(),
                randomVel()
            );

            // Initialize and the block to the list
            block.initialize();
            Add(block);
        }
    }

    // Keep the specified entity on world causing it to "bounce"
    // off of the edges of the world.
    private void KeepOnWorld(Entity obj)
    {
        Rectangle world = gameData.getWorld();
        if ((obj.getX() > world.getWidth()) ||
            (obj.getX() < 0) ||
            (obj.getY() > world.getHeight()) ||
            (obj.getY() < 0))
        {
            obj.setVelocity(obj.getVelocity().scl(-1));
        }
    }

    /// <summary>
    /// Returns the live blocks in the world
    /// which are currently visible on the screen.
    /// </summary>
    /// <returns>Blocks which are visible to the player</returns>
    public Iterable<SpaceBlock> getVisibleBlocks()
    {
        Vector2 camera   = gameData.getCamera();
        Rectangle bounds = gameData.getResolution();

        // The portion of the game world being shown on
        // the screen.
        Rectangle VisibleWorldRect = new Rectangle(
            (int)camera.x,
            (int)camera.y,
            bounds.getWidth(),
            bounds.getHeight()
        );

        Array<SpaceBlock> res = new Array<SpaceBlock>(false,16);

        for(SpaceBlock spaceBlock : entities) {
            if(spaceBlock.isAlive() && VisibleWorldRect.overlaps(spaceBlock.getRectangle())) {
                res.add(spaceBlock);
            }
        }

        return res;
    }

    @Override
    public void Update()
    {
        for (SpaceBlock block : this) {
            KeepOnWorld(block);
        }

        super.Update();
    }
}
