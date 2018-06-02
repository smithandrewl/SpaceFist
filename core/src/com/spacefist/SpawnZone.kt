package com.spacefist

import com.badlogic.gdx.math.Vector2

/**
 * Represents a region of a level where enemies will may spawn.
 */
class SpawnZone(val count: Int, val left: Int, val right: Int, val top: Int, val bottom: Int, val center: Vector2)