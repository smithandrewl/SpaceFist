using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceshipShooter.Components.Abstract;

namespace SpaceshipShooter.Components
{
    // For use by entities that do not use input
    public class NullInputComponent : InputComponent
    {
        public void Update(Game game, GameObject obj, Microsoft.Xna.Framework.GameTime time)
        {
        }
    }
}
