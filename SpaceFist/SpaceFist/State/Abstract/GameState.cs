using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.Abstract
{
    public interface GameState : State
    {
        void LoadContent();
        void Draw(GameTime time);
    }
}
