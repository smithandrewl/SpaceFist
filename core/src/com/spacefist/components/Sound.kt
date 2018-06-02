package com.spacefist.components

import com.spacefist.GameData
import com.spacefist.components.abst.SoundComponent
import com.spacefist.entities.Entity

/**
 * A simple sound component that plays a sound effect.
 */
class Sound
/**
 * Creates a new Sound instance from a SoundEffect.
 *
 * @param sound The sound to play
 */
(internal var soundEffect: com.badlogic.gdx.audio.Sound) : SoundComponent {

    /**
     * Plays the sound.
     */
    fun play() {
        soundEffect.play()
    }

    override fun update(gameData: GameData, obj: Entity) {}
}