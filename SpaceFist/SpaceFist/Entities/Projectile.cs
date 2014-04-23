using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SpaceFist.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Entities
{
    public class Projectile : Entity
    {
        private bool soundPlayed = false;

        public  bool EnemyProjectile { get; set; }

        
        public Projectile(Game game, Texture2D texture, Vector2 position, Vector2 unitVector, int speed, bool enemyProjectile = false) : 
            base(game, 
                 new Rectangle((int)position.X, (int)position.Y, texture.Width, texture.Height), 
                 new Physics(), 
                 new NullInputComponent(), 
                 new Sprite(texture),
                 new Sound(game.LaserSound),
                 game.ScreenScale)
        {

            Velocity        = unitVector * speed;
            EnemyProjectile = enemyProjectile;
            
        }

        // Plays a firing noise on the first update
        public override void Update() {
            var sound = (Sound) this.sound;
            
            if (!soundPlayed)
            {
                sound.play();
                soundPlayed = true;
            }

            base.Update();
        }
    }
}
