using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using SpaceFist.Components.Abstract;

namespace SpaceFist
{
    // Updates The game objects position using its properties. 
    // This will use acceleration and friction at some point in the
    // future but just uses velocity right now
    class Physics : PhysicsComponent
    {
        public Physics() 
        {
        }
        
        public void Update(Game game, Entity obj)
        {
            obj.X += (int)obj.Velocity.X;
            obj.Y += (int)obj.Velocity.Y;
        }
    }
}
