package com.spacefist.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.ai.aggressiveai.AggressiveAI;

/// <summary>
/// Represents an enemy fighter with aggressive ramming behavior.
/// </summary>
public class EnemyFighter extends Enemy
{
    /// <summary>
    /// Creates a new EnemyFighter instance at a specified location.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    /// <param name="position">The place in the world to put the fighter</param>
    public EnemyFighter(GameData gameData, Vector2 position) {
        super(gameData, gameData.getTextures().get("EnemyFighter"), gameData.getSoundEffects().get("Explosion"), position);

        setAi(new AggressiveAI(gameData, this));
    }
}
