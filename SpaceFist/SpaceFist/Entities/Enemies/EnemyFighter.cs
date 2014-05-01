using Microsoft.Xna.Framework;
using SpaceFist.AI.DummyAI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Entities.Enemies
{
    public class EnemyFighter : Enemy
    {
        public EnemyFighter(Game game, Vector2 position)
            : base(game, game.EnemyFighterTexture, game.ExplosionSound, position)
        {
            this.AI = new AggressiveAI(game, this, game.InPlayState.ship);
        }
    }
}
