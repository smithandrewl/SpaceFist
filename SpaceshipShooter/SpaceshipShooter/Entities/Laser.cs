using Microsoft.Xna.Framework;
using Project2.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Project2.Entities
{
    class Laser : GameObject
    {
        private const int Width  = 26;
        private const int Height = 47;
        private const int Speed  = 10;

        private const String LaserImageAsset = @"Images\Sprites\Laser";
        private const String LaserSoundAsset = @"Sound\laser";

        private bool soundPlayed = false;

        public Laser(Game game, Vector2 position) : 
            base(game, 
                 new Rectangle((int)position.X, (int)position.Y, Width, Height), 
                 new Physics(), 
                 new NullInputComponent(), 
                 new Sprite(LaserImageAsset),
                 new Sound(LaserSoundAsset),
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
