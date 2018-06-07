package com.spacefist.state.shipstates

import com.spacefist.GameData
import com.spacefist.entities.Ship
import com.spacefist.state.abst.ShipState

/**
 *
 * LowHealthState determines the appearance of the ship when it has low health
 */
class LowHealthState(private val gameData: GameData) : ShipState {

    override fun update() {
        var ship = gameData.ship

        /*
        TODO: Convert Ship Flashing

        Color color = new Color(ship.getTint());


        color.a = (byte)((color.a + 20)  % 255);
        color.g = (byte)(((color.g + 10) % 255));
        color.b = (byte)(((color.b + 10) % 255));


        ship.setTint(color);
        */

        // If the ship has died, switch to the respawning state
        if (ship.health <= 0) {
            ship.setCurrentState(SpawningState(gameData))
        }
    }

    override fun enteringState() {}
    override fun exitingState() {}
}
