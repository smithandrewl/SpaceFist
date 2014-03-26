using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.ShipStates
{
    public class LowHealthState : ShipState
    {
        private Ship Ship {get; set;} 

        public LowHealthState(Ship ship)
        {
            Ship = ship;   
        }
        public void Update()
        {
            Ship.Tint.A = (byte)((Ship.Tint.A + 20) % 255);
            Ship.Tint.G = (byte)(((Ship.Tint.G + 10) % 255));
            Ship.Tint.B = (byte)(((Ship.Tint.B + 10) % 255));

            if (Ship.Health <= 0)
            {
                Ship.CurrentState = new SpawningState(Ship);
            }
        }

        public void EnteringState()
        {
        }

        public void ExitingState()
        {
        }
    }
}
