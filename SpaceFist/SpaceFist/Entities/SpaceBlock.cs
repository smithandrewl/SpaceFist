using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using SpaceFist.Components;
using Microsoft.Xna.Framework.Graphics;

namespace SpaceFist.Entities
{
    /// <summary>
    /// 
    /// </summary>
    public class SpaceBlock : Entity
    {
        // Dimensions of the block
        private const int Width  = 80;
        private const int Height = 60;

        // The sound made when the ship hits a block
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

        // Called when the block has been shot
        public void Destroy()
        {
            var sound = (Sound) this.sound;

            sound.play();
            Alive = false;
        }

        // Called when the block has been bumped by the ship
        public void Thump()
        {
            thump.play();
            Alive = false;
        }
    }
}
