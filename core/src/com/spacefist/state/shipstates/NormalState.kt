package com.spacefist.state.shipstates

import com.badlogic.gdx.graphics.Color
import com.spacefist.GameData
import com.spacefist.entities.Ship
import com.spacefist.state.abst.ShipState

/**
 * NormalState holds the behavior for the ship after it has loaded,
 * but before it has low health.
 *
 * In this state, the ship will wait until it has low health before switching
 * the ship to the low-health state.
 */
class NormalState(private val gameData: GameData) : ShipState {

    override fun update() {
        val ship = gameData.ship

        if (ship.health <= LOW_HEALTH_THRESHOLD) {
            // Change the ships state to LowHealthState
            ship.currentState = LowHealthState(gameData)
        }

    }

    override fun enteringState() {
        // TODO: Set the ship to draw opaque after entering the normal state
        gameData.ship.tint = Color.WHITE
    }

    override fun exitingState() {}

    companion object {
        // The percentage of full health at which the ship is considered to have low-health.
        private val LOW_HEALTH_THRESHOLD = 0.40f
    }
}
