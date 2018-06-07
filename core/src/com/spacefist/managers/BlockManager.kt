package com.spacefist.managers

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.spacefist.GameData
import com.spacefist.entities.Entity
import com.spacefist.entities.SpaceBlock

/// <summary>
/// Keeps track of the space blocks in the world and
/// provides methods to operate on them.
///
/// This class is enumerable and instances can be used
/// in foreach loops to iterate the live blocks in the world.
/// </summary>
class BlockManager/// <summary>
/// Creates a new BlockManager instance.
/// </summary>
/// <param name="gameData">Common game data</param>
(gameData: GameData) : Manager<SpaceBlock>(gameData) {

    /// <summary>
    /// Returns the live blocks in the world
    /// which are currently visible on the screen.
    /// </summary>
    /// <returns>Blocks which are visible to the player</returns>
    // The portion of the game world being shown on
    // the screen.
    val visibleBlocks: Iterable<SpaceBlock>
        get() {
            val camera = gameData.camera
            val bounds = gameData.resolution
            val visibleWorldRect = Rectangle(
                    camera.x.toInt().toFloat(),
                    camera.y.toInt().toFloat(),
                    bounds.getWidth(),
                    bounds.getHeight()
            )

            val res = Array<SpaceBlock>(false, 16)

            for (spaceBlock in entities) {
                if (spaceBlock.isAlive && visibleWorldRect.overlaps(spaceBlock.rectangle)) {
                    res.add(spaceBlock)
                }
            }

            return res
        }

    /// <summary>
    /// Repositions the existing blocks around the world with random
    /// velocities marking them as alive.
    /// </summary>
    fun respawnBlocks() {
        for (block in this) {
            val position = randomPos()

            block.x = position.x.toInt()
            block.y = position.y.toInt()
            block.velocity = randomVel()
            block.isAlive = true
        }
    }

    /// <returns>A random point in the world</returns>
    private fun randomPos(): Vector2 {
        assert(gameData.world != null)

        val randX = MathUtils.random(0f, gameData.world.getWidth()).toInt()
        val randY = MathUtils.random(0, gameData.world.getHeight().toInt())

        return Vector2(randX.toFloat(), randY.toFloat())
    }

    /// <returns>A velocity with a random x between -2 and 2</returns>
    private fun randomVel(): Vector2 {
        return Vector2((MathUtils.random(4) - 2).toFloat(), MathUtils.random(4).toFloat())
    }

    /// <summary>
    /// Spawns a specified number of blocks to random
    /// locations in the game world.
    /// </summary>
    /// <param name="count">The number of blocks to spawn</param>
    fun spawnBlocks(count: Int) {
        clear()

        // spawn space blocks
        for (i in 0 until count) {
            // Construct the block
            val block = SpaceBlock(
                    gameData,
                    gameData.textures["Block"],
                    randomPos(),
                    randomVel()
            )

            // initialize and the block to the list
            block.initialize()
            add(block)
        }
    }

    // Keep the specified entity on world causing it to "bounce"
    // off of the edges of the world.
    private fun keepOnWorld(obj: Entity) {
        assert(obj != null)
        assert(gameData.world != null)

        val world = gameData.world

        if (obj.x > world.getWidth() ||
                obj.x < 0 ||
                obj.y > world.getHeight() ||
                obj.y < 0) {
            obj.velocity = obj.velocity!!.scl(-1f)
        }
    }

    override fun update() {
        for (block in this) {
            keepOnWorld(block)
        }

        super.update()
    }
}
