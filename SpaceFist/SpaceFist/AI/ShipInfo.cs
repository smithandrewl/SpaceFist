using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;

namespace SpaceFist.AI
{
    public class ShipInfo : FuzzyLogicEnabled
    {
        private Game              game;
        private EnemyManager      enemyManager;
        private DateTime LastPrint = DateTime.Now;

        // raw data
        private float   health;
        private int     speed;
        private Ship ship;

        private RoundData roundData;

        private FuzzyVariable fuzzySpeed;
        private FuzzyVariable fuzzyHealth;
        private FuzzyVariable fuzzyTriggerHappy;

        // Speed
        public FuzzyVariable Speed
        {
            get
            {
                return grade(speed, 0, 20, fuzzySpeed);
            }
        }

        // Health
        public FuzzyVariable Health
        {
            get
            {
                return grade(health, 0, 1, fuzzyHealth);
            }
        }

        // TriggerHappy
        public FuzzyVariable TriggerHappy {
            get {
                return grade(roundData.ShotsPerPeriod, 0, 60, fuzzyTriggerHappy);
            }
        }

        public ShipInfo(Game game, Ship ship, EnemyManager enemyManager, RoundData roundData)
        {
            this.game = game;
            this.enemyManager = enemyManager;
            this.ship = ship;
            this.roundData = roundData;

            fuzzyHealth       = new FuzzyVariable { Name = "Health" };
            fuzzySpeed        = new FuzzyVariable { Name = "Speed" };
            fuzzyTriggerHappy = new FuzzyVariable { Name = "Trigger Happy" };
        }

        public override void Update()
        {
            speed = (int) ship.Velocity.Length();
            health = ship.Health;

            PrintDebugInfo();

        }

        private void PrintDebugInfo()
        {
            if ((DateTime.Now - LastPrint).Seconds >= 1)
            {
                Console.WriteLine(Speed);
                Console.WriteLine(Health);
                Console.WriteLine(TriggerHappy);
                Console.WriteLine();

                LastPrint = DateTime.Now;
            }
        }


    }
}
