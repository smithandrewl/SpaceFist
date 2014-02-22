using Project2.Components.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Project2.Components
{
    // For use by entities that do not have a sound 
    // The background for example
    class NullSoundComponent : SoundComponent
    {
        public void LoadContent(Game game)
        {
        }

        public void Update(Game game, GameObject obj, Microsoft.Xna.Framework.GameTime time)
        {
        }
    }
}
