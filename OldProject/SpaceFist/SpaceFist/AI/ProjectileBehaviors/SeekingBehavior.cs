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
        /// <summary>
        /// The entity the projectile is intercepting.
        /// </summary>
        private Entity  target;

        /// <summary>
        /// The point on world that the projectile was fired.
        /// </summary>
        private Vector2 origin;

        /// <summary>
        /// The direction the ship was heading when it fired the projectile.
        /// </summary>
        private Vector2 origVector;

        /// <summary>
        /// Creates a new SeekingBehavior instance given a target, an initial direction and an initial velocity.
        /// </summary>
        /// <param name="unitVector">The direction the projectile was fired.</param>
        /// <param name="origin">The point from which the projectile was fired.</param>
        /// <param name="target">The target entity to intercept.</param>
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
                // The minimum distance from the launching point
                // that the projectile must be after being fired, before it will start
                // intercepting the target.
                int minDist  = 150;

                var xDiff = projectile.X - origin.X;
                var yDiff = projectile.Y - origin.Y;

                // The distance of the projectile from the launch point.
                var distFromOrigin = (int)Math.Sqrt((xDiff * xDiff) + (yDiff * yDiff));

                if (distFromOrigin < minDist)
                {
                    // If the minimum distance has not been reached,
                    // continue moving in the direction fired.
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

                    // The point of interception
                    var poi = targetPos + (target.Velocity * timeToIntercept);

                    var desiredVelocity = poi - projPos;
                    desiredVelocity.Normalize();

                    desiredVelocity *= MaxSpeed;

                    var steering = desiredVelocity - projectile.Velocity;

                    var newVelocity = projectile.Velocity + (steering * .2f);

                    float direction = (MathHelper.ToDegrees((float)Math.Atan2(newVelocity.Y, newVelocity.X)) + 90);

                    direction = MathHelper.ToRadians(direction);

                    projectile.Rotation = direction;
                    projectile.Velocity = newVelocity;
                }
            }
            else
            {
                // Go away if the target dies before we reach it.
                projectile.Alive = false;
            }
        }
    }
}
