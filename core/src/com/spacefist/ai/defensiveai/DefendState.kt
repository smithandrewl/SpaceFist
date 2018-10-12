package com.spacefist.ai.defensiveai

import com.spacefist.ai.ShipEnemyInfo
import com.spacefist.ai.ShipInfo
import com.spacefist.ai.abst.EnemyAI
import com.spacefist.ai.abst.EnemyAIState
import com.spacefist.ai.abst.FuzzyLogicEnabled
import com.spacefist.entities.enemies.Enemy

/**
 * The fuzzy state for a non-firing AI
 */
data class DefendState(private val ai: EnemyAI) : FuzzyLogicEnabled(), EnemyAIState {
    private val shipInfo:      ShipInfo      = ai.shipInfo
    private val shipEnemyInfo: ShipEnemyInfo = ai.shipEnemyInfo
    private val enemy:         Enemy?        = null

    override fun update() {}
}
