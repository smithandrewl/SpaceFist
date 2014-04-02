using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    class EnemyManager : IEnumerable<Enemy>
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
        }

        public void Spawn(int count){
            for (int i = 0; i < count; i++)
            {
                int   randX    = rand.Next(0, screen.Width);
                int   randY    = rand.Next(0, 15);
                float rotation = MathHelper.ToRadians(180);
                Enemy enemy    = new Enemy(game, new Vector2(randX, randY));
                
                enemy.Rotation = rotation;
                enemies.Add(enemy);
            }
        }

        public void Update(){
            enemies.ForEach(enemy => enemy.Update());
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
    }
}
