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
                And(Or(ShipFacingAway, ShipFacingElseWhere), ShipInfo.MovingVeryQuickly);
                return 0;
            }
        }

        public float ShipMovingQuicklyAway
        {
            get
            {
                And(Or(ShipFacingAway, ShipFacingElseWhere), ShipInfo.MovingQuickly);
                return 0;
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
                
                // trap
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
        public float ShipExtremelyClose { get; set; }
        public float ShipVeryClose      { get; set; }
        public float ShipClose          { get; set; }
        public float ShipAway           { get; set; }
        public float ShipFar            { get; set; }
        public float ShipVeryFar        { get; set; }
        public float ShipExtremelyFar   { get; set; }

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
