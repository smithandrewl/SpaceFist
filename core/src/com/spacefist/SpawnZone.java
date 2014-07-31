package com.spacefist;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents a region of a level where enemies will may spawn.
 */
public class SpawnZone {
    private int count;
    private int left;
    private int right;
    private int top;
    private int bottom;
    private Vector2 center;

    public SpawnZone(int count, int left, int right, int top, int bottom, Vector2 center) {
        this.count  = count;
        this.left   = left;
        this.right  = right;
        this.top    = top;
        this.bottom = bottom;
        this.center = center;
    }

    public int getCount() {
        return count;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public Vector2 getCenter() {
        return center;
    }
}