package com.spacefist.managers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.spacefist.GameData
import com.spacefist.Level

class LevelManager(private val gameData: GameData) {

    fun init() {
        val levelCount = Gdx.files.absolute("/maps").list().size

        gameData.levelCount = levelCount
    }

    fun loadLevel(id: Int) {
        val loader = TmxMapLoader()
        val map = loader.load(Gdx.files.absolute("maps/$id.tmx").path())
        gameData.level = Level(map)
    }
}
