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
        private const int Width  = 26;
        private const int Height = 47;
        private const int Speed  = 10;

        private bool soundPlayed = false;

        public Projectile(Game game, Texture2D texture, Vector2 position) : 
            base(game, 
                 new Rectangle((int)position.X, (int)position.Y, Width, Height), 
                 new Physics(), 
                 new NullInputComponent(), 
                 new Sprite(texture),
                 new Sound(game.LaserSound),
                 game.ScreenScale)
        {
            Velocity = new Vector2(0, -Speed);
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
