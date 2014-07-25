package com.spacefist.ai.defensiveai;

import com.spacefist.GameData;
import com.spacefist.ai.abst.EnemyAI;
import com.spacefist.entities.enemies.Enemy;
import com.spacefist.ai.ShipEnemyInfo;
import com.spacefist.ai.ShipInfo;

public class DefensiveAI implements EnemyAI
{
    // Fuzzy sets
    private DefendState defendState;
    private FireState   fireState;

    /// <summary>
    /// Creates a new DefensiveAI instance.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    /// <param name="enemy">The enemy this AI will control</param>
    public DefensiveAI(GameData gameData, Enemy enemy)
    {
        setShipInfo(new ShipInfo(gameData));
        setShipEnemyInfo(new ShipEnemyInfo(enemy, shipInfo, gameData));

        defendState = new DefendState(this);
        fireState   = new FireState(this, gameData);
    }
    /// <summary>
    /// Fuzzy information about the ship specific to the enemy this AI controls
    /// </summary>
    private ShipEnemyInfo shipEnemyInfo;

    /// <summary>
    /// Fuzzy information about the players ship
    /// </summary>
    private ShipInfo  shipInfo;



    public void Update()
    {
        shipInfo.Update();
        shipEnemyInfo.Update();

        fireState.Update();
        defendState.Update();
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