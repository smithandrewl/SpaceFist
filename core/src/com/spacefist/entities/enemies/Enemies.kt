package com.spacefist.entities.enemies

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.ai.abst.EnemyAI
import com.spacefist.ai.aggressiveai.AggressiveAI
import com.spacefist.ai.defensiveai.DefensiveAI
import com.spacefist.components.NullInputComponent
import com.spacefist.components.Physics
import com.spacefist.components.Sprite
import com.spacefist.entities.Entity

/**
 * The parent class of all game enemies.
 */
open class Enemy(
    gameData: GameData,
    enemyTexture: Texture,
    sound: Sound,
    position: Vector2
) : Entity(
    gameData,
    Rectangle(
            position.x.toInt().toFloat(),
            position.y.toInt().toFloat(),
            enemyTexture.width.toFloat(),
            enemyTexture.height.toFloat()
    ),
    Physics(),
    NullInputComponent(),
    Sprite(enemyTexture),
    com.spacefist.components.Sound(sound),
    0.0f
) {
    /**
     * The AI that will control this enemy.
     */
    var ai: EnemyAI? = null

    init {
        rotation = (3 * Math.PI / 2).toFloat()
    }

    override fun update() {
        ai!!.update()
        super.update()
    }

    /**
     * Plays an explosion sound.
     */
    fun onDeath() {
        (sound as com.spacefist.components.Sound).play()
    }
}
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