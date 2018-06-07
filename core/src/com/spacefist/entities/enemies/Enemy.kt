package com.spacefist.entities.enemies

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.ai.abst.EnemyAI
import com.spacefist.components.NullInputComponent
import com.spacefist.components.Physics
import com.spacefist.components.Sprite
import com.spacefist.entities.Entity

/**
 * The parent class of all game enemies.
 */
open class Enemy(
    gameData:     GameData,
    enemyTexture: Texture,
    sound:        Sound,
    position:     Vector2
) :Entity(
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
