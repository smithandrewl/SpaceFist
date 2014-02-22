using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Project2.State.Abstract
{
    public interface GameState
    {
        void LoadContent();
        void EnteringState();
        void Update(GameTime time);
        void Draw(GameTime time);
        void ExitingState();
    }
}
