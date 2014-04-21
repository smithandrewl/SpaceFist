using Microsoft.Xna.Framework;
using SpaceFist.AI.DummyAI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Entities.Enemies
{
    public class DummyEnemy : Enemy
    {
        public DummyEnemy(Game game, Vector2 position) 
            : base(game, game.EnemyTexture, game.ExplosionSound, position) {
        
            this.AI = new DummyAI(game, this, game.InPlayState.ship, game.InPlayState.EnemyManager);
        }
    }
}
