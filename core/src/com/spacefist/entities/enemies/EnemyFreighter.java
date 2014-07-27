package com.spacefist.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.ai.defensiveai.DefensiveAI;

/// <summary>
///  Represents a bulky enemy freighter that does not follow the player, but fires in its
///  direction.
/// </summary>
public class EnemyFreighter extends Enemy {
    /// <summary>
    /// Creates a new EnemyFreighter instance at a specified location.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    /// <param name="position">The location to place the freighter in the world.</param>
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
