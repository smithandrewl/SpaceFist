package com.spacefist.ai.defensiveai;

import com.spacefist.GameData;
import com.spacefist.ai.ShipEnemyInfo;
import com.spacefist.ai.ShipInfo;
import com.spacefist.ai.abst.EnemyAI;
import com.spacefist.entities.enemies.Enemy;

public class DefensiveAI implements EnemyAI {
    // Fuzzy sets
    private DefendState defendState;
    private FireState   fireState;

    /**
     * Fuzzy information about the ship specific to the enemy this AI controls
     **/
    private ShipEnemyInfo shipEnemyInfo;

    /**
     * Fuzzy information about the players ship
     */
    private ShipInfo shipInfo;

    /**
     * Creates a new DefensiveAI instance.
     *
     * @param gameData Common game data
     * @param enemy The enemy this AI will control
     */
    public DefensiveAI(GameData gameData, Enemy enemy) {
        setShipInfo(new ShipInfo(gameData));
        setShipEnemyInfo(new ShipEnemyInfo(enemy, shipInfo, gameData));

        defendState = new DefendState(this);
        fireState   = new FireState(this, gameData);
    }

    public void Update() {
        shipInfo.update();
        shipEnemyInfo.update();

        fireState.update();
        defendState.update();
    }

    @Override
    public ShipEnemyInfo getShipEnemyInfo() {
        return shipEnemyInfo;
    }

    @Override
    public void setShipEnemyInfo(ShipEnemyInfo shipEnemyInfo) {
        this.shipEnemyInfo = shipEnemyInfo;
    }

    @Override
    public ShipInfo getShipInfo() {
        return shipInfo;
    }

    @Override
    public void setShipInfo(ShipInfo shipInfo) {
        this.shipInfo = shipInfo;
    }
}