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
        // Seven sets per variable
        // overlap of 25%
        // grade and reverse grade on the sides
        // trapezoid in the middle
        // triangle in the center

        public ShipEnemyInfo(Enemy enemy, Ship ship, ShipInfo shipInfo)
        {
            this.Enemy = enemy;
            this.Ship = ship;
            this.ShipInfo = shipInfo;
        }

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

        // Distance
        public float ShipExtremelyClose
        {
            get
            {
                return ReverseGrade(distance, 5, 20); 
            }
        }

        public float ShipVeryClose
        {
            get
            {
                return Trapezoid(distance, 5, 20, 65, 80);
            }
        }

        public float ShipClose
        {
            get
            {
                return Trapezoid(distance, 65, 80, 125, 140);
            }
        }

        public float ShipAway
        {
            get
            {
                return Triangle(distance, 125, 185, 155);
            }
        }

        public float ShipFar
        {
            get
            {
                return Trapezoid(distance, 170, 185, 230, 245);
            }
        }

        public float ShipVeryFar
        {
            get
            {
                return Trapezoid(distance, 230, 245, 290, 305);
            }
        }
        
        public float ShipExtremelyFar
        {
            get
            {
                return Grade(distance, 290, 305);
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

            // update rotation
        }

        public Enemy Enemy { get; set; }
        public Ship Ship   { get; set; }

        public ShipInfo ShipInfo { get; set; }
    }
}
