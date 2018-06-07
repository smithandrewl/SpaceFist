package com.spacefist.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.ai.abst.ProjectileBehavior
import com.spacefist.ai.projectilebehaviors.NullBehavior
import com.spacefist.components.NullInputComponent
import com.spacefist.components.Physics
import com.spacefist.components.Sound
import com.spacefist.components.Sprite
import com.spacefist.components.abst.SoundComponent

/**
 * Represents a projectile fired by either the player or an enemy.
 */
class Projectile(
        gameData: GameData,
        texture: Texture,
        position: Vector2,
        unitVector: Vector2,
        speed: Int,
        val isEnemyProjectile: Boolean
) : Entity(gameData, Rectangle(
        position.x.toInt().toFloat(),
        position.y.toInt().toFloat(),
        texture.width.toFloat(),
        texture.height.toFloat()
), Physics(), NullInputComponent(), Sprite(texture), Sound(gameData.soundEffects["Laser"]!!), gameData.screenScale) {
    private var soundPlayed: Boolean = false

    var behavior: ProjectileBehavior? = null

    init {

        velocity = Vector2(
                unitVector.x * speed,
                unitVector.y * speed
        )

        behavior = NullBehavior()
    }

    /**
     * Plays a firing noise on the first update
     */
    override fun update() {
        val sound = sound

        if (!soundPlayed) {
            (sound as Sound).play()
            soundPlayed = true
        }

        behavior!!.update(this)

        super.update()
    }
}
