package com.spacefist.ai.abst;

import com.spacefist.ai.FuzzyVariable;

/**
 * Base class for classes using fuzzy logic.
 *
 *
 * The triangle and trapezoid membership grading methods are based on the article
 * http://www.dma.fi.upm.es/java/fuzzy/fuzzyinf/funpert_en.htm
 */
public abstract class FuzzyLogicEnabled {
    protected static float Grade(float val, float lowerLimit, float lowerSupportlimit) {
        if (val < lowerLimit)
            return 0;

        if ((val >= lowerLimit) && (val <= lowerSupportlimit))
            return (val - lowerLimit) / (lowerSupportlimit - lowerLimit);

        return 1;
    }

    protected static float reverseGrade(float val, float upperSupportLimit, float upperLimit) {
        if (val > upperLimit)
            return 0;

        if ((val >= upperSupportLimit) && (val <= upperLimit))
            return (upperLimit - val) / (upperLimit - upperSupportLimit);

        return 1;
    }

    protected static float triangle(float val, float lowerLimit, float middle, float upperLimit) {
        if (val <= lowerLimit) return 0;
        if (val <= middle) return (val - lowerLimit) / (middle - lowerLimit);
        if (val < upperLimit) return (upperLimit - val) / (upperLimit - middle);

        return 0;
    }

    protected static float trapezoid(
        float val,
        float lowerLimit,
        float lowerSupportLimit,
        float upperSupportLimit,
        float upperLimit
    ) {
        boolean outOfBounds = (val < lowerLimit) || (val > upperLimit);
        boolean inLowRange = (val >= lowerLimit) && (val <= lowerSupportLimit);
        boolean inMiddleRange = (val >= lowerSupportLimit) && (val <= upperSupportLimit);

        if (outOfBounds) return 0;
        if (inLowRange) return (val - lowerLimit) / (lowerSupportLimit - lowerLimit);
        if (inMiddleRange) return 1;

        // inUpperRange
        return (upperLimit - val) / (upperLimit - upperSupportLimit);
    }

    /**
     * Given a high and a low value, this method applies the grading functions
     * necessary to populate a fuzzy variable consisting of low, medium and high sets.
     *
     * @param val The crisp input
     * @param low The highest value to be considered low
     * @param high The lowest high value to be considered high
     * @param fuzzyVariable The fuzzy variable to populate
     * @return The populated fuzzy variable
     */
     protected static FuzzyVariable grade(float val, float low, float high, FuzzyVariable fuzzyVariable) {
        float med = (high - low) / 2.0f;

        fuzzyVariable.setValue(val);
        fuzzyVariable.setLow(reverseGrade(val, low, med));
        fuzzyVariable.setMed(triangle(val, low, med, high));
        fuzzyVariable.setHigh(Grade(val, med, high));

        return fuzzyVariable;
    }

    /**
     * Performs a fuzzy "and" operation.
     *
     * @param first The first operand
     * @param second The second operand
     * @return The result of the and operation
     */
    protected static float and(float first, float second) {
        return first * second;
    }

    /**
     * Performs a fuzzy "probabilistic-or" operation
     *
     * @param first The first operand
     * @param second The second operand
     * @return The result of the or operation
     */
    protected static float or(float first, float second) {
        return first + second - (first * second);
    }

    /**
     * Performs a fuzzy negation operation.
     *
     * @param val The value to negate
     * @return The negated value
     */
    protected static float not(float val) {
        return 1 - val;
    }

    public abstract void update();
}
