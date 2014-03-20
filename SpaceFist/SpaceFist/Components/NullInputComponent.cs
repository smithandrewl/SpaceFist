using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.Components.Abstract;

namespace SpaceFist.Components
{
    // For use by entities that do not use input
    public class NullInputComponent : InputComponent
    {
        public void Update(Game game, GameObject obj, Microsoft.Xna.Framework.GameTime time)
        {
        }
    }
}
