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

        private GameData gameData;
        private DateTime SpawnedAt { get; set; }

        public SpawningState(GameData gameData)
        {
            this.gameData = gameData;
        }
        
        public void Update()
        {
            Ship ship = gameData.Ship;

            byte increment = 5;

            // If the ship is not fully visible, increase its visibility
            if (ship.Tint.A < 255)
            {
                ship.Tint.A += increment;
                ship.Tint.R += increment;
                ship.Tint.G += increment;
                ship.Tint.B += increment;
            }

            // The number of seconds the ship has been in this state
            var elapsed = DateTime.Now.Subtract(SpawnedAt).Seconds;

            // After the ship fades in, switch to the normal statea
            if (elapsed > SpawnTime)
            {
                ship.CurrentState = new NormalState(gameData);
            }
        }

        public void EnteringState()
        {
            SpawnedAt = DateTime.Now;

            // Set the color to transparent so that it can fade into full visibility
            gameData.Ship.Tint = Color.Transparent;
        }

        public void ExitingState()
        {
        }
    }
}
