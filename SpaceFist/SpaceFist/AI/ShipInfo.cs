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

        // Speed
        public float LowSpeed
        {
            get
            {
                return ReverseGrade(speed, 0, 10);
            }
        }

        public float MediumSpeed
        {
            get
            {
                return Triangle(speed, 0, 10, 20);
            }
        }

        public float HighSpeed
        {
            get
            {
                return Grade(speed, 10, 20);
            }
        }

        // Health
        public float LowHealth
        {
            get
            {
                return ReverseGrade(health, 0, 0.5f);
            }
        }

        public float MediumHealth
        {
            get
            {
                return Triangle(health, 0, 0.5f, 1);
            }
        }

        public float HighHealth
        {
            get
            {
                return Grade(health, .5f, 1);
            }
        }

        // TriggerHappy
        public float LowTriggerHappy
        {
            get
            {
                return ReverseGrade(roundData.ShotsPerPeriod, 0, 12.5f);
            }
        }

        public float MediumTriggerHappy
        {
            get
            {
                return Triangle(roundData.ShotsPerPeriod, 0, 12.5f, 25);
            }
        }

        public float HighTriggerHappy
        {
            get
            {
                return Grade(roundData.ShotsPerPeriod, 12.5f, 25);
            }
        }

        public ShipInfo(Game game, Ship ship, EnemyManager enemyManager, RoundData roundData)
        {
            this.game = game;
            this.enemyManager = enemyManager;
            this.ship = ship;
            this.roundData = roundData;
            
        }

        public override void Update()
        {
            speed = (int) ship.Velocity.Length();
            health = ship.Health;

            if ((DateTime.Now - LastPrint).Seconds >= 1)
            {
                Console.WriteLine("Speed: {0}, {1:P}, {2:P}, {3:P}", speed, LowSpeed, MediumSpeed, HighSpeed);
                Console.WriteLine("Health: {0}, {1:P}, {2:P}, {3:P}",health,  LowHealth, MediumHealth, HighHealth);
                Console.WriteLine("TriggerHappy: {0}, {1:P}, {2:P}, {3:P}", roundData.ShotsPerPeriod, LowTriggerHappy, MediumTriggerHappy, HighTriggerHappy);
                Console.WriteLine();
                LastPrint = DateTime.Now;
            }

           }
    }
}
