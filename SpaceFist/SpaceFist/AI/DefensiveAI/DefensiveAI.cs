using SpaceFist.AI.AggressiveAI;
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
        private FireState   fireState;

        public DefensiveAI(Game game, Enemy enemy)
        {
            ShipInfo      = new ShipInfo(game.InPlayState.ship, game.InPlayState.RoundData);
            ShipEnemyInfo = new ShipEnemyInfo(enemy, game.InPlayState.ship, ShipInfo, game);

            defendState = new DefendState(this);
            fireState = new FireState(this, game);
        }

        public ShipEnemyInfo ShipEnemyInfo { get; set; }
        public ShipInfo      ShipInfo      { get; set; }

        public void Update()
        {
            ShipInfo.Update();
            ShipEnemyInfo.Update();

            fireState.Update();
            defendState.Update();
        }
    }
}