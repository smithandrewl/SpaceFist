﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;

namespace Project2.Components.Abstract
{
    public interface Component
    {
        void Update(Game game, GameObject obj, GameTime time);
    }
}
