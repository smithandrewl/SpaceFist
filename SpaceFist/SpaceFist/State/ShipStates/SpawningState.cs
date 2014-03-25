using Microsoft.Xna.Framework;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.State.ShipStates
{
    class SpawningState : ShipState
    {
        private const int SpawnTime = 2;

        private Ship Ship { get; set; }

        private DateTime SpawnedAt { get; set; }

        public SpawningState(Ship ship)
        {
            Ship = ship;
        }
        
        public void Update()
        {
            byte increment = 5;
            if (Ship.Tint.A < 255)
            {
                Ship.Tint.A += increment;
                Ship.Tint.R += increment;
                Ship.Tint.G += increment;
                Ship.Tint.B += increment;
            }

            var elapsed = DateTime.Now.Subtract(SpawnedAt).Seconds;

            if (elapsed > SpawnTime)
            {
                Ship.State = new NormalState(Ship);
            }
        }

        public void EnteringState()
        {

            SpawnedAt = DateTime.Now;
            Ship.Tint = Color.Transparent;
        }

        public void ExitingState()
        {
        }
    }
}
