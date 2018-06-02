package com.spacefist.components

import com.spacefist.GameData
import com.spacefist.components.abst.SoundComponent
import com.spacefist.entities.Entity

/**
 * For use by entities that do not have a sound
 * The background for example
 */
class NullSoundComponent : SoundComponent {
    fun loadContent(gameData: GameData) {}
    override fun update(gameData: GameData, obj: Entity) {}
}
