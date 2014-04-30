using Microsoft.Xna.Framework;
using SpaceFist.AI.Abstract;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.AI.ProjectileBehaviors
{
    // Interception and steering behavior based 
    // on the pursuit section of the paper "Steering Behaviors For Autonomous Characters"
    // http://www.red3d.com/cwr/steer/gdc99/
    class SeekingBehavior : ProjectileBehavior
    {
        private Entity  target;
        private Vector2 origin;
        private Vector2 origVector;

        public SeekingBehavior(Vector2 unitVector, Vector2 origin, Entity target)
        {
            this.origin     = origin;
            this.target     = target;
            this.origVector = unitVector;
        }

        public void Update(Projectile projectile)
        {
            if (target.Alive)
            {
                int MaxSpeed = 10;
                int minDist  = 150;

                var xDiff = projectile.X - origin.X;
                var yDiff = projectile.Y - origin.Y;

                var distFromOrigin = (int)Math.Sqrt((xDiff * xDiff) + (yDiff * yDiff));

                if (distFromOrigin < minDist)
                {
                    projectile.Velocity = origVector * MaxSpeed;
                }
                else
                {
                    var targetPos = new Vector2(target.X, target.Y);
                    var projPos   = new Vector2(projectile.X, projectile.Y);

                    int timeToIntercept;

                    var positionDiff = new Vector2(target.X, target.Y) - new Vector2(projectile.X, projectile.Y);
                    var velocityDiff = target.Velocity - projectile.Velocity;

                    timeToIntercept = (int)(positionDiff.Length() / velocityDiff.Length());

                    var poi = targetPos + (target.Velocity * timeToIntercept);

                    var desiredVelocity = poi - projPos;
                    desiredVelocity.Normalize();

                    desiredVelocity *= MaxSpeed;

                    var steering = desiredVelocity - projectile.Velocity;

                    var newVelocity = projectile.Velocity + steering;

                    float direction = (MathHelper.ToDegrees((float)Math.Atan2(newVelocity.Y, newVelocity.X)) + 90);

                    direction = MathHelper.ToRadians(direction);

                    projectile.Rotation = direction;
                    projectile.Velocity = newVelocity;
                }
            }
            else
            {
                projectile.Alive = false;
            }
        }
    }
}
