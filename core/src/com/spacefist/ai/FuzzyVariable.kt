package com.spacefist.ai

/**
 * Represents a fuzzy input variable of three sets.
 */
class FuzzyVariable {
    /**
     * The name of the fuzzy variable.
     */
    var name: String? = null

    /**
     * The degree that the variable is at the low end of the spectrum.
     */
    var low: Float = 0.toFloat()

    /**
     * The degree that the variable is in the middle of the spectrum.
     */
    var med: Float = 0.toFloat()

    /**
     * The degree that the variable is in the high end of the spectrum.
     */
    var high: Float = 0.toFloat()

    /**
     * The crisp input for this fuzzy variable
     */
    var value: Float = 0.toFloat()

    /**
     * Converts the fuzzy variable to a single number using a
     * weighted average.
     *
     * @param lowWeight The weight to apply to the low set membership
     * @param medWeight The weight to apply to the medium set membership
     * @param highWeight The weight to apply to the high set membership
     */
    fun defuzzify(lowWeight: Float, medWeight: Float, highWeight: Float): Float {
        val adjustedLow  = low  * lowWeight
        val adjustedMed  = med  * medWeight
        val adjustedHigh = high * highWeight

        val weightedTotal = adjustedLow + adjustedMed + adjustedHigh
        val total         = low         + med         + high

        return weightedTotal / total
    }

    /**
     * @return The contents of the variable in a format suitable for display
     */
    override fun toString(): String {
        return ""
    }
}
