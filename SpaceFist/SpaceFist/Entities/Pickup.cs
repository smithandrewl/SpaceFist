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
    public class Pickup : Entity
    {
        private Sound         pickupSound;
        private PickupHandler pickupHandler;

        public delegate bool PickupHandler(Ship ship);
 
        public Pickup(Game          game, 
                      Texture2D     texture, 
                      SoundEffect   sound, 
                      Vector2       position, 
                      Vector2       velocity, 
                      PickupHandler pickupHandler) :
            base(
                game, 
                new Rectangle((int)position.X, (int) position.Y, texture.Width, texture.Height),
                new Physics(),
                new NullInputComponent(),
                new Sprite(texture),
                new Sound(sound),
                game.ScreenScale
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
            return pickupHandler(ship);
        }
    }
}
