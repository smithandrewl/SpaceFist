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
        private BlockManager      blockManager;
        private ProjectileManager projectileManager;
        private DateTime LastPrint = DateTime.Now;

        // raw data
        private Vector2 heading;
        private float   health;
        private float   acc;
        private float   shotsPerMin;
        private float   weaponDamage;
        private int     powerUpsUsed;
        private int     score;
        private int     totalKills;
        private int     killsAMinute;
        private int     collisionsPerMinute;
        private int     speed;

        private Entity possibleTarget;
        private Ship ship;

        // How often the player shoots
        public float NeverShoots    { get; set; }
        public float RarelyShoots   { get; set; }
        public float SometimeShoots { get; set; }
        public float Normal         { get; set; }
        public float ShootsOften    { get; set; }
        public float TriggerHappy   { get; set; }
        public float Maniac         { get; set; }

        // Weapon Strength
        public float WeakWeapon     { get; set; }
        public float NormalWeapon   { get; set; }
        public float PowerfulWeapon { get; set; }

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

        public ShipInfo(Game game, Ship ship, EnemyManager enemyManager)
        {
            this.game = game;
            this.enemyManager = enemyManager;
            this.ship = ship;
        }

        public override void Update()
        {
            speed = (int) ship.Velocity.Length();
            health = ship.Health;

            if ((DateTime.Now - LastPrint).Seconds >= 1)
            {
                Console.WriteLine("Speed: {0}, {1:P}, {2:P}, {3:P}", speed, LowSpeed, MediumSpeed, HighSpeed);
                Console.WriteLine("Health: {0}, {1:P}, {2:P}, {3:P}",health,  LowHealth, MediumHealth, HighHealth);
                Console.WriteLine();
                LastPrint = DateTime.Now;
            }

           }
    }
}
