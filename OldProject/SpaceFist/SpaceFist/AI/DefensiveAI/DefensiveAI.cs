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
        // Fuzzy sets
        private DefendState defendState;
        private FireState   fireState;

        /// <summary>
        /// Creates a new DefensiveAI instance.
        /// </summary>
        /// <param name="gameData">Common game data</param>
        /// <param name="enemy">The enemy this AI will control</param>
        public DefensiveAI(GameData gameData, Enemy enemy)
        {
            ShipInfo      = new ShipInfo(gameData);
            ShipEnemyInfo = new ShipEnemyInfo(enemy, ShipInfo, gameData);

            defendState = new DefendState(this);
            fireState = new FireState(this, gameData);
        }
        /// <summary>
        /// Fuzzy information about the ship specific to the enemy this AI controls
        /// </summary>
        public ShipEnemyInfo ShipEnemyInfo { get; set; }

        /// <summary>
        /// Fuzzy information about the players ship
        /// </summary>
        public ShipInfo  ShipInfo { get; set; }

        public void Update()
        {
            ShipInfo.Update();
            ShipEnemyInfo.Update();

            fireState.Update();
            defendState.Update();
        }
    }
}