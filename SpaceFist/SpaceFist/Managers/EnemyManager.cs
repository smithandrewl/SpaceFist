using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.AI.DummyAI;

namespace SpaceFist.Managers
{
    public class EnemyManager : IEnumerable<Enemy>
    {
        private Random      rand;
        private List<Enemy> enemies;
        private Game        game;
        private Rectangle   screen;

        public EnemyManager(Game game, Rectangle screen)
        {
            rand        = new Random();
            this.game   = game;
            this.screen = screen;
            enemies     = new List<Enemy>();
        }

        public void Spawn(int x, int y)
        {
            Enemy enemy = new Enemy(game, new Vector2(x, y));
            enemy.AI = new DummyAI(game, enemy, game.InPlayState.ship, this);
        }

        public void Spawn(int count){

            enemies.Clear();

            for (int i = 0; i < count; i++)
            {
                int   randX    = rand.Next(0, game.InPlayState.World.Width);
                int   randY    = rand.Next(0, (int) game.InPlayState.World.Height);
                float rotation = MathHelper.ToRadians(180);
                Enemy enemy    = new Enemy(game, new Vector2(randX, randY));

                enemy.AI = new DummyAI(game, enemy, game.InPlayState.ship, this);
                enemy.Rotation = rotation;
                enemies.Add(enemy);
            }
        }

        public void Update(){
            enemies.ForEach(enemy => enemy.Update());

            var alive = enemies.Count(enemy => enemy.Alive);
        }
        
        public void Draw() {
            enemies.ForEach(enemy => enemy.Draw());
        }

        public IEnumerator<Enemy> GetEnumerator()
        {
            return enemies.GetEnumerator();
        }

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        public IEnumerable<Entity> Collisions(Entity entity)
        {
            var res =
                from enemy in enemies
                where enemy.Alive && (enemy != entity) && enemy.Rectangle.Intersects(entity.Rectangle)
                select enemy;


            return res;
        }
    }
}
