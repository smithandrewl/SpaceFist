package com.spacefist.ai.projectilebehaviors;

import com.badlogic.gdx.math.Vector2;
import com.spacefist.ai.abst.ProjectileBehavior;
import com.spacefist.entities.Entity;
import com.spacefist.entities.Projectile;

// Interception and steering behavior based
// on the pursuit section of the paper "Steering Behaviors For Autonomous Characters"
// http://www.red3d.com/cwr/steer/gdc99/
class SeekingBehavior implements ProjectileBehavior {
    /**
     * The entity the projectile is intercepting.
     */
    private Entity target;

    /**
     * The point on world that the projectile was fired.
     */
    private Vector2 origin;

    /**
     * The direction the ship was heading when it fired the projectile.
     */
    private Vector2 origVector;

    /**
     * Creates a new SeekingBehavior instance given a target, an initial direction and an initial velocity.
     *
     * @param unitVector The direction the projectile was fired
     * @param origin The point from which the projectile was fired
     * @param target The target entity to intercept
     */
    public SeekingBehavior(Vector2 unitVector, Vector2 origin, Entity target) {
        this.origin = origin;
        this.target = target;
        origVector  = unitVector;
    }

    public void Update(Projectile projectile) {
        if (target.isAlive()) {
            int maxSpeed = 10;
            // The minimum distance from the launching point
            // that the projectile must be after being fired, before it will start
            // intercepting the target.
            int minDist = 150;

            float xDiff = projectile.getX() - origin.x;
            float yDiff = projectile.getY() - origin.y;

            // The distance of the projectile from the launch point.
            int distFromOrigin = (int) Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));

            if (distFromOrigin < minDist) {
                // If the minimum distance has not been reached,
                // continue moving in the direction fired.
                projectile.setVelocity(new Vector2(origVector.x * maxSpeed, origVector.y * maxSpeed));
            } else {
                Vector2 targetPos = new Vector2(target.getX(), target.getY());
                Vector2 projPos   = new Vector2(projectile.getX(), projectile.getY());

                int timeToIntercept;

                Vector2 positionDiff = new Vector2(target.getX(), target.getY()).sub(new Vector2(projectile.getX(), projectile.getY()));
                Vector2 velocityDiff = target.getVelocity().sub(projectile.getVelocity());

                timeToIntercept = (int) (positionDiff.len() / velocityDiff.len());

                // The point of interception
                Vector2 poi = targetPos.add(
                    new Vector2(
                        target.getVelocity().x * timeToIntercept,
                        target.getVelocity().y * timeToIntercept
                    )
                );

                Vector2 desiredVelocity = poi.sub(projPos);
                desiredVelocity.nor();

                desiredVelocity = new Vector2(
                        desiredVelocity.x * maxSpeed,
                        desiredVelocity.y * maxSpeed
                );

                Vector2 steering = desiredVelocity.sub(projectile.getVelocity());

                Vector2 newVelocity = projectile.getVelocity().add(new Vector2(steering.x * .2f, steering.y * .2f));

                float direction = (float) (Math.toDegrees((float) Math.atan2(newVelocity.y, newVelocity.x)) + 90);

                direction = (float) Math.toRadians(direction);

                projectile.setRotation(direction);
                projectile.setVelocity(newVelocity);
            }
        } else {
            // Go away if the target dies before we reach it.
            projectile.setAlive(false);
        }
    }
}
