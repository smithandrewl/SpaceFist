package com.spacefist.ai.abst;

import com.spacefist.entities.Projectile;

/**
 * This interface must be implemented by any weapon behavior.
 */
public interface ProjectileBehavior {
    void Update(Projectile projectile);
}
