using Microsoft.Xna.Framework;
using SpaceFist.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.AI;
using SpaceFist.AI.DummyAI;

namespace SpaceFist.Entities
{
    public class Enemy : Entity
    {
       
       // The dimensions of the enemy
       public  const int WIDTH = 60;
       public const int HEIGHT = 133;

       // The frames to use from the spritesheet
       private const int LeftIndex   = 0;
       private const int AtRestIndex = 4;
       private const int RightIndex  = 7;

       public EnemyAI AI {get; set;}
        

       public List<Vector2> WayPoints { get; set; }
       // A list of waypoints to follow (hardcoded for now)

       public Enemy(Game game, Vector2 position) : 
           base(game, 
                new Rectangle((int) position.X, (int) position.Y, WIDTH, HEIGHT),
                new Physics(), 
                new NullInputComponent(), 
                new IndexedSprite(game.EnemySheet, WIDTH, HEIGHT, AtRestIndex), 
                new NullSoundComponent(), game.ScreenScale)
       {
           WayPoints = new List<Vector2>();
           Rotation = (float) ((3 * Math.PI) / 2);
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
           if (WayPoints.Count != 0)
           {
               var wayPoint = WayPoints[0];
              
               // If the enemy is close to the waypoint, remove the way point
               // and draw the enemy at rest.
               if(Near(X, Y, (int) wayPoint.X, (int) wayPoint.Y)) {
                   WayPoints.Remove(wayPoint);
                   ((IndexedSprite)graphics).Index = AtRestIndex;
               }
               else
               {
                   // The line of sight vector
                   var direction = (wayPoint - new Vector2(X, Y));
        
                   // The rotation of the ship needed for it to face in the direction of the next waypoint
                   var destRotation = (float) MathHelper.ToDegrees((float)(Math.Atan2(direction.Y, direction.X))) + 90;

                   Rotation = MathHelper.ToRadians(destRotation);

                   // Convert the direction to a unit vector
                   direction.Normalize();

                   // Calculate a velocity to move along the line of sight at a magnitude of 5
                   Velocity = direction * 5;

                   var indexedSprite = (IndexedSprite) graphics;
               }
           }

           AI.Update();
           base.Update();
       }
    }
}
