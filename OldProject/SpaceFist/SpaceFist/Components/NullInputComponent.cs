using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.Components.Abstract;

namespace SpaceFist.Components
{
    /// <summary>
    /// For use by entities that do not use input.
    /// </summary>
    public class NullInputComponent : InputComponent
    {
        public void Update(GameData gameData, Entity obj)
        {
        }
    }
}
