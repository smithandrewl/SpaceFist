package com.spacefist.state.shipstates;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.state.abst.ShipState;

/**
 * LowHealthState determines the appearance of the ship when it has low health
 */
public class LowHealthState implements ShipState {
    private GameData gameData;

    public LowHealthState(GameData gameData) {
        this.gameData = gameData;
    }

    public void update() {
        Ship ship = gameData.getShip();

        /* TODO: Fade the ship from invisible to visible while also fading between its normal colors and a red tint during the low health state
        ship.Tint.A = (byte)((ship.Tint.A + 20) % 255);
        ship.Tint.G = (byte)(((ship.Tint.G + 10) % 255));
        ship.Tint.B = (byte)(((ship.Tint.B + 10) % 255));
        */

        // If the ship has died, switch to the respawning state
        if (ship.getHealth() <= 0) {
            ship.setCurrentState(new SpawningState(gameData));
        }
    }

    public void enteringState() { }
    public void exitingState()  { }
}
