using SpaceFist.Entities;
using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.AI.DefensiveAI
{
    class DefensiveAI : EnemyAI
    {
        private DefendState defendState;

        public DefensiveAI(Game game, Enemy enemy)
        {
            defendState = new DefendState(this);
        }

        public ShipEnemyInfo ShipEnemyInfo { get; set; }
        public ShipInfo      ShipInfo      { get; set; }

        public void Update()
        {
            defendState.Update();
        }
    }
}