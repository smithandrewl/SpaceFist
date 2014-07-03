using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;

namespace SpaceFist.Components.Abstract
{
    /// <summary>
    /// The base interface of any more specific component interface.
    /// 
    /// All components directly or indirectly implement this interface.
    /// </summary>
    public interface Component
    {
        void Update(GameData gameData, Entity obj);
    }
}
