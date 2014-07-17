using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.ParticleEngine
{
    public class ParticleOptions
    {
        private float speed;
        private int     ttl;
        private int     minRotation;
        private int     maxRotation;
        private int     minAngularVelocity;
        private int     maxAngularVelocity;
        private float   minScale;
        private float   maxScale;

        public float Speed              { get { return speed;              } }
        public int   Ttl                { get { return ttl;                } }
        public int   MinRotation        { get { return minRotation;        } }
        public int   MaxRotation        { get { return maxRotation;        } }
        public int   MinAngularVelocity { get { return minAngularVelocity; } }
        public int   MaxAngularVelocity { get { return maxAngularVelocity; } }
        public float MinScale           { get { return minScale;           } }
        public float MaxScale           { get { return maxScale;           } }

        public ParticleOptions(
            float speed,              int   ttl,                
            int   minRotation,        int   maxRotation, 
            int   minAngularVelocity, int   maxAngularVelocity, 
            float minScale,           float maxScale)
        {
            this.speed              = speed;
            this.ttl                = ttl;
            this.minRotation        = minRotation;
            this.maxRotation        = maxRotation;
            this.minAngularVelocity = minAngularVelocity;
            this.maxAngularVelocity = maxAngularVelocity;
            this.minScale           = minScale;
            this.maxScale           = maxScale;
        }
    }
}
