using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Project2.Components.Abstract;

namespace Project2
{
    // Updates The game objects position using its properties. 
    // This will use acceleration and friction at some point in the
    // future but just uses velocity right now
    class Physics : PhysicsComponent
    {
        public Physics() 
        {
        }
        
        public void Update(Game game, GameObject obj, GameTime time)
        {
            obj.X += (int)obj.Velocity.X;
            obj.Y += (int)obj.Velocity.Y;
        }
    }
}
