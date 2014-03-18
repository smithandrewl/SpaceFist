using Microsoft.Xna.Framework;
using SpaceshipShooter.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceshipShooter.Managers
{
    class BlockManager : IEnumerable<SpaceBlock>
    {
        List<SpaceBlock> blocks = new List<SpaceBlock>();
        Random rand;
        Game game;

        Rectangle screen;

        public BlockManager(Game game, Rectangle screen)
        {
            this.game = game;
            this.screen = screen;
            this.blocks = new List<SpaceBlock>();
            rand = new Random();
        }

        public void SpawnBlocks(int count)
        {
            blocks.Clear();
            // Spawn space blocks
            for (int i = 0; i < count; i++)
            {
                // Generate a random position on-screen
                var randX = rand.Next(screen.Width);
                var randY = rand.Next(screen.Height);

                // Generate a random velocity of between -5 and 5d 
                var randXVel = rand.Next(10) - 5;
                var randYVel = rand.Next(10) - 5;

                // Construct the block
                var block =
                    new SpaceBlock(game,
                        game.BlockTexture,
                        new Vector2(randX, randY),
                        new Vector2(randXVel, randYVel));

                // Initialize and the block to the list

                block.Initialize();
                blocks.Add(block);
            }
        }

        public void Update(GameTime gameTime)
        {
             //  Update blocks
            foreach (var block in blocks.Where(block => block.Alive))
            {
                block.Update(gameTime);
                WrapOffScreen(block);
            }
        }

        public void Draw(GameTime gameTime)
        {
            blocks.ForEach(block => block.Draw(gameTime));
        }

        // If the specified game object has left the screen,
        // wrap it around
        private void WrapOffScreen(GameObject obj)
        {

            if (obj.X > screen.Width) obj.X = 0;
            if (obj.X < 0) obj.X = screen.Width;

            if (obj.Y > screen.Height) obj.Y = 0;
            if (obj.Y < 0) obj.Y = screen.Height;
        }

        public IEnumerator<SpaceBlock> GetEnumerator()
        {
            return blocks.GetEnumerator();
        }

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        public void Add(SpaceBlock block)
        {

        }

        public void Remove(SpaceBlock block)
        {
            blocks.Remove(block);

        }

        public IEnumerable<SpaceBlock> collisions(GameObject obj)
        {
            var collisions = 
                from   block in blocks 
                where  block.Rectangle.Intersects(obj.Rectangle) 
                select block;

            return collisions;
        }
    }
}
