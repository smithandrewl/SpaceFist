using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    /// <summary>
    /// Keeps track of the space blocks in the world and
    /// provides methods to operate on them.
    /// 
    /// This class is enumerable and instances can be used
    /// in foreach loops to iterate the live blocks in the world.
    /// </summary>
    public class BlockManager : Manager<SpaceBlock>
    {
        Random rand;

        /// <summary>
        /// Creates a new BlockManager instance.
        /// </summary>
        /// <param name="game">The game</param>
        public BlockManager(Game game)
            : base(game)
        {
            rand = new Random();
        }

        /// <summary>
        /// Repositions the existing blocks around the world with random
        /// velocities marking them as alive.
        /// </summary>
        public void RespawnBlocks()
        {
            foreach (var block in this)
            {
                var position = randomPos();

                block.X = (int)position.X;
                block.Y = (int)position.Y;
                block.Velocity = randomVel();
                block.Alive = true;
            }
        }

        /// <returns>A random point in the world</returns>
        private Vector2 randomPos()
        {
            int randX = rand.Next(0, game.InPlayState.World.Width);
            int randY = rand.Next(0, (int)game.InPlayState.World.Height);

            return new Vector2(randX, randY);
        }

        /// <returns>A velocity with a random x between -2 and 2</returns>
        private Vector2 randomVel()
        {
            return new Vector2(rand.Next(4) - 2, rand.Next(4));
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
                var block = new SpaceBlock(
                    game,
                    game.Textures["BlockTexture"],
                    randomPos(),
                    randomVel()
                );

                // Initialize and the block to the list
                block.Initialize();
                Add(block);
            }
        }

        // Keep the specified entity on world causing it to "bounce"
        // off of the edges of the world.
        private void KeepOnWorld(Entity obj)
        {
            var world = game.InPlayState.World;
            if ((obj.X > world.Width) ||
                (obj.X < 0) ||
                (obj.Y > world.Height) ||
                (obj.Y < 0))
            {
                obj.Velocity *= -1;
            }
        }

        /// <summary>
        /// Returns the live blocks in the world
        /// which are currently visible on the screen.
        /// </summary>
        /// <returns>Blocks which are visible to the player</returns>
        public IEnumerable<SpaceBlock> VisibleBlocks()
        {
            var camera = game.InPlayState.Camera;
            var bounds = game.BackgroundRect;

            // The portion of the game world being shown on
            // the screen.
            var VisibleWorldRect = new Rectangle(
                (int)camera.X,
                (int)camera.Y,
                bounds.Width,
                bounds.Height
            );

            var res =
                from block in entities
                where block.Alive && VisibleWorldRect.Intersects(block.Rectangle)
                select block;

            return res;
        }
    }
}
