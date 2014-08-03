package com.spacefist.managers;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
        String levelDirectory = Gdx.files.getLocalStoragePath() + "/Maps";

        FileHandle handle = Gdx.files.getFileHandle(levelDirectory, Files.FileType.Local);

        int levelCount = handle.list("tmx").length;

        gameData.setLevelCount(levelCount);
    }

    public void LoadLevel(int id)
    {
        TmxMapLoader loader = new TmxMapLoader();
        TiledMap map = loader.load(Gdx.files.getLocalStoragePath() + "/Maps/" + id + ".tmx");
        gameData.setLevel(new Level(map));
    }
}
