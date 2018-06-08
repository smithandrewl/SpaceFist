package com.spacefist.ai.defensiveai

import com.badlogic.gdx.utils.TimeUtils
import com.spacefist.GameData
import com.spacefist.ai.ShipEnemyInfo
import com.spacefist.ai.ShipInfo
import com.spacefist.ai.abst.EnemyAI
import com.spacefist.ai.abst.EnemyAIState
import com.spacefist.ai.abst.FuzzyLogicEnabled
import com.spacefist.managers.ProjectileManager

/**
 * This fuzzy state fires at the ship when the enemy is on the screen.
 *
 * @param ai The AI this state belongs to
 * @param gameData Common game data
 */
class FireState(var ai: EnemyAI?, gameData: GameData) : FuzzyLogicEnabled(), EnemyAIState {
    private var rateOfFire: Float = 0.toFloat()

    private val projectileManager: ProjectileManager

    // The last time this enemy fired at the player
    private var lastFire: Long = 0

    // Fuzzy information about the player
    var shipInfo: ShipInfo? = null

    // Fuzzy information about the player specific to this enemy
    var shipEnemyInfo: ShipEnemyInfo? = null

    init {
        shipInfo      = ai!!.getShipInfo()
        shipEnemyInfo = ai!!.getShipEnemyInfo()
        lastFire      = TimeUtils.millis()

        this.projectileManager = gameData.projectileManager
    }

    override fun update() {
        val distance = shipEnemyInfo!!.getDistance()

        rateOfFire = distance.defuzzify(700f, 550f, 300f)

        // if this enemy is on screen, fire and wait a fuzzy amount of time before
        // firing again.
        if (ai!!.shipEnemyInfo.isEnemyVisible) {
            // Fire at the ship every 200 to 600 milliseconds depending on how far away
            // the ship is.  The further the ship is, the faster the enemy will fire.
            val millSinceLastFire = TimeUtils.millis() - lastFire

            val timeToFire = millSinceLastFire > rateOfFire

            if (timeToFire) {
                val enemyRect = shipEnemyInfo!!.enemy!!.rectangle

                val halfWidth  = enemyRect!!.getWidth().toInt() / 2
                val halfHeight = enemyRect.getHeight().toInt()  / 2

                val projPosX = shipEnemyInfo!!.enemy!!.x + halfWidth
                val projPosY = shipEnemyInfo!!.enemy!!.y + halfHeight

                val lineOfSight = shipEnemyInfo!!.lineOfSight

                projectileManager.fireLaser(projPosX, projPosY, lineOfSight, true)

                lastFire = TimeUtils.millis()
            }
        }
    }
}
