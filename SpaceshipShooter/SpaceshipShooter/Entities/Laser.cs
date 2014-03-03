using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SpaceshipShooter.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceshipShooter.Entities
{
    class Laser : GameObject
    {
        private const int Width  = 26;
        private const int Height = 47;
        private const int Speed  = 10;

        private bool soundPlayed = false;

        public Laser(Game game, Texture2D texture, Vector2 position) : 
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

        public override void Update(GameTime gameTime) {
            var sound = (Sound) this.sound;
            if (!soundPlayed)
            {
                sound.play();
                soundPlayed = true;
            }

            base.Update(gameTime);
        }
    }
}
