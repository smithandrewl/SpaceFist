using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.Abstract;
using SpaceFist.Entities;
using Microsoft.Xna.Framework;

namespace SpaceFist.AI.DummyAI
{
    class DummyAIDummyState : EnemyAIState
    {
        public EnemyAI AI {get; set;}
        public Enemy Enemy {get; set;}
        public Ship Ship { get; set; }

        private DateTime lastUpdate;

        public DummyAIDummyState(EnemyAI ai)
        {
            AI = ai;
            Ship = ai.ShipEnemyInfo.Ship;
            Enemy = ai.ShipEnemyInfo.Enemy;

            lastUpdate = DateTime.Now;
        }

        public void Update()
        {
            var millisecondsPassed = (DateTime.Now - lastUpdate).Milliseconds;

            if (millisecondsPassed > 100)
            {
                if (Enemy.WayPoints.Count < 30)
                {
                    var shipLocation = new Microsoft.Xna.Framework.Vector2(Ship.X, Ship.Y);

                    if (!Enemy.WayPoints.Contains(shipLocation))
                    {
                        Vector2 lastPoint;

                        if (Enemy.WayPoints.Count == 0)
                        {
                            lastPoint = new Vector2(Enemy.X, Enemy.Y);
                        }
                        else
                        {
                            lastPoint = Enemy.WayPoints[Enemy.WayPoints.Count - 1];
                        }
                        
                        var newPoint = shipLocation - lastPoint;
                        newPoint.Normalize();

                        newPoint = lastPoint + (newPoint * 15);
                        Enemy.WayPoints.Add(newPoint);
                    }
                }

                lastUpdate = DateTime.Now;
            }
        }

        public void EnteringState()
        {
        }

        public void ExitingState()
        {
        }
    }
}
