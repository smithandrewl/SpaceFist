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
        protected float Triangle(float x, float a, float b, float m)
        {
            if (x <= a) return 0;
            if (x <= m) return (x - a) / (m - a);
            if (x < b)  return (b - x) / (b - m);

            return 0;
        }

        protected float Trapezoid(float x, float a, float b, float c, float d)
        {
            if      ((x < a) || (x > d))   return 0;
            else if ((x >= a) && (x <= b)) return (x - a) / (b - a);
            else if ((x >= b) && (x <= c)) return 1;
            else                           return (d - x) / (d - c);
        }

        protected float And(float a, float b)
        {
            return Math.Min(a, b);
        }

        protected float Or(float a, float b)
        {
            return Math.Max(a, b);
        }

        protected float Not(float a)
        {
            return 1 - a;
        }

        public abstract void Update();
    }
}
