using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.Abstract
{
    /// <summary>
    /// The interface implemented by all state machines.
    /// </summary>
    /// <typeparam name="State"></typeparam>
    public interface StateMachine<State>
    {
        /// <summary>
        /// The state active in the machine.
        /// </summary>
        State CurrentState { get; set; }
    }
}
