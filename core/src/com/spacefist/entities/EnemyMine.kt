package com.spacefist.entities

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.components.NullInputComponent
import com.spacefist.components.Physics
import com.spacefist.components.Sound
import com.spacefist.components.Sprite

class EnemyMine(gameData: GameData, position: Vector2) : Entity(gameData, Rectangle(
        position.x.toInt().toFloat(),
        position.y.toInt().toFloat(),
        gameData.textures["EnemyMine"]!!.getWidth().toFloat(),
        gameData.textures["EnemyMine"]!!.getHeight().toFloat()
), Physics(), NullInputComponent(), Sprite(gameData.textures["EnemyMine"]!!), Sound(gameData.soundEffects["Explosion"]!!), 0.0f) {

    fun hit() {
        (sound as Sound).play()
    }
}
