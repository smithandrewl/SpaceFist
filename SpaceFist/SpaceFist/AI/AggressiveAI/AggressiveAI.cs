using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.Abstract;
using SpaceFist.Entities;
using SpaceFist.Managers;

namespace SpaceFist.AI.DummyAI
{
    class AggressiveAI : EnemyAI
    {
        private RamState ramState;
      
        public AggressiveAI(Game game, Enemy enemy, Ship ship, EnemyManager enemyManager)
        {
            ShipInfo      = new AI.ShipInfo(game, ship, enemyManager, game.InPlayState.RoundData);
            ShipEnemyInfo = new AI.ShipEnemyInfo(enemy, ship, ShipInfo);

            ramState = new RamState(this);
        }

        public void Update()
        {
            ShipInfo.Update();
            ShipEnemyInfo.Update();
            ramState.Update();
        }

        public ShipEnemyInfo ShipEnemyInfo { get; set; }
        public ShipInfo      ShipInfo      { get; set; }
    }
}
