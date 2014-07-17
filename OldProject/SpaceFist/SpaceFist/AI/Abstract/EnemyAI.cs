using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.Abstract;
using SpaceFist.Entities;

namespace SpaceFist.AI
{
    /// <summary>
    /// The interface implemented by all enemy AI's.
    /// </summary>
    public interface EnemyAI
    {
        ShipEnemyInfo ShipEnemyInfo { get; set; }
        ShipInfo      ShipInfo      { get; set; }

        void Update();
    }
}
