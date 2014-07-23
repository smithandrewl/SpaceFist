package com.spacefist.components.abst;

import com.spacefist.GameData;
import com.spacefist.entities.Entity;

    /**
     * The base interface of any more specific component interface.
     *
     * All components directly or indirectly implement this interface.
     */
    public interface Component
    {
        void update(GameData gameData, Entity obj);
    }
