using Microsoft.Xna.Framework;
using SpaceFist.AI.DefensiveAI;
using SpaceFist.AI.DummyAI;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    /// <summary>
    ///  Represents a bulky enemy freighter that does not follow the player, but fires in its 
    ///  direction.
    /// </summary>
    public class EnemyFreighter : Enemy
    {
        /// <summary>
        /// Creates a new EnemyFreighter instance at a specified location.
        /// </summary>
        /// <param name="game">The game</param>
        /// <param name="position">The location to place the freighter in the world.</param>
        public EnemyFreighter(Game game, Vector2 position):
            base(game, game.Textures["EnemyFreighter"], game.SoundEffects["Explosion"], position)
        {
            AI = new DefensiveAI(game, this);
        }
    }
}
