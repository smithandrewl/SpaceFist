package com.spacefist.state.shipstates;

import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.state.abst.ShipState;

/**
 *
 * LowHealthState determines the appearance of the ship when it has low health
 */
public class LowHealthState implements ShipState {
    private GameData gameData;

    public LowHealthState(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void update() {
        Ship ship = gameData.getShip();

        /*
        TODO: Convert Ship Flashing

        Color color = new Color(ship.getTint());


        color.a = (byte)((color.a + 20)  % 255);
        color.g = (byte)(((color.g + 10) % 255));
        color.b = (byte)(((color.b + 10) % 255));


        ship.setTint(color);
        */

        // If the ship has died, switch to the respawning state
        if (ship.getHealth() <= 0) {
            ship.setCurrentState(new SpawningState(gameData));
        }
    }

    @Override
    public void enteringState() { }
    @Override
    public void exitingState()  { }
}
