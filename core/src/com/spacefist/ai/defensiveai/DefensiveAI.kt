package com.spacefist.ai.defensiveai

import com.spacefist.GameData
import com.spacefist.ai.ShipEnemyInfo
import com.spacefist.ai.ShipInfo
import com.spacefist.ai.abst.EnemyAI
import com.spacefist.entities.enemies.Enemy

class DefensiveAI
/**
 * Creates a new DefensiveAI instance.
 *
 * @param gameData Common game data
 * @param enemy The enemy this AI will control
 */
(gameData: GameData, enemy: Enemy) : EnemyAI {
    // Fuzzy sets
    private val defendState: DefendState
    private val fireState:   FireState

    /**
     * Fuzzy information about the ship specific to the enemy this AI controls
     */
    private var shipEnemyInfo: ShipEnemyInfo? = null

    /**
     * Fuzzy information about the players ship
     */
    private var shipInfo: ShipInfo? = null

    init {
        shipInfo      = ShipInfo(gameData)
        shipEnemyInfo = ShipEnemyInfo(enemy, shipInfo!!, gameData)
        defendState   = DefendState(this)
        fireState     = FireState(this, gameData)
    }

    override fun update() {
        shipInfo!!.update()
        shipEnemyInfo!!.update()

        fireState.update()
        defendState.update()
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