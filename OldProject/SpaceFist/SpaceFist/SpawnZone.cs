using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist
{
    /// <summary>
    /// Represents a region of a level where enemies will may spawn.
    /// </summary>
    public class SpawnZone
    {
        private int   count;
        private int   left;
        private int   right;
        private int   top;
        private int   bottom;
        private Point center;

        public int   Count  { get { return count;  } }
        public int   Left   { get { return left;   } }
        public int   Right  { get { return right;  } }
        public int   Top    { get { return top;    } }
        public int   Bottom { get { return bottom; } }
        public Point Center { get { return center; } }

        public SpawnZone(int count, int left, int right, int top, int bottom, Point center)
        {
            this.count  = count;
            this.left   = left;
            this.right  = right;
            this.top    = top;
            this.bottom = bottom;
            this.center = center;
        }
    }
}
