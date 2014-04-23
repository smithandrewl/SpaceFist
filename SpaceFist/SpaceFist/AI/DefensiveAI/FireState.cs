using SpaceFist.Managers;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.AI.AggressiveAI
{
    class FireState : EnemyAIState
    {
        private ProjectileManager projectileManager;
        private float membership;
        private DateTime lastFire;
        private Game game;

        public FireState(EnemyAI ai, Game game)
        {
            this.AI            = ai;
            this.ShipInfo      = AI.ShipInfo;
            this.ShipEnemyInfo = AI.ShipEnemyInfo;
            membership = 0;
            lastFire = DateTime.Now;

            this.projectileManager = game.InPlayState.ProjectileManager;
            this.game = game;
        }

        public void Update()
        {

            // if the ship is in the line of sight, fire every time period
            if (AI.ShipEnemyInfo.EnemyVisible)
            {
                var now = DateTime.Now;
                if (now.Subtract(lastFire).Milliseconds > 500)
                {
                    int halfWidth = ShipEnemyInfo.Enemy.Rectangle.Width / 2;
                    int halfHeight = ShipEnemyInfo.Enemy.Rectangle.Height / 2;

                    projectileManager.fireLaser(ShipEnemyInfo.Enemy.X +halfWidth, ShipEnemyInfo.Enemy.Y + halfHeight, ShipEnemyInfo.LineOfSight, true);
                    lastFire = now;
                }
            }
        }

        public EnemyAI AI { get; set; }

        public ShipInfo ShipInfo { get; set; }

        public ShipEnemyInfo ShipEnemyInfo { get; set; }
    }
}
