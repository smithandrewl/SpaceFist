package com.spacefist.ai.projectilebehaviors

import com.badlogic.gdx.math.Vector2
import com.spacefist.ai.abst.ProjectileBehavior
import com.spacefist.entities.Entity
import com.spacefist.entities.Projectile

// Interception and steering behavior based
// on the pursuit section of the paper "Steering Behaviors For Autonomous Characters"
// http://www.red3d.com/cwr/steer/gdc99/
class SeekingBehavior
/**
 * Creates a new SeekingBehavior instance given a target, an initial direction and an initial velocity.
 *
 * @param unitVector The direction the projectile was fired
 * @param origin The point from which the projectile was fired
 * @param target The target entity to intercept
 */
(
        /**
         * The direction the ship was heading when it fired the projectile.
         */
        private val origVector: Vector2,
        /**
         * The point on world that the projectile was fired.
         */
        private val origin: Vector2,
        /**
         * The entity the projectile is intercepting.
         */
        private val target: Entity) : ProjectileBehavior {

    override fun update(projectile: Projectile) {
        if (target.isAlive) {
            val maxSpeed = 10
            // The minimum distance from the launching point
            // that the projectile must be after being fired, before it will start
            // intercepting the target.
            val minDist = 150.0f

            val projectilePos = Vector2(projectile.x.toFloat(), projectile.y.toFloat())

            // The distance of the projectile from the launch point.
            val distFromOrigin = projectilePos.dst(origin)

            if (distFromOrigin < minDist) {
                // If the minimum distance has not been reached,
                // continue moving in the direction fired.
                projectile.velocity = origVector.scl(maxSpeed.toFloat())
            } else {
                val targetPos = Vector2(target.x.toFloat(), target.y.toFloat())
                val projPos = Vector2(projectile.x.toFloat(), projectile.y.toFloat())

                val timeToIntercept: Int

                val positionDiff = targetPos.sub(projPos)
                val velocityDiff = target.velocity.sub(projectile.velocity)

                timeToIntercept = (positionDiff.len() / velocityDiff.len()).toInt()

                // The point of interception
                val poi = targetPos.add(target.velocity.scl(timeToIntercept.toFloat()))

                var desiredVelocity = poi.sub(projPos)
                desiredVelocity.nor()

                desiredVelocity = desiredVelocity.scl(maxSpeed.toFloat())

                val steering = desiredVelocity.sub(projectile.velocity)

                val newVelocity = projectile.velocity.add(steering.scl(0.2f))

                val direction = newVelocity.angle() + 90

                projectile.rotation = direction
                projectile.velocity = newVelocity
            }
        } else {
            // Go away if the target dies before we reach it.
            projectile.isAlive = false
        }
    }
}
