using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.Abstract
{
    /// <summary>
    /// The interface implemented by all enemy AI states.
    /// </summary>
    public interface EnemyAIState
    {
        void Update();
    }
}
