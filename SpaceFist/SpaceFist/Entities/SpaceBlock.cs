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
    /// Represents a floating block or crate.
    /// </summary>
    public class SpaceBlock : Entity
    {
        // Dimensions of the block
        private const int Width  = 80;
        private const int Height = 60;

        // The sound made when the ship hits a block
        private Sound thump;

        /// <summary>
        /// Creates a new SpaceBlock instance with a specified position and velocity.
        /// </summary>
        /// <param name="game">The game</param>
        /// <param name="texture">The image for the block</param>
        /// <param name="position">The position of the block in the game world</param>
        /// <param name="velocity">The velocity of the block</param>
        public SpaceBlock(Game game, Texture2D texture, Vector2 position, Vector2 velocity) : 
            base(game, 
                 new Rectangle(
                     (int) position.X, 
                     (int) position.Y, 
                     Width, Height
                 ), 
                 new Physics(), 
                 new NullInputComponent(), 
                 new Sprite(texture),
                 new Sound(game.SoundEffects["Explosion"]),
                 game.ScreenScale)
        {
            thump = new Sound(game.SoundEffects["Explosion"]);

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
