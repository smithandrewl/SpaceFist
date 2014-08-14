package com.spacefist.components.abst;

import com.spacefist.GameData;
import com.spacefist.entities.Entity;

/**
 * All graphics components implement this interface
 */
public interface GraphicsComponent extends Component {
    void draw(GameData gameData, Entity obj);
}
