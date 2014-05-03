using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using SpaceFist.Components.Abstract;

namespace SpaceFist
{
    /// <summary>
    /// Updates The game objects position using its properties.
    /// </summary>
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
