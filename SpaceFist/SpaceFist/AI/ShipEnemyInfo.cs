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
        public ShipEnemyInfo(Enemy enemy, Ship ship, ShipInfo shipInfo)
        {
            this.Enemy = enemy;
            this.Ship = ship;
            this.ShipInfo = shipInfo;
        }

        private DateTime lastPrint = DateTime.Now;

        //-------------- Crisp input ----------------- 
        // Distance
        private int distance;
        private int speed;

        // ------------- Crisp Input -----------------



        // Distance
        public float LowDistance
        {
            get
            {
                return ReverseGrade(distance, 5, 147.5f);
            }
        }

        public float MediumDistance
        {
            get
            {
                return Triangle(distance, 5, 147.5f, 300);
            }
        }

        public float HighDistance
        {
            get
            {
                return Grade(distance, 147.5f, 300);
            }
        }

        // visible
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

            
            speed      = (int) Ship.Velocity.Length();

            if ((DateTime.Now - lastPrint).Seconds >= 1)
            {
                Console.WriteLine("Distance: {0}, {1:P3}, {2:P3}, {3:P3}", distance, LowDistance, MediumDistance, HighDistance);

                lastPrint = DateTime.Now;
            }
        }

        public Enemy Enemy { get; set; }
        public Ship Ship   { get; set; }

        public ShipInfo ShipInfo { get; set; }
    }
}
