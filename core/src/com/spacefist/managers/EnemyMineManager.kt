package com.spacefist.managers

import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.entities.EnemyMine

class EnemyMineManager(gameData: GameData) : Manager<EnemyMine>(gameData) {

    init {
        this.gameData = gameData
    }

    fun spawnEnemyMine(x: Int, y: Int) {
        add(EnemyMine(gameData, Vector2(x.toFloat(), y.toFloat())))
    }
}
