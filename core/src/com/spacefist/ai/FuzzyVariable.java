package com.spacefist.ai;

/// <summary>
/// Represents a fuzzy input variable of three sets
/// </summary>
public class FuzzyVariable {
    /// <summary>
    /// The name of the fuzzy variable.
    /// </summary>
    private String name;

    /// <summary>
    /// The degree that the variable is at the low end of the spectrum.
    /// </summary>
    private float low;

    /// <summary>
    /// The degree that the variable is in the middle of the spectrum.
    /// </summary>
    private float med;

    /// <summary>
    /// The degree that the variable is in the high end of the spectrum.
    /// </summary>
    private float high;

    /// <summary>
    /// The crisp input for this fuzzy variable
    /// </summary>
    private float value;

    /// <summary>
    /// Converts the fuzzy variable to a single number using a
    /// weighted average.
    /// </summary>
    /// <param name="lowWeight">The weight to apply to the low set membership</param>
    /// <param name="medWeight">The weight to apply to the medium set membership</param>
    /// <param name="highWeight">The weight to apply to the high set membership</param>
    /// <returns></returns>
    public float Defuzzify(float lowWeight, float medWeight, float highWeight) {
        return (getLow() * lowWeight + getMed() * medWeight + getHigh() * highWeight) / (getLow() + getMed() + getHigh());
    }

    /// <returns>The contents of the variable in a format suitable for display</returns>
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
