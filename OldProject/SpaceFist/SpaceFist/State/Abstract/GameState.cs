using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.Abstract
{
    /// <summary>
    /// The interface implemented by each game screen / state.
    /// </summary>
    public interface GameState : State
    {
        void LoadContent();
        void Draw(GameTime time);
    }
}
