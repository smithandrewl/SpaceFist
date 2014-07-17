using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Weapons
{
    // Any class implementing this interface can be used as a weapon
    // by the Ship
    public interface Weapon
    {
        void fire();
    }
}
