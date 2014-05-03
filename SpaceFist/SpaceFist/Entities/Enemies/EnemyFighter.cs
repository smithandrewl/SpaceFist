using Microsoft.Xna.Framework;
using SpaceFist.AI.DummyAI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Entities.Enemies
{
    /// <summary>
    /// Represents an enemy fighter with aggressive ramming behavior.
    /// </summary>
    public class EnemyFighter : Enemy
    {
        /// <summary>
        /// Creates a new EnemyFighter instance at a specified location.
        /// </summary>
        /// <param name="game">The game</param>
        /// <param name="position">The place in the world to put the fighter</param>
        public EnemyFighter(Game game, Vector2 position)
            : base(game, game.EnemyFighterTexture, game.ExplosionSound, position)
        {
            this.AI = new AggressiveAI(game, this, game.InPlayState.ship);
        }
    }
}
