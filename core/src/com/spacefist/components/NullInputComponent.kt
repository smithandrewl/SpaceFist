package com.spacefist.components

import com.spacefist.GameData
import com.spacefist.components.abst.InputComponent
import com.spacefist.entities.Entity

/**
 * For use by entities that do not use input.
 */
class NullInputComponent : InputComponent {
    override fun update(gameData: GameData, obj: Entity) {}
}
