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
        /// <param name="game">The game</param>
        /// <param name="enemy">The enemy this AI will control</param>
        /// <param name="ship">The players ship</param>
        public AggressiveAI(Game game, Enemy enemy, Ship ship)
        {
            ShipInfo      = new AI.ShipInfo(ship, game.InPlayState.RoundData);
            ShipEnemyInfo = new AI.ShipEnemyInfo(enemy, ship, ShipInfo, game);

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
