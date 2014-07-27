package com.spacefist.state.shipstates;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.state.abst.ShipState;

/// <summary>
/// NormalState holds the behavior for the ship after it has loaded,
/// but before it has low health.
///
/// In this state, the ship will wait until it has low health before switching
/// the ship to the low-health state.
/// </summary>
public class NormalState implements ShipState {
    // The percentage of full health at which the ship is considered to have low-health.
    private static final float LowHealthThreshold = .40f;
    private GameData gameData;

    public NormalState(GameData gameData) {
        this.gameData = gameData;
    }

    public void Update() {
        Ship ship = gameData.getShip();

        if (ship.getHealth() <= LowHealthThreshold) {
            // Change the ships state to LowHealthState
            ship.setCurrentState(new LowHealthState(gameData));
        }
    }

    public void EnteringState() {
        // TODO: Set the ship to draw opaque after entering the normal state
        //gameData.Ship.Tint = Color.White;
    }

    public void ExitingState() { }
}
