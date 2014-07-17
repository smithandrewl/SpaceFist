using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SpaceFist;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.ParticleEngine
{
    /*
 *         Examples:
 *         
 *          plasmaBallEmitter = new ParticleEmitter(
                part, 
                1000,
                10, 
                new Vector2(200, 100), 
                spriteBatch,
                new ParticleOptions(1.25f, 450, -360, 360, 0, 3, 1, 6)
            );

            explosionEmitter = new ParticleEmitter(
                explosion,
                500,
                25,
                new Vector2(400, 100),
                spriteBatch,
                new ParticleOptions(1.25f, 500, 0, 50, 0, 0, 1, 7)
            );
 */
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
        private Vector2        center;
        private SpriteBatch    spriteBatch;
        private bool           alive;
        private DateTime       lastEmission;
        private Texture2D      texture;
        private Random         rand;
        private ParticleOptions particleOptions;
        private GameData gameData;

        public ParticleEmitter(GameData gameData, Texture2D texture, int maxParticles, int freq, Vector2 center, SpriteBatch spriteBatch,
            ParticleOptions particleOptions)
        {
            this.gameData = gameData;

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

        public void Update()
        {
            if (alive)
            {
                // add more particles if needed
                if ((particles.Count < maxParticles) && ((DateTime.Now - lastEmission).TotalMilliseconds > freq))
                {
                    for (int i = 0; i < 3; i++)
                    {
                        int degrees = rand.Next(particleOptions.MinRotation, particleOptions.MaxRotation);


                        float speed = particleOptions.Speed;
                        var velocity = new Vector2((float)(speed * Math.Cos(degrees)), (float)(speed * Math.Sin(degrees)));
                        

                        var rotation = MathHelper.ToRadians(rand.Next(particleOptions.MinRotation, particleOptions.MaxRotation));
                        

                        var angularVelocity = rand.Next(particleOptions.MinAngularVelocity, particleOptions.MaxAngularVelocity);
                        
                        var scale = rand.Next(((int) particleOptions.MinScale), (int) particleOptions.MaxScale);

                        particles.Add(new Particle(texture, scale, rotation, angularVelocity, velocity, particleOptions.Ttl, Color.White, Position));
                    }

                    lastEmission = DateTime.Now;
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

                    var drawAt = new Vector2(particle.X, particle.Y) - gameData.Camera;

                    Color color = new Color(particle.Tint.R, particle.Tint.G, particle.Tint.B, transparency);

                    spriteBatch.Draw(
                        particle.Texture,
                        drawAt,
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
