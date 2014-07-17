package com.spacefist;

/**
 * Represents a spawn point in a level.
 */
public class SpawnPoint
{
    private int x;
    private int y;

    public SpawnPoint(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}