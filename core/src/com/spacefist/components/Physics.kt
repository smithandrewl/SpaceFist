package com.spacefist.components

import com.spacefist.GameData
import com.spacefist.components.abst.PhysicsComponent
import com.spacefist.entities.Entity

/**
 * Updates The game objects position using its properties.
 */
class Physics : PhysicsComponent {
    override fun update(gameData: GameData, obj: Entity) {
        obj.x = obj.x + obj.velocity.x.toInt()
        obj.y = obj.y - obj.velocity.y.toInt()
        obj.rotation = obj.rotation % 360
    }
}
