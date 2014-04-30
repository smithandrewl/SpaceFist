using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    public class BlockManager : IEnumerable<SpaceBlock>
    {
        List<SpaceBlock> blocks = new List<SpaceBlock>();
        
        Random    rand;
        Game      game;

        public BlockManager(Game game)
        {
            this.game   = game;
            this.blocks = new List<SpaceBlock>();
            rand        = new Random();
        }

        public void RespawnBlocks()
        {
            foreach(var block in blocks) {
                var position = randomPos();

                block.X        = (int)position.X;
                block.Y        = (int)position.Y;
                block.Velocity = randomVel();
                block.Alive    = true;
            }
        }

        private Vector2 randomPos()
        {

            int randX = rand.Next(0, game.InPlayState.World.Width);
            int randY = rand.Next(0, (int)game.InPlayState.World.Height);

            return new Vector2(randX, randY);
        }

        private Vector2 randomVel()
        {
            return new Vector2(rand.Next(4) - 2, rand.Next(4));
        }

        public void SpawnBlocks(int count)
        {
            blocks.Clear();

            // Spawn space blocks
            for (int i = 0; i < count; i++)
            {
                // Construct the block
                var block = new SpaceBlock(
                    game,
                    game.BlockTexture,
                    randomPos(),
                    randomVel()
                );

                // Initialize and the block to the list
                block.Initialize();
                blocks.Add(block);
            }
        }

        public void Update()
        {
            // If there are no blocks in the world,
            // respawn more
            if (blocks.All(block => block.Alive == false))
            {
                RespawnBlocks();
            }

             //  Update blocks
            foreach (var block in blocks.Where(block => block.Alive))
            {
                block.Update();
                KeepOnWorld(block);
            }
        }

        public void Draw()
        {
            blocks.ForEach(block => block.Draw());
        }

        // Keep the specified entity on world
        private void KeepOnWorld(Entity obj)
        {
            var world = game.InPlayState.World;
            if ((obj.X > world.Width)  || (obj.X < 0) ||
                (obj.Y > world.Height) || (obj.Y < 0))
            {
                obj.Velocity *= -1;
            }
        }

        public IEnumerator<SpaceBlock> GetEnumerator()
        {
            return blocks.GetEnumerator();
        }

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        public IEnumerable<SpaceBlock> VisibleBlocks()
        {
            var camera = game.InPlayState.Camera;
            var bounds = game.BackgroundRect;

            var VisibleWorldRect = new Rectangle(
                (int) camera.X, 
                (int) camera.Y, 
                bounds.Width, 
                bounds.Height
            );
            
            var res = 
                from block in blocks
                where block.Alive && VisibleWorldRect.Intersects(block.Rectangle)
                select block;
       
            return res;
        }

        public void Remove(SpaceBlock block)
        {
            blocks.Remove(block);
        }

        public IEnumerable<SpaceBlock> Collisions(Entity obj)
        {
            var collisions = 
                from   block in blocks 
                where  block.Alive && block.Rectangle.Intersects(obj.Rectangle)
                select block;

            return collisions;
        }
    }
}
