using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;

namespace SpaceFist.Components.Abstract
{
    public interface GraphicsComponent : Component
    {
        void Draw(Game game, GameObject obj);
    }
}
