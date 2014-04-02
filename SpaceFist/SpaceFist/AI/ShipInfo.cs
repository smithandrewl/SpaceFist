using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.AI
{
    public class ShipInfo : FuzzyLogicEnabled
    {
        private Game game;
        private EnemyManager enemyManager;
        private BlockManager blockManager;
        private ProjectileManager projectileManager;

        // raw data
        // heading
        // health
        // player acc
        // shots fired per minute
        // weapon damage
        // power-ups used
        // score
        // kills per minute
        // collisions per minute
        // current speed
        // possible target

        // fuzzy values
        // Very low health, low health, medium health, high health, full health
        // Very low acc, low acc, medium acc, high acc, perfect acc
        // Never shoots, some times shoots, trigger happy, maniac
        // low power weapon, normal power weapon, high power weapon
        // Never uses pickups, sometimes uses pickups, uses all pickups
        // No kills, so so, many, crazy
        // perfect, some hits, normal, many hits, bumper cars
        // stationary, moving slowly, moving quickly, top speed
        // stationary, seeking health, avoiding block, chasing, evading, unknown
        public ShipInfo(Game game, Ship ship, EnemyManager enemyManager, BlockManager blockManager, ProjectileManager projectileManager)
        {
            this.game = game;
            this.enemyManager = enemyManager;
            this.blockManager = blockManager;
            this.projectileManager = projectileManager;
        }

        public override void Update()
        {
            throw new NotImplementedException();
        }
    }
}
