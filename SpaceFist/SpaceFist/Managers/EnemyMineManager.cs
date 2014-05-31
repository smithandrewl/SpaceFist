using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    public class EnemyMineManager: Manager<EnemyMine>
    {

        public EnemyMineManager(Game game): base(game)
        {
            this.game = game;
        }

        public void SpawnEnemyMine(int x, int y)
        {
            Add(new EnemyMine(game, new Vector2(x, y)));
        }
    }
}
