using Microsoft.Xna.Framework;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.ShipStates
{
    /// <summary>
    /// NormalState holds the behavior for the ship after it has loaded,
    /// but before it has low health.
    /// 
    /// In this state, the ship will wait until it has low health before switching
    /// the ship to the low-health state.
    /// </summary>
    class NormalState : ShipState
    {
        // The percentage of full health at which the ship is considered to have low-health.
        private const float LowHealthThreshold = .40f;
        private GameData gameData;

        public NormalState(GameData gameData)
        {
            this.gameData = gameData;
        }

        public void Update()
        {
            Ship ship = gameData.Ship;

            if (ship.Health <= LowHealthThreshold)
            {
                // Change the ships state to LowHealthState
                ship.CurrentState = new LowHealthState(gameData);
            } 
        }

        public void EnteringState()
        {
            gameData.Ship.Tint = Color.White;    
        }

        public void ExitingState()
        {
        }
    }
}
