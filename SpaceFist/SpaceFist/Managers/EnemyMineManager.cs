using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    public class EnemyMineManager
    {
        private List<EnemyMine> mines;
        private Game game;

        public EnemyMineManager(Game game)
        {
            this.game = game;
            mines = new List<EnemyMine>();
        }

        public void Update()
        {
            mines.ForEach(mine => mine.Update());
        }

        public void Draw()
        {
            mines.ForEach(mine => mine.Draw());
        }

        public void SpawnEnemyMine(int x, int y)
        {
            mines.Add(new EnemyMine(game, new Vector2(x, y)));
        }

        public IEnumerable<EnemyMine> Collisions(Entity entity)
        {
            var collisions = 
                from mine in mines
                where mine.Alive && mine.Rectangle.Intersects(entity.Rectangle)
                select mine;

            return collisions;
        }

        public void Clear()
        {
            mines.Clear();
        }
    }
}
