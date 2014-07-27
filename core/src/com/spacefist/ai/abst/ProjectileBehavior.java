package com.spacefist.ai.abst;

import com.spacefist.entities.Projectile;

/// <summary>
/// This interface must be implemented by any weapon behavior.
/// </summary>
public interface ProjectileBehavior {
    void Update(Projectile projectile);
}
