using SpaceFist.Managers;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;

namespace SpaceFist.AI.AggressiveAI
{
    class FireState : FuzzyLogicEnabled, EnemyAIState
    {
        private ProjectileManager projectileManager;
        private float membership;
        private DateTime lastFire;

        public FireState(EnemyAI ai, Game game)
        {
            this.AI            = ai;
            this.ShipInfo      = AI.ShipInfo;
            this.ShipEnemyInfo = AI.ShipEnemyInfo;
            membership = 0;
            lastFire = DateTime.Now;

            this.projectileManager = game.InPlayState.ProjectileManager;
        }

        public override void Update()
        {
            membership = ShipEnemyInfo.Distance.High;

            // if the ship is in the line of sight, fire every time period
            if (AI.ShipEnemyInfo.EnemyVisible)
            {
                var now = DateTime.Now;
                if (now.Subtract(lastFire).Milliseconds > Math.Max(200, (600 * Not(membership))))
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
