package com.spacefist.components;

import com.spacefist.GameData;
import com.spacefist.components.abst.InputComponent;
import com.spacefist.entities.Entity;

/**
 * For se by entities that do not use input.
 */
public class NullInputComponent implements InputComponent
{
    public void Update(GameData gameData, Entity obj)
    {
    }
}
