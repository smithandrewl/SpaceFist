package com.spacefist.entities.enemies

import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.ai.defensiveai.DefensiveAI

/**
 * Represents a bulky enemy freighter that does not follow the player, but fires in its
 * direction.
 */
class EnemyFreighter
/**
 * Creates a new EnemyFreighter instance at a specified location.
 *
 * @param gameData Common game data
 * @param position The location to place the freighter in the world
 */
(gameData: GameData, position: Vector2) : Enemy(gameData, gameData.textures["EnemyFreighter"]!!, gameData.soundEffects["Explosion"]!!, position) {
    init {

        ai = DefensiveAI(gameData, this)
    }
}
