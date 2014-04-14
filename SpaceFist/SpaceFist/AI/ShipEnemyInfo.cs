using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using SpaceFist.Entities;

namespace SpaceFist.AI
{
    public class ShipEnemyInfo : FuzzyLogicEnabled
    {
        private const bool DisplayDebug = false;

        private const float DistanceHigh = 950;
        private const float DistanceLow  = 0;

        public ShipEnemyInfo(Enemy enemy, Ship ship, ShipInfo shipInfo)
        {
            this.Enemy    = enemy;
            this.Ship     = ship;
            this.ShipInfo = shipInfo;

            fuzzyDistance = new FuzzyVariable { Name = "Distance" };
        }

        private FuzzyVariable fuzzyDistance;

        private DateTime lastPrint = DateTime.Now;

        //-------------- Crisp input ----------------- 
        // Distance
        private int distance;

        // ------------- Crisp Input -----------------

        // Distance
        public FuzzyVariable Distance
        {
            get
            {
                return grade(distance, DistanceLow, DistanceHigh, fuzzyDistance);
            }
        }

        public bool ShipVisible { get; set; }

        public float ShipFacingAway      { get; set; }
        public float ShipFacingElseWhere { get; set; }
        public float ShipFacingUs        { get; set; }

        // Interception information
        public int     TimeToIntercept   { get; set; }
        public Vector2 LineOfSight       { get; set; }
        public Vector2 InterceptVelocity { get; set; }
        public Vector2 InterceptPoint    { get; set; }

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

        public Enemy    Enemy    { get; set; }
        public Ship     Ship     { get; set; }
        public ShipInfo ShipInfo { get; set; }
    }
}
