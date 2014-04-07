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

        // Rotation Difference
        private int rotationTo;

        private int speed;

        // ------------- Crisp Input -----------------



        // ship movement in relation to the enemy
        public float ShipSpeedingAway
        {
            get
            {
                // reverse grade
                return And(Or(ShipFacingAway, ShipFacingElseWhere), ShipInfo.MovingVeryQuickly);
            }
        }

        public float ShipMovingQuicklyAway
        {
            get
            {
                return And(Or(ShipFacingAway, ShipFacingElseWhere), ShipInfo.MovingQuickly);
            }
        }

        public float ShipMovingAway
        {
            get
            {
                return And(Or(ShipFacingAway, ShipFacingElseWhere), ShipInfo.Moving);
            }
        }

        public float ShipNotMoving
        {
            get
            {
                return Not(ShipInfo.Moving);
            }
        }


        public float ShipMovingTowards
        {
            get
            {
                return And(ShipFacingUs, ShipInfo.Moving);
            }
        }

        public float ShipMovingQuicklyTowards
        {
            get
            {
                return And(ShipFacingUs, ShipInfo.MovingQuickly);
            }
        }
        
        public float ShipSpeedingTowards
        {
            get
            {
                return And(ShipFacingUs, ShipInfo.MovingVeryQuickly);
            }
        }

        /*
// Distance







);


         */

        public float ShipExtremelyClose
        {
            get
            {
                return ReverseGrade(distance, 5, 26.43f); 
            }
        }

        public float ShipVeryClose
        {
            get
            {
                return Trapezoid(distance, 5, 26.43f, 47.86f, 69.29f);
            }
        }

        public float ShipClose
        {
            get
            {
                return Trapezoid(distance, 47.86f, 69.29f, 90.71f, 112.14f);
            }
        }

        public float ShipAway
        {
            get
            {
                return Triangle(distance, 90.71f, 133.57f, 112.14f);
            }
        }

        public float ShipFar
        {
            get
            {
                return Trapezoid(distance, 112.14f, 133.57f, 155, 176.43f);
            }
        }

        public float ShipVeryFar
        {
            get
            {
                return Trapezoid(distance, 155, 176.43f, 197.86f, 219.29f);
            }
        }

        public float ShipExtremelyFar
        {
            get
            {
                return Grade(distance, 197.86f, 219.29f);
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
                Console.WriteLine("Distance: {0}, {1:P3}, {2:P3}, {3:P3}, {4:P3}, {5:P3}, {6:P3}, {7:P3}",distance, this.ShipExtremelyFar, ShipVeryFar, ShipFar, ShipAway, ShipClose, ShipVeryClose, ShipExtremelyClose);

                lastPrint = DateTime.Now;
            }
        }

        public Enemy Enemy { get; set; }
        public Ship Ship   { get; set; }

        public ShipInfo ShipInfo { get; set; }
    }
}
