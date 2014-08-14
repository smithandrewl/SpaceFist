package com.spacefist;

/**
 * Holds information about the current round.
 */
public class RoundData {
    private int lives;
    private int score;
    private int enemiesShot;
    private int blocksShot;
    private int blocksBumped;
    private int shotsFired;

    /**
     * @return The players accuracy
     */
    public float getAcc() {
        return (enemiesShot + blocksShot) / (float) shotsFired;
    }

    /**
     * Resets all of the values to their default values.
     */
    public void reset() {
        lives        = 0;
        score        = 0;
        blocksBumped = 0;
        blocksShot   = 0;
        enemiesShot  = 0;
        shotsFired   = 0;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getEnemiesShot() {
        return enemiesShot;
    }

    public void setEnemiesShot(int enemiesShot) {
        this.enemiesShot = enemiesShot;
    }

    public int getBlocksShot() {
        return blocksShot;
    }

    public void setBlocksShot(int blocksShot) {
        this.blocksShot = blocksShot;
    }

    public int getBlocksBumped() {
        return blocksBumped;
    }

    public void setBlocksBumped(int blocksBumped) {
        this.blocksBumped = blocksBumped;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public void setShotsFired(int shotsFired) {
        this.shotsFired = shotsFired;
    }
}