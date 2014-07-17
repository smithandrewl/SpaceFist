using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;

namespace SpaceFist.AI
{
    /// <summary>
    /// Provides fuzzy information about the ship and how the player
    /// has played this current round.
    /// </summary>
    public class ShipInfo : FuzzyLogicEnabled
    {
        private const bool DisplayDebug = false;

        // ------------------- fuzzy variable range constants ---------
        private const float SpeedHigh        = 20;
        private const float SpeedLow         = 0;
        
        private const float TriggerHappyHigh = 60;
        private const float TriggerHappyLow  = 0;
        
        private const float AccuracyHigh     = 1;
        private const float AccuracyLow      = 0;
        
        private const float HealthLow        = 0;
        private const float HealthHigh       = 100;
        // ------------------------------------------------------------

        // The last time debug information was displayed
        private DateTime LastPrint = DateTime.Now;

        // ======================== Crisp Input  ========================
        // ship data
        private float health;
        private int   speed;

        // round data
        private RoundData roundData;
        // ===============================================================

        // ======================== Fuzzy Input Variables ================
        private FuzzyVariable fuzzySpeed;
        private FuzzyVariable fuzzyHealth;
        private FuzzyVariable fuzzyTriggerHappy;
        private FuzzyVariable fuzzyAccuracy;       
        private GameData      gameData;
        // ===============================================================

        /// <summary>
        /// The degree to which the ship belongs to the low, medium and high speed sets.
        /// </summary>
        public FuzzyVariable Speed
        {
            get
            {
                return grade(speed, SpeedLow, SpeedHigh, fuzzySpeed);
            }
        }

        /// <summary>
        /// The degree to which the ship belongs to the low, medium and high health sets.
        /// </summary>
        public FuzzyVariable Health
        {
            get
            {
                return grade(health, HealthLow, HealthHigh, fuzzyHealth);
            }
        }

        /// <summary>
        /// The degree to which the ship belongs to the low, medium and high sets for firing often.
        /// If the player never shoots, the membership in TriggerHappy.Low will be 100 percent.
        /// </summary>
        public FuzzyVariable TriggerHappy
        {
            get 
            {
                return grade(roundData.ShotsPerPeriod, TriggerHappyLow, TriggerHappyHigh, fuzzyTriggerHappy);
            }
        }

        /// <summary>
        /// The degree to which the player has low, medium or high accuracy.
        /// </summary>
        public FuzzyVariable Accuracy
        {
            get
            {
                return grade(roundData.acc, AccuracyLow, AccuracyHigh, fuzzyAccuracy);
            }
        }

        /// <summary>
        /// Creates a new ShipInfo instance.
        /// </summary>
        /// <param name="gameData">Common game data</param>
        public ShipInfo(GameData gameData)
        {
            this.roundData    = gameData.RoundData;
            this.gameData     = gameData;

            fuzzyHealth       = new FuzzyVariable { Name = "Health"        };
            fuzzySpeed        = new FuzzyVariable { Name = "Speed"         };
            fuzzyTriggerHappy = new FuzzyVariable { Name = "Trigger Happy" };
            fuzzyAccuracy     = new FuzzyVariable { Name = "Accuracy"      };
        }

        public override void Update()
        {
            // The ships speed is the magnitude of its velocity
            speed  = (int) gameData.Ship.Velocity.Length();
            health = gameData.Ship.Health;

            if (DisplayDebug)
            {
                PrintDebugInfo();
            }
        }

        /// <summary>
        /// Displays fuzzy membership information once a second.
        /// </summary>
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
