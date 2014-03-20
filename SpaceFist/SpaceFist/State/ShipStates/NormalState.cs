using Microsoft.Xna.Framework;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.ShipStates
{

    class NormalState : ShipState
    {
        private const float LowHealthThreshold = .20f;

        private Ship ship { get; set; }

        public NormalState(Ship ship)
        {
            this.ship = ship;
        }

        public void Update()
        {
            if (ship.Health < LowHealthThreshold)
            {
                ship.setState(new LowHealthState(ship));
            } 
        }

        public void EnteringState()
        {
            ship.Tint = Color.White;    
        }

        public void ExitingState()
        {
        }
    }
}
