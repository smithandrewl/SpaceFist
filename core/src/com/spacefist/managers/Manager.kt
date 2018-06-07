package com.spacefist.managers

import com.badlogic.gdx.utils.Array
import com.spacefist.GameData
import com.spacefist.entities.Entity

open class Manager<T : Entity>(protected var gameData: GameData) : Iterable<T> {

    protected var entities: Array<T>

    init {
        entities = Array(false, 16)
    }

    protected fun add(entity: T) {
        assert(entity != null)

        entities.add(entity)
    }

    protected fun remove(entity: T) {
        assert(entity != null)

        entities.removeValue(entity, true)
    }

    open fun update() {
        for (entity in entities) {
            if (entity.isAlive) {
                entity.update()
            }
        }
    }

    fun draw() {
        for (entity in entities) {
            entity.draw()
        }
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
