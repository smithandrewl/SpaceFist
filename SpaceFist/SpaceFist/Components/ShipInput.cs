using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.Components.Abstract;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework;

namespace SpaceFist.Components
{
    // Calls ship methods in response to user input
    public class ShipInput : InputComponent
    {
        private bool spaceDown = false;
        private bool aDown     = false;
        private bool dDown     = false;

        public void Update(Game game, Entity obj)
        {
            var ship = (Ship) obj;
            KeyboardState keys = Keyboard.GetState();

            if (keys.IsKeyDown(Keys.W))
            {
                ship.Forward();
            }

            if(keys.IsKeyUp(Keys.A) && aDown)
            {
                aDown = false;
                ship.Reset();
            }

            if (keys.IsKeyDown(Keys.A))
            {
                aDown = true;
                ship.Left();
            }

            if (keys.IsKeyUp(Keys.D) && dDown)
            {
                dDown = false;
                ship.Reset();
            }
            
            if (keys.IsKeyDown(Keys.D))
            {
                dDown = true;
                ship.Right();
            }

            if (keys.IsKeyDown(Keys.S))
            {
                ship.Backward();
            }

            if (keys.IsKeyDown(Keys.Space))
            {
                if (!spaceDown)
                {
                    spaceDown = true;
                    ship.Fire();
                }
            }

            if (keys.IsKeyUp(Keys.Space))
            {
                spaceDown = false;
            }
        }
    }
}
