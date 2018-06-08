package com.spacefist

/**
 * Holds information about the current round.
 */
class RoundData {
    var lives:        Int = 0
    var score:        Int = 0
    var enemiesShot:  Int = 0
    var blocksShot:   Int = 0
    var blocksBumped: Int = 0
    var shotsFired:   Int = 0

    /**
     * @return The players accuracy
     */
    val acc: Float
        get() = (enemiesShot + blocksShot) / shotsFired.toFloat()

    /**
     * Resets all of the values to their default values.
     */
    fun reset() {
        lives        = 0
        score        = 0
        blocksBumped = 0
        blocksShot   = 0
        enemiesShot  = 0
        shotsFired   = 0
    }

    fun shotFired() {
        shotsFired++
    }
}