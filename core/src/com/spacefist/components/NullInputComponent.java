package com.spacefist.components;

import com.spacefist.GameData;
import com.spacefist.components.abst.InputComponent;
import com.spacefist.entities.Entity;

/**
 * For use by entities that do not use input.
 */
public class NullInputComponent implements InputComponent {
    public void update(GameData gameData, Entity obj) { }
}
