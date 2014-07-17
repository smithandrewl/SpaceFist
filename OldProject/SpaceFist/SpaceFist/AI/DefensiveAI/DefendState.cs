using SpaceFist.Entities;
using SpaceFist.State.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.AI.DefensiveAI
{
    /// <summary>
    /// The fuzzy state for a non-firing AI
    /// </summary>
    public class DefendState : FuzzyLogicEnabled, EnemyAIState
    {
        public EnemyAI       AI            { get; set; }
        public ShipInfo      ShipInfo      { get; set; }
        public ShipEnemyInfo ShipEnemyInfo { get; set; }
        public Enemy         Enemy         { get; set; }

        public DefendState(EnemyAI ai)
        {
            AI            = ai;
            ShipInfo      = AI.ShipInfo;
            ShipEnemyInfo = AI.ShipEnemyInfo;
        }

        public override void Update()
        {

        }
    }
}
