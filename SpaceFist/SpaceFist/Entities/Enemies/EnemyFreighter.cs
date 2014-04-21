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
    public class EnemyFreighter : Enemy
    {
        public EnemyFreighter(Game game, Vector2 position):
            base(game, game.EnemyFreighterTexture, game.ExplosionSound, position)
        {
            AI = new DefensiveAI(game, this);
        }
    }
}
