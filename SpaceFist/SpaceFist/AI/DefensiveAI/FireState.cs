using SpaceFist.Managers;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;

namespace SpaceFist.AI.AggressiveAI
{
    /// <summary>
    /// This fuzzy state fires at the ship when the enemy is on the screen.
    /// </summary>
    class FireState : FuzzyLogicEnabled, EnemyAIState
    {
<<<<<<< HEAD
        // The degree to which this state is active
        private float membership;
=======
        private float membership;

        private ProjectileManager projectileManager;
        private DateTime          lastFire;
>>>>>>> 1627f196e7707136c4f509c8182908f40de0fc5b

        private ProjectileManager projectileManager;
        private DateTime          lastFire; // The last time this enemy fired at the player

        /// <summary>
        /// Creates a new FireState instance
        /// </summary>
        /// <param name="ai">The AI this state belongs to.</param>
        /// <param name="game">The game</param>
        public FireState(EnemyAI ai, Game game)
        {
            this.AI            = ai;
            this.ShipInfo      = AI.ShipInfo;
            this.ShipEnemyInfo = AI.ShipEnemyInfo;
            membership         = 0;
            lastFire           = DateTime.Now;

            this.projectileManager = game.InPlayState.ProjectileManager;
        }

        public override void Update()
        {
            membership = ShipEnemyInfo.Distance.High;

            // if this enemy is on screen, fire and wait a fuzzy amount of time before
            // firing again.
            if (AI.ShipEnemyInfo.EnemyVisible)
            {
                var now = DateTime.Now;

<<<<<<< HEAD
                // Fire at the ship every 200 to 600 milliseconds depending on how far away
                // the ship is.  The further the ship is, the faster the enemy will fire.
=======
>>>>>>> 1627f196e7707136c4f509c8182908f40de0fc5b
                if (now.Subtract(lastFire).Milliseconds > Math.Max(200, (600 * Not(membership))))
                {
                    int halfWidth  = ShipEnemyInfo.Enemy.Rectangle.Width  / 2;
                    int halfHeight = ShipEnemyInfo.Enemy.Rectangle.Height / 2;

                    projectileManager.fireLaser(
                        ShipEnemyInfo.Enemy.X +halfWidth, 
                        ShipEnemyInfo.Enemy.Y + halfHeight, 
                        ShipEnemyInfo.LineOfSight, 
                        true
                    );

                    lastFire = now;
                }
            }
        }

<<<<<<< HEAD
        // The AI that this state belongs to
        public EnemyAI       AI            { get; set; }

        // Fuzzy information about the player
        public ShipInfo      ShipInfo      { get; set; }

        // Fuzzy information about the player specific to this enemy
=======
        public EnemyAI       AI            { get; set; }
        public ShipInfo      ShipInfo      { get; set; }
>>>>>>> 1627f196e7707136c4f509c8182908f40de0fc5b
        public ShipEnemyInfo ShipEnemyInfo { get; set; }
    }
}
