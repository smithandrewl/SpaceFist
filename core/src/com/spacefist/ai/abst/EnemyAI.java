package com.spacefist.ai.abst;


import com.spacefist.ai.ShipEnemyInfo;
import com.spacefist.ai.ShipInfo;

/**
 * The interface implemented by all enemy AI's.
 */
public interface EnemyAI {
    ShipEnemyInfo getShipEnemyInfo();
    void setShipEnemyInfo(ShipEnemyInfo shipEnemyInfo);
    ShipInfo getShipInfo();
    void setShipInfo(ShipInfo shipInfo);
    void update();
}
