package com.spacefist.state.shipstates;

import com.badlogic.gdx.utils.TimeUtils;
import com.spacefist.GameData;
import com.spacefist.entities.Ship;
import com.spacefist.state.abst.ShipState;

/**
 * The SpawningState determines the ships behavior when it is spawning or has died and respawned.
 *
 * The ship starts invisible and then fades to fully visible.  When 2 seconds have passed (to give it time to fade-in),
 * the state is changed to NormalState.
 */
public class SpawningState implements ShipState {
    // The number of seconds to wait for the ship to load.
    // This gives the ship time to fully fade-in from transparent to opaque.
    private static final int SPAWN_TIME = 1;

    private GameData gameData;
    private long     spawnedAt;

    public SpawningState(GameData gameData) {
        this.gameData = gameData;
    }

    public long getSpawnedAt() {
        return spawnedAt;
    }

    public void setSpawnedAt(long spawnedAt) {
        this.spawnedAt = spawnedAt;
    }

    @Override
    public void update() {
        Ship ship      = gameData.getShip();
        byte increment = 5;

        /*
        TODO: Implement fading during the spawning state

        // If the ship is not fully visible, increase its visibility
        if (ship.Tint.A < 255)
        {
            ship.Tint.A += increment;
            ship.Tint.R += increment;
            ship.Tint.G += increment;
            ship.Tint.B += increment;
        }
        */

        /*
        TODO: Calculate the time the ship has been fading in
        // The number of seconds the ship has been in this state
        var elapsed =  DateTime.Now.Subtract(SpawnedAt).Seconds;

        // After the ship fades in, switch to the normal state
        if (elapsed > SPAWN_TIME)
        {
            ship.setCurrentState(new NormalState(gameData));
        }
        */
    }

    @Override
    public void enteringState() {
        spawnedAt = TimeUtils.millis();

        /*
        TODO: draw the ship transparent when the spawning state has been entered
         */
        // Set the color to transparent so that it can fade into full visibility
        //gameData.Ship.Tint = Color.Transparent;
    }

    @Override
    public void exitingState() { }
}
