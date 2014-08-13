package com.spacefist.ai.defensiveai;

import com.spacefist.ai.ShipEnemyInfo;
import com.spacefist.ai.ShipInfo;
import com.spacefist.ai.abst.EnemyAI;
import com.spacefist.ai.abst.EnemyAIState;
import com.spacefist.ai.abst.FuzzyLogicEnabled;
import com.spacefist.entities.enemies.Enemy;

/**
 * The fuzzy state for a non-firing AI
 */
public class DefendState extends FuzzyLogicEnabled implements EnemyAIState {
    private EnemyAI       ai;
    private ShipInfo      shipInfo;
    private ShipEnemyInfo shipEnemyInfo;
    private Enemy         enemy;

    public DefendState(EnemyAI ai) {
        this.ai       = ai;
        shipInfo      = ai.getShipInfo();
        shipEnemyInfo = ai.getShipEnemyInfo();
    }

    public void update() { }
}
