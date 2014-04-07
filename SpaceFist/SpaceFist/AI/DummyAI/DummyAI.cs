using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.Abstract;
using SpaceFist.Entities;
using SpaceFist.Managers;

namespace SpaceFist.AI.DummyAI
{
    class DummyAI : EnemyAI
    {
        public State.Abstract.EnemyAIState State { get; set; }

        public DummyAI(Game game, Enemy enemy, Ship ship, EnemyManager enemyManager)
        {
            ShipInfo      = new AI.ShipInfo(game, ship, enemyManager);
            ShipEnemyInfo = new AI.ShipEnemyInfo(enemy, ship, ShipInfo);

            State = new DummyAIDummyState(this);
            State.EnteringState();
        }

        public void Update()
        {
            ShipInfo.Update();
            ShipEnemyInfo.Update();
            State.Update();
        }

        public ShipEnemyInfo ShipEnemyInfo { get; set; }
        public ShipInfo      ShipInfo      { get; set; }
    }
}
