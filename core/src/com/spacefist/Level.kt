package com.spacefist

import com.badlogic.gdx.maps.objects.EllipseMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

/**
 * Represents a spawn point in a level.
 */
class SpawnPoint(val x: Int, val y: Int)

/**
 * Represents a region of a level where enemies will may spawn.
 */
class SpawnZone(val count: Int, val left: Int, val right: Int, val top: Int, val bottom: Int, val center: Vector2)

class Level(map: TiledMap) {
    val height:                 Int
    val width:                  Int
    val title:                  String
    val debrisParticleMinScale: Int
    val debrisParticleMaxScale: Int
    val debrisParticleImage:    String
    val debrisParticleCount:    Int
    val song:                   String
    val blockCount:             Int
    val backgroundImage:        String
    val levelId:                Int
    val isLastLevel:            Boolean

    private val mines:      Array<SpawnPoint>
    private val fighters:   Array<SpawnZone>
    private val freighters: Array<SpawnZone>

    init {
        mines      = Array(false, 50)
        fighters   = Array(false, 50)
        freighters = Array(false, 50)

        val properties = map.properties

        val heightInTiles = properties.get("height",     Int::class.java)
        val tileheight    = properties.get("tileheight", Int::class.java)
        val widthInTiles  = properties.get("width",      Int::class.java)
        val tileWidth     = properties.get("tilewidth",  Int::class.java)

        height = heightInTiles * tileheight
        width  = widthInTiles  * tileWidth

        title                  = properties.get("Level Title", String::class.java)

        debrisParticleMinScale = Integer.parseInt(properties.get("Debris Particle Min Scale", String::class.java))
        debrisParticleMaxScale = Integer.parseInt(properties.get("Debris Particle Max Scale", String::class.java))
        debrisParticleImage    = properties.get("Debris Particle Image", String::class.java)
        debrisParticleCount    = Integer.parseInt(properties.get("Debris Particle Count", String::class.java))

        song            = properties.get("Song", String::class.java)
        blockCount      = Integer.parseInt(properties.get("Block Count", String::class.java))
        backgroundImage = properties.get("Background Image", String::class.java)
        levelId         = Integer.parseInt(properties.get("Level ID", String::class.java))
        isLastLevel     = java.lang.Boolean.parseBoolean(properties.get("Is Last Level", String::class.java))

        for (obj in map.layers.get(0).objects) {

            val `object` = obj as EllipseMapObject

            val objectType = `object`.properties.get("type", String::class.java)

            var count = 1

            if (`object`.properties.containsKey("count")) {
                count = Integer.parseInt(`object`.properties.get("count", String::class.java))
            }

            val bounds = `object`.ellipse
            val center = Vector2(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2)

            val left   = bounds.x.toInt()
            val right  = (bounds.x + bounds.width).toInt()
            val top    = bounds.y.toInt()
            val bottom = (bounds.y + bounds.height).toInt()

            if (objectType != null) {
                if (objectType == "FighterZone") {
                    fighters.add(SpawnZone(count, left, right, top, bottom, center))
                }

                if (objectType == "FreighterZone") {
                    freighters.add(SpawnZone(count, left, right, top, bottom, center))
                }

                if (objectType == "Mines") {
                    mines.add(SpawnPoint(center.x.toInt(), center.y.toInt()))
                }
            }
        }
    }

    fun getMines(): Iterable<SpawnPoint> {
        return mines
    }

    fun getFighters(): Iterable<SpawnZone> {
        return fighters
    }

    fun getFreighters(): Iterable<SpawnZone> {
        return freighters
    }
}
