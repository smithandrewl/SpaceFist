using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.AI
{
    /// <summary>
    /// Represents a fuzzy input variable of three sets
    /// </summary>
    public class FuzzyVariable
    {
        /// <summary>
        /// The name of the fuzzy variable.
        /// </summary>
        public string Name  { get; set; }

        /// <summary>
        /// The degree that the variable is at the low end of the spectrum.
        /// </summary>
        public float  Low   { get; set; }

        /// <summary>
        /// The degree that the variable is in the middle of the spectrum.
        /// </summary>
        public float  Med   { get; set; }

        /// <summary>
        /// The degree that the variable is in the high end of the spectrum.
        /// </summary>
        public float  High  { get; set; }

        /// <summary>
        /// The crisp input for this fuzzy variable
        /// </summary>
        public float  Value { get; set; }

        /// <summary>
        /// Converts the fuzzy variable to a single number using a 
        /// weighted average.
        /// </summary>
        /// <param name="lowWeight">The weight to apply to the low set membership</param>
        /// <param name="medWeight">The weight to apply to the medium set membership</param>
        /// <param name="highWeight">The weight to apply to the high set membership</param>
        /// <returns></returns>
        public float Defuzzify(float lowWeight, float medWeight, float highWeight)
        {
            return (Low * lowWeight + Med * medWeight + High * highWeight) / (Low+Med+High);
        }

        /// <returns>The contents of the variable in a format suitable for display</returns>
        public override string ToString()
        {
            return String.Format("{0}: {1}, {2:P} Low, {3:P} Med, {4:P} High", Name, Value, Low, Med, High);
        }
    }
}
