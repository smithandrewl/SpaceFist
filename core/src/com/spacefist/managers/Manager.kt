package com.spacefist.managers

import com.badlogic.gdx.utils.Array
import com.spacefist.GameData
import com.spacefist.entities.Entity

open class Manager<T : Entity>(protected var gameData: GameData) : Iterable<T> {

    protected var entities: Array<T> = Array(false, 16)

    protected fun add(entity: T) {
        entities.add(entity)
    }

    protected fun remove(entity: T) {
        entities.removeValue(entity, true)
    }

    open fun update() {
        entities
            .filter  { it.isAlive  }
            .forEach { it.update() }
    }

    fun draw() {
        entities.forEach { entity -> entity.draw() }
    }

    fun collisions(obj: Entity): Iterable<T> {
        val collisions = Array<T>(false, 16)

        for (entity in entities) {
            if (entity.isAlive && entity.rectangle!!.overlaps(obj.rectangle)) {
                collisions.add(entity)
            }
        }

        return collisions
    }

    fun clear() {
        entities.clear()
    }

    override fun iterator(): Iterator<T> {
        return entities.iterator()
    }
}
