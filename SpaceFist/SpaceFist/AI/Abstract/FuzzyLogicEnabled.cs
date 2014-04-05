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
        protected float Grade(float val, float lowerSupportlimit, float lowerLimit)
        {
            if (val < lowerLimit)       return 0;
            if (val <lowerSupportlimit) return (val - lowerLimit) /(lowerSupportlimit - lowerLimit);

            return 1;
        }

        protected float ReverseGrade(float val, float upperSupportLimit, float upperLimit)
        {
            if (val > upperLimit)         return 0;
            if (val >= upperSupportLimit) return (upperLimit - val) / (upperLimit - upperSupportLimit);
            
            return 1;
        }

        protected float Triangle(float val, float lowerLimit, float upperLimit, float middle)
        {
            if (val <= lowerLimit) return 0; 
            if (val <= middle)     return (val - lowerLimit) / (middle - lowerLimit);
            if (val < upperLimit)  return (upperLimit - val) / (upperLimit - middle);
            
            return 0;
        }

        protected float Trapezoid(
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

        protected float And(float first, float second)
        {
            return Math.Min(first, second);
        }

        protected float Or(float first, float second)
        {
            return Math.Max(first, second);
        }

        protected float Not(float val)
        {
            return 1 - val;
        }

        public abstract void Update();
    }
}
