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

        private const bool DisplayDebug = false;

        private const float SpeedHigh        = 20;
        private const float SpeedLow         = 0;
        private const float TriggerHappyHigh = 60;
        private const float TriggerHappyLow  = 0;
        private const float AccuracyHigh     = 1;
        private const float AccuracyLow      = 0;
        private const float HealthLow        = 0;
        private const float HealthHigh       = 100;

        private DateTime LastPrint = DateTime.Now;

        // raw data
        private float health;
        private int   speed;
        private Ship  ship;

        private RoundData roundData;

        private FuzzyVariable fuzzySpeed;
        private FuzzyVariable fuzzyHealth;
        private FuzzyVariable fuzzyTriggerHappy;
        private FuzzyVariable fuzzyAccuracy;

        // Speed
        public FuzzyVariable Speed
        {
            get
            {
                return grade(speed, SpeedLow, SpeedHigh, fuzzySpeed);
            }
        }

        // Health
        public FuzzyVariable Health
        {
            get
            {
                return grade(health, HealthLow, HealthHigh, fuzzyHealth);
            }
        }

        // TriggerHappy
        public FuzzyVariable TriggerHappy
        {
            get 
            {
                return grade(roundData.ShotsPerPeriod, TriggerHappyLow, TriggerHappyHigh, fuzzyTriggerHappy);
            }
        }

        public FuzzyVariable Accuracy
        {
            get
            {
                return grade(roundData.acc, AccuracyLow, AccuracyHigh, fuzzyAccuracy);
            }
        }

        public ShipInfo(Ship ship, RoundData roundData)
        {
            this.ship         = ship;
            this.roundData    = roundData;

            fuzzyHealth       = new FuzzyVariable { Name = "Health"        };
            fuzzySpeed        = new FuzzyVariable { Name = "Speed"         };
            fuzzyTriggerHappy = new FuzzyVariable { Name = "Trigger Happy" };
            fuzzyAccuracy     = new FuzzyVariable { Name = "Accuracy"      };
        }

        public override void Update()
        {
            speed  = (int) ship.Velocity.Length();
            health = ship.Health;

            if (DisplayDebug)
            {
                PrintDebugInfo();
            }
        }

        private void PrintDebugInfo()
        {
            if ((DateTime.Now - LastPrint).Seconds >= 1)
            {
                Console.WriteLine("Ship Info:");
                Console.WriteLine(Speed);
                Console.WriteLine(Health);
                Console.WriteLine(TriggerHappy);
                Console.WriteLine(Accuracy);
                Console.WriteLine();

                LastPrint = DateTime.Now;
            }
        }
    }
}
