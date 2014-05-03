using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    /// <summary>
    /// Keeps track of all of the explosions in the world.
    /// </summary>
    class ExplosionManager : IEnumerable<Explosion>
    {
        List<Explosion> explosions;
        Game game;
        
        /// <summary>
        /// Creates a new ExplosionManager instance.
        /// </summary>
        /// <param name="game">The game</param>
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

        /// <summary>
        /// Adds a new explosion at the specified location.
        /// </summary>
        /// <param name="x">The X component of the location</param>
        /// <param name="y">The Y component of the location</param>
        public void add(int x, int y)
        {
            var explosion = new Explosion(game, new Vector2(x, y));
            explosions.Add(explosion);
        }
    }
}
