using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.Abstract;
using SpaceFist.Entities;
using Microsoft.Xna.Framework;

namespace SpaceFist.AI.DummyAI
{
    class DummyAIDummyState : FuzzyLogicEnabled, EnemyAIState
    {
        public List<Vector2> WayPoints { get; set; }

        public EnemyAI AI {get; set;}
        public Enemy Enemy {get; set;}
        public Ship Ship { get; set; }

        private DateTime lastUpdate;
        private Random random;
        private float membership;

        public DummyAIDummyState(EnemyAI ai)
        {
            random = new Random();

            AI = ai;
            Ship = ai.ShipEnemyInfo.Ship;
            Enemy = ai.ShipEnemyInfo.Enemy;
            WayPoints = new List<Vector2>();

            lastUpdate = DateTime.Now;
        }

        private bool Near(int x1, int y1, int x2, int y2)
        {
            int tolerance = 10;

            var xIsNear = MathHelper.Distance(x1, x2) <= tolerance;
            var yIsNear = MathHelper.Distance(y1, y2) <= tolerance;

            return xIsNear && yIsNear;
        }

        public override void Update()
        {
            membership = Or(AI.ShipEnemyInfo.LowDistance, AI.ShipEnemyInfo.MediumDistance);

            var millisecondsPassed = (DateTime.Now - lastUpdate).Milliseconds;

            
            if (millisecondsPassed > 100)
            {
                if (WayPoints.Count < 3)
                {

                    int randX = random.Next(-25, 25);
                    int randY = random.Next(-25, 25);

                    var shipLocation = new Microsoft.Xna.Framework.Vector2(Ship.X + randX, Ship.Y + randY);

                    if (!WayPoints.Contains(shipLocation))
                    {
                        Vector2 lastPoint;

                        if (WayPoints.Count == 0)
                        {
                            lastPoint = new Vector2(Enemy.X, Enemy.Y);
                        }
                        else
                        {
                            lastPoint = WayPoints[WayPoints.Count - 1];
                        }
                        
                        var newPoint = shipLocation - lastPoint;
                        newPoint.Normalize();

                        newPoint = lastPoint + (newPoint * 15);
                        WayPoints.Add(newPoint);
                    }
                }

                lastUpdate = DateTime.Now;
            }

            if (WayPoints.Count != 0)
            {
                var wayPoint = WayPoints[0];

               
                // If the enemy is close to the waypoint, remove the way point
                // and draw the enemy at rest.
                if (Near(Enemy.X, Enemy.Y, (int)wayPoint.X, (int)wayPoint.Y))
                {
                    WayPoints.Remove(wayPoint);
                    //((IndexedSprite)graphics).Index = AtRestIndex;
                }
                else
                {
                    // The line of sight vector
                    var direction = (wayPoint - new Vector2(Enemy.X, Enemy.Y)) * membership;

                    // The rotation of the ship needed for it to face in the direction of the next waypoint
                    var destRotation = (float)MathHelper.ToDegrees((float)(Math.Atan2(direction.Y, direction.X))) + 90;


                    Enemy.Rotation = MathHelper.ToRadians(destRotation);
                   
                    // Convert the direction to a unit vector
                    direction.Normalize();

                    // Calculate a velocity to move along the line of sight at a magnitude of 5
                    Enemy.Velocity = (direction * 8) * membership;

//                    var indexedSprite = (IndexedSprite)graphics;
                }
            }
        }
    }
}
