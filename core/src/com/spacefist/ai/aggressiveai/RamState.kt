package com.spacefist.ai.aggressiveai

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.spacefist.GameData
import com.spacefist.ai.FuzzyVariable
import com.spacefist.ai.ShipEnemyInfo
import com.spacefist.ai.ShipInfo
import com.spacefist.ai.abst.EnemyAI
import com.spacefist.ai.abst.EnemyAIState
import com.spacefist.ai.abst.FuzzyLogicEnabled
import com.spacefist.entities.Ship
import com.spacefist.entities.enemies.Enemy

import java.util.ArrayList

/**
 * A fuzzy state that rams the players ship.
 *
 * The likelyhood of ramming the players ship and the path the enemy takes are determined
 * by the degree that the state is active.
 */
class RamState(var ai: EnemyAI?, private val gameData: GameData) : FuzzyLogicEnabled(), EnemyAIState {
    var enemy: Enemy? = null

    init {

        val shipEnemyInfo = ai.getShipEnemyInfo()
        enemy = shipEnemyInfo.enemy
    }

    /**
     * Updates the degree to which this state is active.
     */
    override fun update() {
        val ship = gameData.ship

        val shipInfo = ai!!.shipInfo
        val shipEnemyInfo = ai!!.shipEnemyInfo

        val accuracy = shipInfo.accuracy
        val health = shipInfo.getHealth()
        val distance = shipEnemyInfo.getDistance()

        val membership = FuzzyLogicEnabled.or(
                // If the player is doing too well
                FuzzyLogicEnabled.and(
                        accuracy.high,
                        health.high
                ),
                // If the player is not too far away
                FuzzyLogicEnabled.not(distance.high)
        )

        val shipLocation = Vector2(
                ship.x.toFloat(),
                ship.y.toFloat()
        )


        // The line of sight vector
        var direction = shipLocation.sub(
                Vector2(
                        enemy!!.x.toFloat(),
                        enemy!!.y.toFloat()
                )
        )

        direction = direction.nor()


        //direction = new Vector2(enemy.getX(), enemy.getY()).nor();

        // The rotation of the ship needed for it to face in the direction of the next waypoint
        val destRotation = direction.angle() + 90

        enemy!!.rotation = destRotation

        // Calculate a velocity to move along the line of sight at a magnitude of 5
        // TODO: Convert linear interpolation code in RamState
        //enemy.Velocity = (direction * (MathHelper.Lerp(Enemy.Velocity.Length(), SPEED, .15f) * membership));


        enemy!!.velocity = Vector2(direction.x * SPEED.toFloat() * membership, direction.y * SPEED.toFloat() * membership * -1.0f)
    }

    companion object {
        private val SPEED = 4

        /**
         * Determines whether or not one point is near another (within 10 pixels).
         *
         * @param x1 The X coordinate of point 1
         * @param y1 The Y coordinate of point 1
         * @param x2 The X coordinate of point 2
         * @param y2 The Y coordinate of point 2
         * @return Returns true if the two points are within 10 pixels of each other
         */
        private fun isNear(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
            val tolerance = 10

            val xIsNear = Math.abs(x1 - x2) <= tolerance
            val yIsNear = Math.abs(y1 - y2) <= tolerance

            return xIsNear && yIsNear
        }
    }
}
