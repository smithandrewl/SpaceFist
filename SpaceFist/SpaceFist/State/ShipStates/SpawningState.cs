using Microsoft.Xna.Framework;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.ShipStates
{
    /// <summary>
    /// The SpawningState determines the ships behavior when it is spawning or has died and respawned.
    /// 
    /// The ship starts invisible and then fades to fully visible.  When 2 seconds have passed (to give it time to fade-in),
    /// the state is changed to NormalState.
    /// </summary>
    class SpawningState : ShipState
    {
        // The number of seconds to wait for the ship to load.
        // This gives the ship time to fully fade-in from transparent to opaque.
        private const int SpawnTime = 1;

        private Ship     Ship      { get; set; }
        private DateTime SpawnedAt { get; set; }

        public SpawningState(Ship ship)
        {
            Ship = ship;
        }
        
        public void Update()
        {
            byte increment = 5;

            // If the ship is not fully visible, increase its visibility
            if (Ship.Tint.A < 255)
            {
                Ship.Tint.A += increment;
                Ship.Tint.R += increment;
                Ship.Tint.G += increment;
                Ship.Tint.B += increment;
            }

            // The number of seconds the ship has been in this state
            var elapsed = DateTime.Now.Subtract(SpawnedAt).Seconds;

            // After the ship fades in, switch to the normal statea
            if (elapsed > SpawnTime)
            {
                Ship.CurrentState = new NormalState(Ship);
            }
        }

        public void EnteringState()
        {
            SpawnedAt = DateTime.Now;

            // Set the color to transparent so that it can fade into full visibility
            Ship.Tint = Color.Transparent;
        }

        public void ExitingState()
        {
        }
    }
}
