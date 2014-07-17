using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.AI.Abstract
{
    /// <summary>
    /// This interface must be implemented by any weapon behavior. 
    /// </summary>
    public interface ProjectileBehavior
    {
        void Update(Projectile projectile);
    }
}
