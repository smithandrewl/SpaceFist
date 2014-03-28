using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.ShipStates
{
    /// <summary>
    /// LowHealthState determines the appearance of the ship when it has low health
    /// </summary>
    public class LowHealthState : ShipState
    {
        private Ship Ship {get; set;} 

        public LowHealthState(Ship ship)
        {
            Ship = ship;   
        }
        public void Update()
        {
            // Fade the ship from invisible to visible while also fading between its 
            // normal colors and a red tint
            Ship.Tint.A = (byte)((Ship.Tint.A + 20) % 255);
            Ship.Tint.G = (byte)(((Ship.Tint.G + 10) % 255));
            Ship.Tint.B = (byte)(((Ship.Tint.B + 10) % 255));

            // If the ship has died, switch to the respawning state
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
