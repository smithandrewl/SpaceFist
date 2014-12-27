package com.spacefist.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.spacefist.GameData;
import com.spacefist.Level;

public class LevelManager
{
    private GameData gameData;

    public LevelManager(GameData gameData)
    {
        this.gameData = gameData;
    }

    public void Init()
    {
        int levelCount = Gdx.files.absolute("/maps").list().length;

        gameData.setLevelCount(levelCount);
    }

    public void LoadLevel(int id)
    {
        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load(Gdx.files.absolute("maps/" + id + ".tmx").path());
        gameData.setLevel(new Level(map));
    }
}
