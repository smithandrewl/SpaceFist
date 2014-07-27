package com.spacefist.components.abst;

import com.spacefist.GameData;
import com.spacefist.entities.Entity;

/// <summary>
/// All graphics components implement this interface
/// </summary>
public interface GraphicsComponent extends Component {
    void Draw(GameData gameData, Entity obj);
}
