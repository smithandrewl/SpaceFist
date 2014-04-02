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

        public ShipEnemyInfo(Enemy enemy, Ship ship)
        {
            this.Enemy = enemy;
            this.Ship = ship;
        }

        // Distance
        private int distance;

        // Rotation Difference
        private int rotationTo;  

        // ship movement in relation to the enemy
        public double ShipMovingAway    { get; set; }
        public double ShipMovingTowards { get; set; }
        public double ShipNotMoving     { get; set; }

        // Distance
        public double ShipVeryClose { get; set; }
        public double ShipNear      { get; set; }
        public double ShipAway      { get; set; }
        public double ShipFar       { get; set; }
        public double ShipVeryFar   { get; set; }

        // visible
        public bool ShipVisible { get; set; }

        public double ShipFacingAway      { get; set; }
        public double ShipFacingElseWhere { get; set; }
        public double ShipFacingUs        { get; set; }

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
    }
}
