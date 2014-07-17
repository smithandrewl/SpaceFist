using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Graphics;
using SpaceFist.Components;
using SpaceFist.Components.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Entities
{
    /// <summary>
    /// Represents a pickup item which runs a function when it collides with the players ship.
    /// </summary>
    public class Pickup : Entity
    {
        private Sound         pickupSound;
        private PickupHandler pickupHandler;

        // The function called on pickup can modify the ship via the passed in reference.
        public delegate bool PickupHandler(Ship ship);
 
        /// <summary>
        /// Creates a new Pickup instance which runs a function 
        /// when it is picked up by the player.
        /// </summary>
        /// <param name="gameData">Common game data</param>
        /// <param name="texture">The image of the pickup</param>
        /// <param name="sound">The sound to play when picked up</param>
        /// <param name="position">The initial location of the pickup</param>
        /// <param name="velocity">The initial velocity of the pickup</param>
        /// <param name="pickupHandler">The function to run on pickup</param>
        public Pickup(GameData      gameData, 
                      Texture2D     texture, 
                      SoundEffect   sound, 
                      Vector2       position, 
                      Vector2       velocity, 
                      PickupHandler pickupHandler) :
            base(
                gameData, 
                new Rectangle(
                    (int)position.X, 
                    (int) position.Y, 
                    texture.Width, 
                    texture.Height
                ),
                new Physics(),
                new NullInputComponent(),
                new Sprite(texture),
                new Sound(sound),
                gameData.ScreenScale
            )

        {
            pickupSound        = new Sound(sound);
            this.pickupHandler = pickupHandler;

            Velocity = velocity;
        }

        // PickedUp calls pickupHandler when the pickup has been "picked up"
        // The pickup handler returns true if the pickup should be removed
        public bool PickedUp(Ship ship)
        {
            var pickedUp = pickupHandler(ship);
            
            if (pickedUp)
            {
                pickupSound.play();
            }
            
            return pickedUp;
        }
    }
}
