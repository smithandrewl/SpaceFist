using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Project2.Components.Abstract
{
    public interface SoundComponent : Component
    {
        void LoadContent(Game game);
    }
}
