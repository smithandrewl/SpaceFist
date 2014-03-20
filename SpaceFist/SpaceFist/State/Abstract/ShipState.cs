using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.Abstract
{
    public interface ShipState
    {
        void Update();
        void EnteringState();
        void ExitingState();
    }
}
