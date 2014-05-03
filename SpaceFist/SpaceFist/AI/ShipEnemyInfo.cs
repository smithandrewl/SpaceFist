using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using SpaceFist.Entities;

namespace SpaceFist.AI
{
    /// <summary>
    /// Provides information about the players ship that is specific to a particular enemy.
    /// </summary>
    public class ShipEnemyInfo : FuzzyLogicEnabled
    {
        private const bool DisplayDebug = false;

        // The range for the distance fuzzy variable
        private const float DistanceHigh = 1500;
        private const float DistanceLow  = 0;

        //-------------- Crisp input ----------------- 
        private int distance;
        // ------------- Crisp Input -----------------

        private Game game;
        private FuzzyVariable fuzzyDistance;

        public Enemy    Enemy    { get; set; }
        public Ship     Ship     { get; set; }
        public ShipInfo ShipInfo { get; set; }

        private DateTime lastPrint = DateTime.Now;

        /// <summary>
        /// Creates a new ShipEnemyInfo instance.
        /// </summary>
        /// <param name="enemy">The enemy this information is relevant to</param>
        /// <param name="ship">The players ship</param>
        /// <param name="shipInfo">General ship information</param>
        /// <param name="game">The game</param>
        public ShipEnemyInfo(Enemy enemy, Ship ship, ShipInfo shipInfo, Game game)
        {
            this.Enemy    = enemy;
            this.Ship     = ship;
            this.ShipInfo = shipInfo;
            this.game     = game;
<<<<<<< HEAD

            fuzzyDistance = new FuzzyVariable { Name = "Distance" };
        }

        /// <summary>
        /// The fuzzy distance from the player to the enemy.
        /// </summary>
=======
            
            fuzzyDistance = new FuzzyVariable { Name = "Distance" };
        }

        private Game          game;
        private FuzzyVariable fuzzyDistance;

        private DateTime lastPrint = DateTime.Now;

        //-------------- Crisp input ----------------- 
        // Distance
        private int distance;

        // ------------- Crisp Input -----------------

        // Distance
>>>>>>> 1627f196e7707136c4f509c8182908f40de0fc5b
        public FuzzyVariable Distance
        {
            get
            {
                return grade(distance, DistanceLow, DistanceHigh, fuzzyDistance);
            }
        }

        /// <summary>
        /// True if the enemy is on the screen.
        /// </summary>
        public bool EnemyVisible
        {
            get
            {
                if (Enemy.Alive)
                {
                    return game.InPlayState.OnScreenWorld.Contains(new Point(Enemy.X, Enemy.Y));
                }
                else
                {
                    return false;
                }
            }

        }

        /// <summary>
        /// A vector from the enemy to the ship representing its line of sight.
        /// </summary>
        public Vector2 LineOfSight { 
            get 
            {
                var shipPos  = new Vector2(Ship.X, Ship.Y);
                var enemyPos = new Vector2(Enemy.X, Enemy.Y);

                var diff = (shipPos - enemyPos);
                diff.Normalize();

                return diff;
            } 
        }

        public override void Update()
        {
            // update distance
            int xDiff = Ship.X - Enemy.X;
            int yDiff = Ship.Y - Enemy.Y;

            distance = (int) Math.Sqrt((xDiff * xDiff) + (yDiff * yDiff));

            if (DisplayDebug)
            {
                PrintDebuggingInfo();
            }
        }
        /// <summary>
        /// Displays details of the fuzzy variables to the console every second.
        /// </summary>
        private void PrintDebuggingInfo()
        {
            if ((DateTime.Now - lastPrint).Seconds >= 1)
            {
                Console.WriteLine("Ship/Enemy Info:");
                Console.WriteLine(Distance);
                Console.WriteLine();

                lastPrint = DateTime.Now;
            }
        }
    }
}
