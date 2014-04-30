using SpaceFist.AI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist
{
    // Base class for classes using fuzzy logic
    // The Triangle and Trapezoid membership grading methods are based on the article
    // http://www.dma.fi.upm.es/java/fuzzy/fuzzyinf/funpert_en.htm
    public abstract class FuzzyLogicEnabled
    {
        protected static float Grade(float val, float lowerLimit, float lowerSupportlimit)
        {
            if (val < lowerLimit)       
                return 0;
            
            if ((val >= lowerLimit) && (val <= lowerSupportlimit)) 
                return (val - lowerLimit) / (lowerSupportlimit - lowerLimit);

            return 1;
        }

        protected static float ReverseGrade(float val, float upperSupportLimit, float upperLimit)
        {
            if (val > upperLimit)         
                return 0;
            
            if ((val >= upperSupportLimit) && (val <= upperLimit)) 
                return (upperLimit - val) / (upperLimit - upperSupportLimit);
            
            return 1;
        }

        protected static float Triangle(float val, float lowerLimit, float middle, float upperLimit)
        {
            if (val <= lowerLimit) return 0; 
            if (val <= middle)     return (val - lowerLimit) / (middle - lowerLimit);
            if (val < upperLimit)  return (upperLimit - val) / (upperLimit - middle);
            
            return 0;
        }

        protected static float Trapezoid(
            float val, 
            float lowerLimit, 
            float lowerSupportLimit, 
            float upperSupportLimit, 
            float upperLimit
        )
        {
            var outOfBounds   = (val <  lowerLimit)        || (val > upperLimit);
            var inLowRange    = (val >= lowerLimit)        && (val <= lowerSupportLimit);
            var inMiddleRange = (val >= lowerSupportLimit) && (val <= upperSupportLimit);

            if (outOfBounds)   return 0;
            if (inLowRange)    return (val - lowerLimit) / (lowerSupportLimit - lowerLimit);            
            if (inMiddleRange) return 1;            
            
            // inUpperRange
            return (upperLimit - val) / (upperLimit - upperSupportLimit);
        }

        protected static FuzzyVariable grade(float val, float low, float high, FuzzyVariable fuzzyVariable)
        {
                float med = (high - low) / 2.0f;

                fuzzyVariable.Value = val;
                fuzzyVariable.Low   = ReverseGrade(val, low, med);
                fuzzyVariable.Med   = Triangle(val, low, med, high);
                fuzzyVariable.High  = Grade(val, med, high);

                return fuzzyVariable;
        }

        protected static float And(float first, float second)
        {
            return Math.Min(first, second);
        }

        protected static float Or(float first, float second)
        {
            return Math.Max(first, second);
        }

        protected static float Not(float val)
        {
            return 1 - val;
        }

        public abstract void Update();
    }
}
