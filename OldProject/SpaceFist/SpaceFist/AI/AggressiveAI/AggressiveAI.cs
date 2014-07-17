using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.Abstract;
using SpaceFist.Entities;
using SpaceFist.Managers;

namespace SpaceFist.AI.DummyAI
{
    /// <summary>
    /// An aggressive AI which follows the ship and attempts to ram it.
    /// </summary>
    class AggressiveAI : EnemyAI
    {
        /// <summary>
        /// The ram fuzzy state
        /// </summary>
        private RamState ramState;
      
        /// <summary>
        /// Creates a new AggressiveAI instance.
        /// </summary>
        /// <param name="gameData">Common game data</param>
        /// <param name="enemy">The enemy this AI will control</param>
        public AggressiveAI(GameData gameData, Enemy enemy)
        {
            ShipInfo      = new AI.ShipInfo(gameData);
            ShipEnemyInfo = new AI.ShipEnemyInfo(enemy, ShipInfo, gameData);

            ramState = new RamState(this, gameData);
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
