using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.Abstract
{
    /// <summary>
    /// Every state machine state implements State
    /// </summary>
    public interface State
    {
        void Update();

        /// <summary>
        /// Called when this state becomes the active state in 
        /// the state machine.
        /// </summary>
        void EnteringState();

        /// <summary>
        /// Called before another state becomes the active state.
        /// </summary>
        void ExitingState();
    }
}
