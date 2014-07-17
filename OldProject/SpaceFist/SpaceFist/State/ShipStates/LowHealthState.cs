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
        private GameData gameData;

        public LowHealthState(GameData gameData)
        {
            this.gameData = gameData;
        }

        public void Update()
        {
            Ship ship = gameData.Ship;

            // Fade the ship from invisible to visible while also fading between its 
            // normal colors and a red tint
            ship.Tint.A = (byte)((ship.Tint.A + 20) % 255);
            ship.Tint.G = (byte)(((ship.Tint.G + 10) % 255));
            ship.Tint.B = (byte)(((ship.Tint.B + 10) % 255));

            // If the ship has died, switch to the respawning state
            if (ship.Health <= 0)
            {
                ship.CurrentState = new SpawningState(gameData);
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
