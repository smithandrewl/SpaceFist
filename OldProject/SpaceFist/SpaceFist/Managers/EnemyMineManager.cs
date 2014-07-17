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

        public EnemyMineManager(GameData gameData): base(gameData)
        {
            this.gameData = gameData;
        }

        public void SpawnEnemyMine(int x, int y)
        {
            Add(new EnemyMine(gameData, new Vector2(x, y)));
        }
    }
}
