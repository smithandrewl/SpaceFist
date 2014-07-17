using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.Abstract;
using SpaceFist.Entities;
using Microsoft.Xna.Framework;

namespace SpaceFist.AI.DummyAI
{
    /// <summary>
    /// A fuzzy state that rams the players ship.
    /// 
    /// The likelyhood of ramming the players ship and the path the enemy takes are determined
    /// by the degree that the state is active.
    /// </summary>
    class RamState : FuzzyLogicEnabled, EnemyAIState
    {
        public List<Vector2> WayPoints { get; set; }
        public EnemyAI       AI        { get; set; }
        public Enemy         Enemy     { get; set; }

        private GameData gameData;
        private DateTime lastUpdate;
        private Random   random;
        private float    membership;

        private const int Speed = 6;

        public RamState(EnemyAI ai, GameData gameData)
        {
            random = new Random();

            AI        = ai;
            Enemy     = ai.ShipEnemyInfo.Enemy;
            WayPoints = new List<Vector2>();

            lastUpdate = DateTime.Now;

            this.gameData = gameData;
        }

        /// <summary>
        /// Determines whether or not one point is near another (within 10 pixels).
        /// </summary>
        /// <param name="x1">The X coordinate of point 1</param>
        /// <param name="y1">The Y coordinate of point 1</param>
        /// <param name="x2">The X coordinate of point 2</param>
        /// <param name="y2">The Y coordinate of point 2</param>
        /// <returns>Returns true if the two points are within 10 pixels of each other</returns>
        private static bool Near(int x1, int y1, int x2, int y2)
        {
            int tolerance = 10;

            var xIsNear = MathHelper.Distance(x1, x2) <= tolerance;
            var yIsNear = MathHelper.Distance(y1, y2) <= tolerance;

            return xIsNear && yIsNear;
        }

        /// <summary>
        /// Updates the degree to which this state is active.
        /// </summary>
        public override void Update()
        {
            membership = Or(
                // If the player is doing too well
                And(AI.ShipInfo.Accuracy.High, AI.ShipInfo.Health.High),
                // If the player is not too far away
                Not(AI.ShipEnemyInfo.Distance.High)
            );

            var millisecondsPassed = (DateTime.Now - lastUpdate).Milliseconds;
            
            // Keep up to 3 waypoints, updating them every 25 milliseconds
            if (millisecondsPassed > 25)
            {
                if (WayPoints.Count < 3)
                {
                    int randX = random.Next(-10, 10);
                    int randY = random.Next(-10, 10);

                    var shipLocation = new Microsoft.Xna.Framework.Vector2(gameData.Ship.X + randX, gameData.Ship.Y + randY);

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

                        // Using fuzzy logic, the generated way point will be more accurate
                        // the closer the enemy is to the ship.
                        newPoint = lastPoint + (newPoint * 15 * membership);

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
                }
                else
                {
                    
                    // The line of sight vector
                    var direction = (wayPoint - new Vector2(Enemy.X, Enemy.Y)) * membership;
                    
                    var intX = MathHelper.Lerp(Enemy.Velocity.X, direction.X, .185f);
                    var intY = MathHelper.Lerp(Enemy.Velocity.Y, direction.Y, .185f);

                    direction = new Vector2(intX, intY);

                    // Convert the direction to a unit vector
                    direction.Normalize();
                    
                    // The rotation of the ship needed for it to face in the direction of the next waypoint
                    var destRotation = (float)MathHelper.ToDegrees((float)(Math.Atan2(direction.Y, direction.X))) + 90;

                    Enemy.Rotation = MathHelper.ToRadians(destRotation);

                    // Calculate a velocity to move along the line of sight at a magnitude of 5
                    Enemy.Velocity = (direction * (MathHelper.Lerp(Enemy.Velocity.Length(), Speed, .15f) * membership));
                }
            }
        }
    }
}
