using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.AI
{
    public class FuzzyVariable
    {
        public string Name { get; set; }
        public float  Low  { get; set; }
        public float  Med  { get; set; }
        public float  High { get; set; }
        public float Value { get; set; }

        public override string ToString()
        {
            return String.Format("{0}: {1}, {2:P} Low, {3:P} Med, {4:P} High", Name, Value, Low, Med, High);
        }
    }
}
