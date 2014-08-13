package com.spacefist.ai.aggressiveai;

import com.spacefist.GameData;
import com.spacefist.ai.ShipEnemyInfo;
import com.spacefist.ai.ShipInfo;
import com.spacefist.ai.abst.EnemyAI;
import com.spacefist.entities.enemies.Enemy;

/// <summary>
/// An aggressive AI which follows the ship and attempts to ram it.
/// </summary>
public class AggressiveAI implements EnemyAI {
    /**
     * The ram fuzzy state
     */
    private RamState      ramState;
    private ShipEnemyInfo ShipEnemyInfo;
    private ShipInfo      ShipInfo;

    /**
     * Creates a new AggressiveAI instance.
     *
     * @param gameData Common game data
     * @param enemy The enemy this AI will control
     */
    public AggressiveAI(GameData gameData, Enemy enemy) {
        setShipInfo(new ShipInfo(gameData));
        setShipEnemyInfo(new ShipEnemyInfo(enemy, getShipInfo(), gameData));

        ramState = new RamState(this, gameData);
    }

    public void Update() {
        ShipInfo      shipInfo      = getShipInfo();
        ShipEnemyInfo shipEnemyInfo = getShipEnemyInfo();

        shipInfo.update();
        shipEnemyInfo.update();
        ramState.update();
    }

    @Override
    public ShipEnemyInfo getShipEnemyInfo() {
        return ShipEnemyInfo;
    }

    @Override
    public void setShipEnemyInfo(ShipEnemyInfo shipEnemyInfo) {
        ShipEnemyInfo = shipEnemyInfo;
    }

    @Override
    public ShipInfo getShipInfo() {
        return ShipInfo;
    }

    @Override
    public void setShipInfo(ShipInfo shipInfo) {
        ShipInfo = shipInfo;
    }
}
