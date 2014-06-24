using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Framework.ParticleEngine
{
    public class ParticleOptions
    {
        private float speed;
        private int   ttl;
        private int   minRotation;
        private int   maxRotation;
        private int   minAngularVelocity;
        private int   maxAngularVelocity;
        private int   minScale;
        private int   maxScale;

        public float Speed              { get { return speed;              } }
        public int   Ttl                { get { return ttl;                } }
        public int   MinRotation        { get { return minRotation;        } }
        public int   MaxRotation        { get { return maxRotation;        } }
        public int   MinAngularVelocity { get { return minAngularVelocity; } }
        public int   MaxAngularVelocity { get { return maxAngularVelocity; } }
        public int   MinScale           { get { return minScale;           } }
        public int   MaxScale           { get { return maxScale;           } }

        public ParticleOptions(
            float speed,              int ttl,                
            int   minRotation,        int maxRotation, 
            int   minAngularVelocity, int maxAngularVelocity, 
            int   minScale,           int maxScale)
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
