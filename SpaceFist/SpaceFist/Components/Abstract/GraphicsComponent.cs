using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;

namespace SpaceFist.Components.Abstract
{
    /// <summary>
    /// All graphics components implement this interface
    /// </summary>
    public interface GraphicsComponent : Component
    {
        void Draw(GameData gameData, Entity obj);
    }
}
