package com.spacefist.entities.enemies

import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.ai.defensiveai.DefensiveAI

/**
 * Represents a bulky enemy freighter that does not follow the player, but fires in its
 * direction.
 *
 * @param gameData Common game data
 * @param position The location to place the freighter in the world
 */
class EnemyFreighter(
    gameData: GameData,
    position: Vector2
) : Enemy(
    gameData,
    gameData.textures["EnemyFreighter"]!!,
    gameData.soundEffects["Explosion"]!!,
    position
) {
    init {
        ai = DefensiveAI(gameData, this)
    }
}