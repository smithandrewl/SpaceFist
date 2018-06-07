package com.spacefist.entities.enemies

import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.ai.aggressiveai.AggressiveAI

/**
 * Represents an enemy fighter with aggressive ramming behavior.
 *
 * @param gameData Common game data
 * @param position The place in the world to put the fighter
 */
class EnemyFighter(
    gameData: GameData,
    position: Vector2
) : Enemy(
    gameData,
    gameData.textures["EnemyFighter"]!!,
    gameData.soundEffects["Explosion"]!!,
    position
) {
    init {
        ai = AggressiveAI(gameData, this)
    }
}
