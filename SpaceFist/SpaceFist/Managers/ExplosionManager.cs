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
    class ExplosionManager : Manager<Explosion>
    {
        /// <summary>
        /// Creates a new ExplosionManager instance.
        /// </summary>
        /// <param name="game">The game</param>
        public ExplosionManager(Game game) : base(game)
        {
            this.game  = game;
        }

        /// <summary>
        /// Adds a new explosion at the specified location.
        /// </summary>
        /// <param name="x">The X component of the location</param>
        /// <param name="y">The Y component of the location</param>
        public void Add(int x, int y)
        {
            var explosion = new Explosion(game, new Vector2(x, y));
            Add(explosion);
        }
    }
}
