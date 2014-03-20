using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.Abstract
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
