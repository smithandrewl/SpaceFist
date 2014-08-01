package com.spacefist.managers;

import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.entities.EnemyMine;

public class EnemyMineManager extends Manager<EnemyMine> {

    public EnemyMineManager(GameData gameData) {
        super(gameData);
        this.gameData = gameData;
    }

    public void SpawnEnemyMine(int x, int y) {
        Add(new EnemyMine(gameData, new Vector2(x, y)));
    }
}
