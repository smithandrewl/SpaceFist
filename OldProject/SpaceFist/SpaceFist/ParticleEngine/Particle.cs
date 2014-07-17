using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.ParticleEngine
{
    public class Particle
    {
        private Texture2D texture;

        private float    scale;
        private float    rotation;
        private float    angularVelocity;
        private Vector2  velocity;
        private DateTime creation;
        private int      ttl;
        private Color    tint;
        private Vector2  originalPos;
        private Vector2  position;


        public Texture2D Texture
        {
            get
            {
                return texture;
            }
        }

        public float Scale
        {
            get
            {
                return scale;
            }
        }

        public float Rotation
        {
            get
            {
                return rotation;
            }
        }

        public float AngularVelocity
        {
            get
            {
                return angularVelocity;
            }
        }

        public Vector2 Velocity
        {
            get
            {
                return velocity;
            }
        }

        public DateTime Creation
        {
            get
            {
                return creation;
            }
        }

        public int Ttl
        {
            get
            {
                return ttl;
            }
        }

        public Color Tint
        {
            get
            {
                return tint;
            }
        }

        public int X
        {
            get
            {
                return (int) position.X;
            }
        }

        public int Y
        {
            get
            {
                return (int)position.Y;
            }
        }

        public Particle(
            Texture2D texture, float scale, float rotation, float angularVelocity,
            Vector2 velocity, int ttl, Color tint, Vector2 originalPos)
        {
            this.originalPos     = originalPos;
            this.position        = originalPos;
            this.texture         = texture;
            this.scale           = scale;
            this.rotation        = rotation;
            this.angularVelocity = angularVelocity;
            this.velocity        = velocity;
            this.creation        = DateTime.Now;
            this.ttl = ttl;
            this.tint = tint;

        }

        public virtual void Update()
        {
            position = new Vector2(position.X + velocity.X, position.Y + velocity.Y);
            rotation = MathHelper.ToRadians(MathHelper.ToDegrees(rotation) + angularVelocity);
        }
    }
}
