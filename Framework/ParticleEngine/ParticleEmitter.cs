using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Framework.ParticleEngine
{
    public class ParticleEmitter
    {
        public Vector2 Position {
            get
            {
                return center;
            }
            set
            {
                center = value;
            } 
        
        }

        private List<Particle> particles;
        private DateTime       creation;
        private int            maxParticles;
        private int            freq;
        private int            ttl;
        private Vector2        center;
        private SpriteBatch    spriteBatch;
        private bool           alive;
        private DateTime       lastEmission;
        private Texture2D      texture;
        private Random         rand;
        private ParticleOptions particleOptions;

        public ParticleEmitter(Texture2D texture, int maxParticles, int freq, Vector2 center, SpriteBatch spriteBatch,
            ParticleOptions particleOptions)
        {
            particles            = new List<Particle>();
            this.center          = center;
            this.spriteBatch     = spriteBatch;
            alive                = true;
            this.creation        = DateTime.Now;
            lastEmission         = DateTime.Now;
            this.maxParticles    = maxParticles;
            this.freq            = freq;
            this.texture         = texture;
            rand                 = new Random();
            this.particleOptions = particleOptions;
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

        public void Update()
        {
            if (alive)
            {
                // add more particles if needed
                if ((DateTime.Now - lastEmission).TotalMilliseconds > freq)
                {
                    for (int i = 0; i < 3; i++)
                    {
                        // 0, 180
                        int degrees = rand.Next(particleOptions.MinRotation, particleOptions.MaxRotation);

                        // 1.25
                        float speed = particleOptions.Speed;
                        var velocity = new Vector2((float)(speed * Math.Cos(degrees)), (float)(speed * Math.Sin(degrees)));
                        
                        // -360, 360
                        var rotation = MathHelper.ToRadians(rand.Next(particleOptions.MinRotation, particleOptions.MaxRotation));
                        
                        // 0, 3
                        var angularVelocity = rand.Next(particleOptions.MinAngularVelocity, particleOptions.MaxAngularVelocity);
                        // 1,  6
                        var scale = rand.Next(particleOptions.MinScale, particleOptions.MaxScale);

                        particles.Add(new Particle(texture, scale, rotation, angularVelocity, velocity, particleOptions.Ttl, Color.Yellow, Position));
                    }
                }

                // update all particles
                particles.ForEach(particle => particle.Update());

                var particlesToRemove = new List<Particle>();

                foreach (var particle in particles)
                {
                    if((DateTime.Now - particle.Creation).TotalMilliseconds > particle.Ttl)
                    {
                        particlesToRemove.Add(particle);
                    }
                }
                // remove expired particles
                foreach (var particle in particlesToRemove)
                {
                    particles.Remove(particle);
                }
            }
        }

        public void Draw()
        {
            if (alive)
            {
                foreach (var particle in particles)
                {
                    float complete = (float) ((DateTime.Now - particle.Creation).TotalMilliseconds / particle.Ttl);

                    // draw each particle (particles fade as they reach ttl
                    float transparency = complete;

                    Color color = new Color(particle.Tint.R, particle.Tint.G, particle.Tint.B, transparency);

                    spriteBatch.Draw(
                        particle.Texture,
                        new Vector2(particle.X, particle.Y),
                        null,
                        color,
                        particle.Rotation, 
                        new Vector2(particle.Texture.Width / 2, particle.Texture.Height / 2), 
                        particle.Scale,
                        SpriteEffects.None, 0);
                }
            }
        }
    }
}
