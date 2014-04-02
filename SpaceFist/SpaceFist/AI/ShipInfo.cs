using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.AI
{
    public class ShipInfo : FuzzyLogicEnabled
    {
        private Game game;
        private EnemyManager enemyManager;
        private BlockManager blockManager;
        private ProjectileManager projectileManager;

        public ShipInfo(Game game, Ship ship, EnemyManager enemyManager, BlockManager blockManager, ProjectileManager projectileManager)
        {
            this.game = game;
            this.enemyManager = enemyManager;
            this.blockManager = blockManager;
            this.projectileManager = projectileManager;
        }

        public override void Update()
        {
            throw new NotImplementedException();
        }
    }
}
