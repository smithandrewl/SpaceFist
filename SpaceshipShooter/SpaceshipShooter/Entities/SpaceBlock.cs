using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using SpaceshipShooter.Components;
using Microsoft.Xna.Framework.Graphics;

namespace SpaceshipShooter.Entities
{
    public class SpaceBlock : GameObject
    {
        private const int Width  = 80;
        private const int Height = 60;

        private Sound thump;

        public SpaceBlock(Game game, Texture2D texture, Vector2 position, Vector2 velocity) : 
            base(game, 
                 new Rectangle((int) position.X, (int) position.Y, Width, Height), 
                 new Physics(), 
                 new NullInputComponent(), 
                 new Sprite(texture),
                 new Sound(game.ExplosionSound),
                 game.ScreenScale)
        {
            thump = new Sound(game.ExplosionSound);

            Velocity = velocity;
        }

        public void Destroy()
        {
            var sound = (Sound) this.sound;

            sound.play();
            Alive = false;
        }

        public void Thump()
        {
            thump.play();
            Alive = false;
        }
    }
}
