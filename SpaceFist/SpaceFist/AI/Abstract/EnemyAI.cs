using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.Abstract;
using SpaceFist.Entities;

namespace SpaceFist.AI
{
    public interface EnemyAI
    {
        ShipEnemyInfo ShipEnemyInfo { get; set; }
        ShipInfo      ShipInfo      { get; set; }
        EnemyAIState  State         { get; set; }

        void Update();
    }
}
