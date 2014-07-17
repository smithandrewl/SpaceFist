using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist
{
    /// <summary>
    /// Represents a spawn point in a level.
    /// </summary>
    public class SpawnPoint
    {
        private int x;
        private int y;

        public int X { get { return x; } }
        public int Y { get { return y; } }

        public SpawnPoint(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
}
