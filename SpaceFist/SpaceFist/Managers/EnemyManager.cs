using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.AI.DummyAI;
using SpaceFist.Entities.Enemies;

namespace SpaceFist.Managers
{
    /// <summary>
    /// Keeps track of all of the enemies in the world.
    /// </summary>
    public class EnemyManager : Manager<Enemy>
    {
        private Random      rand;

        /// <summary>
        /// Creates a new EnemyManager instance.
        /// </summary>
        /// <param name="game">The game</param>
        public EnemyManager(Game game): base(game)
        {
            rand        = new Random();
        }

        /// <summary>
        /// Spawns a number of enemy fighters to the world.
        /// </summary>
        /// <param name="count">The number of fighters to spawn</param>
        public void SpawnEnemyFighters(int count)
        {
            SpawnEnemies(count, position => new EnemyFighter(game, position));
        }

        /// <summary>
        /// Spawns the specified number of enemyFreighters to the world.
        /// </summary>
        /// <param name="count">The number of freighters to spawn</param>
        public void SpawnEnemyFreighters(int count)
        {
            SpawnEnemies(count, position => new EnemyFreighter(game, position));
        }

       
        public void SpawnEnemy(int x, int y, Func<Vector2, Enemy> func)
        {
            float rotation = MathHelper.ToRadians(180);
            Enemy enemy = func(new Vector2(x, y));
            enemy.Rotation = rotation;
            Add(enemy);
        }

        public void SpawnEnemy(int lowX, int highX, int lowY, int highY, Func<Vector2,Enemy> func)
        {
            int randX = rand.Next(lowX, highX);
            int randY = rand.Next(lowY, highY);

            SpawnEnemy(randX, randY, func);
        }
        /// <summary>
        /// Spawns a number of enemies to random locations on the screen
        /// given an enemy placement method.
        /// </summary>
        /// <param name="count">The number of enemies to spawn</param>
        /// <param name="func">A function to spawn a particular type of enemy</param>
        private void SpawnEnemies(int count, Func<Vector2, Enemy> func){
            SpawnEnemies(
                count,
                0, 
                game.InPlayState.World.Width, 
                0,
                (int)MathHelper.Max(
                    game.InPlayState.World.Height *.9f,
                    game.Resolution.Height / 2
                ),
                func
            );
        }

        public void SpawnEnemies(int count, int lowX, int highX, int lowY, int highY, Func<Vector2, Enemy> func)
        {
            for (int i = 0; i < count; i++)
            {
                SpawnEnemy(lowX, highX, lowY, highY, func);
            }
        }

        /// <summary>
        /// Returns a collection of all of the live enemies
        /// which are visible to the player.
        /// </summary>
        /// <returns></returns>
        public IEnumerable<Enemy> VisibleEnemies()
        {
            var camera         = game.InPlayState.Camera;
            var backgroundRect = game.BackgroundRect;

            var screenRect = new Rectangle(
                (int)camera.X, 
                (int)camera.Y, 
                backgroundRect.Width, 
                backgroundRect.Height
            );

            var res =
                from   enemy in this
                where  enemy.Alive && screenRect.Intersects(enemy.Rectangle)
                select enemy;

            return res;
        }
    }
}
