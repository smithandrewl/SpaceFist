package com.spacefist.ai.projectilebehaviors;

import com.badlogic.gdx.math.Vector2;
import com.spacefist.ai.abst.ProjectileBehavior;
import com.spacefist.entities.Entity;
import com.spacefist.entities.Projectile;

// Interception and steering behavior based
// on the pursuit section of the paper "Steering Behaviors For Autonomous Characters"
// http://www.red3d.com/cwr/steer/gdc99/
public class SeekingBehavior implements ProjectileBehavior {
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

    public void update(Projectile projectile) {
        if (target.isAlive()) {
            int maxSpeed = 10;
            // The minimum distance from the launching point
            // that the projectile must be after being fired, before it will start
            // intercepting the target.
            float minDist = 150.0f;

            Vector2 projectilePos = new Vector2(projectile.getX(), projectile.getY());

            // The distance of the projectile from the launch point.
            float distFromOrigin = projectilePos.dst(origin);

            if (distFromOrigin < minDist) {
                // If the minimum distance has not been reached,
                // continue moving in the direction fired.
                projectile.setVelocity(origVector.scl(maxSpeed));
            } else {
                Vector2 targetPos = new Vector2(target.getX(), target.getY());
                Vector2 projPos   = new Vector2(projectile.getX(), projectile.getY());

                int timeToIntercept;

                Vector2 positionDiff = targetPos.sub(projPos);
                Vector2 velocityDiff = target.getVelocity().sub(projectile.getVelocity());

                timeToIntercept = (int) (positionDiff.len() / velocityDiff.len());

                // The point of interception
                Vector2 poi = targetPos.add(target.getVelocity().scl(timeToIntercept));

                Vector2 desiredVelocity = poi.sub(projPos);
                desiredVelocity.nor();

                desiredVelocity = desiredVelocity.scl(maxSpeed);

                Vector2 steering = desiredVelocity.sub(projectile.getVelocity());

                Vector2 newVelocity = projectile.getVelocity().add(steering.scl(0.2f));

                float direction = newVelocity.angle() + 90;

                projectile.setRotation(direction);
                projectile.setVelocity(newVelocity);
            }
        } else {
            // Go away if the target dies before we reach it.
            projectile.setAlive(false);
        }
    }
}
