using Microsoft.Xna.Framework;
using SpaceshipShooter.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceshipShooter.Entities
{
    public class Enemy : GameObject
    {
       
       public  const int WIDTH       = 60;
       public  const int HEIGHT      = 133;
       private const int LeftIndex   = 0;
       private const int AtRestIndex = 4;
       private const int RightIndex  = 7;

       protected List<Vector2> wayPoints = new List<Vector2> { 
           new Vector2(100, 100), 
           new Vector2(150, 150), 
           new Vector2(200, 200),
           new Vector2(250, 250),
           new Vector2(300, 300),
           new Vector2(350, 350),
           new Vector2(400, 400),
           new Vector2(450, 450)
       };

       public Enemy(Game game, Vector2 position) : 
           base(game, 
                new Rectangle((int) position.X, (int) position.Y, WIDTH, HEIGHT),
                new Physics(), 
                new NullInputComponent(), 
                new IndexedSprite(game.EnemySheet, WIDTH, HEIGHT, AtRestIndex), 
                new NullSoundComponent(), game.ScreenScale)
       {

       }

       private bool Near(int x1, int y1, int x2, int y2)
       {
           int tolerance = 10;

           var xIsNear = MathHelper.Distance(x1, x2) <= tolerance;
           var yIsNear = MathHelper.Distance(y1, y2) <= tolerance;

           return xIsNear && yIsNear;
       }
       public override void Update(GameTime time)
       {
           
           if (wayPoints.Count != 0)
           {
               var wayPoint = wayPoints[0];
              
               if(Near(X, Y, (int) wayPoint.X, (int) wayPoint.Y)) {
                   wayPoints.Remove(wayPoint);
                   ((IndexedSprite)graphics).Index = AtRestIndex;
               }
               else
               {
                   var direction = (wayPoint - new Vector2(X, Y));

                   var destRotation = (float) -(Math.Atan(direction.Y / direction.X));

                   if (Rotation < destRotation)
                       Rotation += .03f;
                   else if (Rotation > destRotation)
                       Rotation-= .03f;

                   direction.Normalize();

                   Velocity = direction * 5;

                   var indexedSprite = (IndexedSprite) graphics;
                   if (direction.X < 0)
                   {
                       indexedSprite.Index = LeftIndex;
                   }
                   else if (direction.X == 0)
                   {
                       indexedSprite.Index = AtRestIndex;
                   }
                   else
                   {
                       indexedSprite.Index = RightIndex;
                   }
               }
           }
           base.Update(time);
       }
           
    }
}
