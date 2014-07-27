package com.spacefist.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.ai.defensiveai.DefensiveAI;

/**
 * Represents a bulky enemy freighter that does not follow the player, but fires in its
 * direction.
 */
public class EnemyFreighter extends Enemy {
    /**
     * Creates a new EnemyFreighter instance at a specified location.
     *
     * @param gameData Common game data
     * @param position The location to place the freighter in the world
     */
    public EnemyFreighter(GameData gameData, Vector2 position) {
        super(
            gameData,
            gameData.getTextures().get("EnemyFreighter"),
            gameData.getSoundEffects().get("Explosion"),
            position
        );

        setAi(new DefensiveAI(gameData, this));
    }
}
