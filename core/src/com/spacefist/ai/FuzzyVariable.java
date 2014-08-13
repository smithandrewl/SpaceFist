package com.spacefist.ai;

/**
 *  Represents a fuzzy input variable of three sets
 */
public class FuzzyVariable {
    /**
     * The name of the fuzzy variable.
     */
    private String name;

    /**
     * The degree that the variable is at the low end of the spectrum.
     */
    private float low;

    /**
     * The degree that the variable is in the middle of the spectrum.
     */
    private float med;

    /**
     * The degree that the variable is in the high end of the spectrum.
     */
    private float high;

    /**
     * The crisp input for this fuzzy variable
     */
    private float value;

    /**
     * Converts the fuzzy variable to a single number using a
     * weighted average.
     *
     * @param lowWeight The weight to apply to the low set membership
     * @param medWeight The weight to apply to the medium set membership
     * @param highWeight The weight to apply to the high set membership
     */
    public float Defuzzify(float lowWeight, float medWeight, float highWeight) {
        float adjustedLow  = getLow()  * lowWeight;
        float adjustedMed  = getMed()  * medWeight;
        float adjustedHigh = getHigh() * highWeight;

        float weightedTotal = adjustedLow + adjustedMed + adjustedHigh;

        float total  = getLow() + getMed() + getHigh();

        return weightedTotal / total;
    }

    /**
     * @return The contents of the variable in a format suitable for display</returns>
     */
    @Override
    public String toString() {
        // TODO: convert FuzzyVariable.toString()
        //return String.format("{0}: {1}, {2:P} low, {3:P} med, {4:P} high", name, value, low, med, high);
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getMed() {
        return med;
    }

    public void setMed(float med) {
        this.med = med;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
