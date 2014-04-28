using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.AI.Abstract
{
    public interface ProjectileBehavior
    {
        void Update(Projectile projectile);
    }
}
