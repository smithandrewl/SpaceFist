package com.spacefist.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.ai.aggressiveai.AggressiveAI;

/**
 * Represents an enemy fighter with aggressive ramming behavior.
 */
public class EnemyFighter extends Enemy {
    /**
     * Creates a new EnemyFighter instance at a specified location.
     *
     * @param gameData Common game data
     * @param position The place in the world to put the fighter
     */
    public EnemyFighter(GameData gameData, Vector2 position) {
        super(
            gameData,
            gameData.getTextures().get("EnemyFighter"),
            gameData.getSoundEffects().get("Explosion"),
            position
        );

        setAi(new AggressiveAI(gameData, this));
    }
}
