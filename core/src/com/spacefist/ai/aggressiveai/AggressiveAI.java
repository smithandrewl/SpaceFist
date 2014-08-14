package com.spacefist.ai.aggressiveai;

import com.spacefist.GameData;
import com.spacefist.ai.ShipEnemyInfo;
import com.spacefist.ai.ShipInfo;
import com.spacefist.ai.abst.EnemyAI;
import com.spacefist.entities.enemies.Enemy;

/**
 * An aggressive AI which follows the ship and attempts to ram it.
 */
public class AggressiveAI implements EnemyAI {
    /**
     * The ram fuzzy state
     */
    private RamState      ramState;
    private ShipEnemyInfo shipEnemyInfo;
    private ShipInfo      shipInfo;

    /**
     * Creates a new AggressiveAI instance.
     *
     * @param gameData Common game data
     * @param enemy The enemy this AI will control
     */
    public AggressiveAI(GameData gameData, Enemy enemy) {
        shipInfo      = new ShipInfo(gameData);
        shipEnemyInfo = new ShipEnemyInfo(enemy, shipInfo, gameData);

        ramState = new RamState(this, gameData);
    }

    public void update() {
        ShipInfo      shipInfo      = this.shipInfo;
        ShipEnemyInfo shipEnemyInfo = this.shipEnemyInfo;

        shipInfo.update();
        shipEnemyInfo.update();
        ramState.update();
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
