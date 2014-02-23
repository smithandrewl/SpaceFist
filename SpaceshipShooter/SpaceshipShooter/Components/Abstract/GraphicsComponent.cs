using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;

namespace SpaceshipShooter.Components.Abstract
{
    public interface GraphicsComponent : Component
    {
        void Draw(Game game, GameObject obj, GameTime time);
    }
}
