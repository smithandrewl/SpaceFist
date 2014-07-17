package com.spacefist.components;

import com.spacefist.GameData;
import com.spacefist.components.abst.SoundComponent;
import com.spacefist.entities.Entity;

/**
 * For use by entities that do not have a sound
 * The background for example
 */
public class NullSoundComponent implements SoundComponent
{
    public void LoadContent(GameData gameData)
    {
    }

    public void Update(GameData gameData, Entity obj)
    {
    }
}
