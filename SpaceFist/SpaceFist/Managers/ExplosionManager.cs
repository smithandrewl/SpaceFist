using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    class ExplosionManager : IEnumerable<Explosion>
    {
        List<Explosion> explosions;
        Game game;
        
        public ExplosionManager(Game game)
        {
            explosions = new List<Explosion>();
            this.game  = game;
        }

        public IEnumerator<Explosion> GetEnumerator()
        {
            return explosions.GetEnumerator();
        }

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        public void Update()
        {
            explosions.ForEach(explosion => explosion.Update());
        }

        public void Draw()
        {
            explosions.ForEach(explosion => explosion.Draw());
        }

        public void add(int x, int y)
        {
            var explosion = new Explosion(game, new Vector2(x, y));
            explosions.Add(explosion);
        }
    }
}
