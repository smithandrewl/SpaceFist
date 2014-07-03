using Microsoft.Xna.Framework;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.Entities;
using System;

namespace SpaceFist.AI
{
    /// <summary>
    /// Provides information about the players ship that is specific to a particular enemy.
    /// </summary>
    public class ShipEnemyInfo : FuzzyLogicEnabled
    {
        private const bool DisplayDebug = false;

        // The range for the distance fuzzy variable
        private const float DistanceHigh = 1000;
        private const float DistanceLow  = 0;

        //-------------- Crisp input ----------------- 
        private int distance;
        // ------------- Crisp Input -----------------

        private GameData gameData;
        private FuzzyVariable fuzzyDistance;

        public Enemy    Enemy    { get; set; }
        public ShipInfo ShipInfo { get; set; }

        private DateTime lastPrint = DateTime.Now;

        /// <summary>
        /// Creates a new ShipEnemyInfo instance.
        /// </summary>
        /// <param name="enemy">The enemy this information is relevant to</param>
        /// <param name="shipInfo">General ship information</param>
        /// <param name="gameData">Common game Data</param>
        public ShipEnemyInfo(Enemy enemy, ShipInfo shipInfo, GameData gameData)
        {
            this.Enemy    = enemy;
            this.ShipInfo = shipInfo;
            this.gameData = gameData;

            /// <summary>
            /// The fuzzy distance from the player to the enemy.
            /// </summary>
            fuzzyDistance = new FuzzyVariable { Name = "Distance" };
        }

        // Distance
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
                    return gameData.OnScreenWorld.Contains(new Point(Enemy.X, Enemy.Y));
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
                var shipPos  = new Vector2(gameData.Ship.X, gameData.Ship.Y);
                var enemyPos = new Vector2(Enemy.X, Enemy.Y);

                var diff = (shipPos - enemyPos);
                diff.Normalize();

                return diff;
            } 
        }

        public override void Update()
        {
            // update distance
            int xDiff = gameData.Ship.X - Enemy.X;
            int yDiff = gameData.Ship.Y - Enemy.Y;

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
