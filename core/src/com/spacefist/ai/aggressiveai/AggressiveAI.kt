package com.spacefist.ai.aggressiveai

import com.spacefist.GameData
import com.spacefist.ai.ShipEnemyInfo
import com.spacefist.ai.ShipInfo
import com.spacefist.ai.abst.EnemyAI
import com.spacefist.entities.enemies.Enemy

/**
 * An aggressive AI which follows the ship and attempts to ram it.
 *
 * @param gameData Common game data
 * @param enemy The enemy this AI will control
*/
class AggressiveAI
(gameData: GameData, enemy: Enemy) : EnemyAI {
    /**
     * The ram fuzzy state
     */
    private val ramState:      RamState
    private var shipEnemyInfo: ShipEnemyInfo? = null
    private var shipInfo:      ShipInfo?      = null

    init {
        shipInfo      = ShipInfo(gameData)
        shipEnemyInfo = ShipEnemyInfo(enemy, shipInfo!!, gameData)
        ramState      = RamState(this, gameData)
    }

    override fun update() {
        val shipInfo      = this.shipInfo
        val shipEnemyInfo = this.shipEnemyInfo

        shipInfo!!.update()
        shipEnemyInfo!!.update()
        ramState.update()
    }

    override fun getShipEnemyInfo(): ShipEnemyInfo? {
        return shipEnemyInfo
    }

    override fun setShipEnemyInfo(shipEnemyInfo: ShipEnemyInfo) {
        this.shipEnemyInfo = shipEnemyInfo
    }

    override fun getShipInfo(): ShipInfo? {
        return shipInfo
    }

    override fun setShipInfo(shipInfo: ShipInfo) {
        this.shipInfo = shipInfo
    }
}
