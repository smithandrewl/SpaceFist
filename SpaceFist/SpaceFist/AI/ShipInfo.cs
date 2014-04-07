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
        
        public float NoHealth { 
            get 
            {
                return ReverseGrade(health, 0, 5); 
            } 
        }

        public float VeryLowHealth  
        {
            get
            {
                return Trapezoid(health, 0, 5, 20, 25);
            }
        }
        public float LowHealth
        {
            get {
                return Trapezoid(health, 20, 25, 40, 45);
            }
        }
        public float MediumHealth
        {
            get
            {
                return Triangle(health, 40, 60, 50);
            }
        }
        public float HighHealth
        {
            get
            {
                return Trapezoid(health, 55, 60, 75, 80);
            }
        }

        public float VeryHighHealth
        {
            get
            {
                return Trapezoid(health, 75, 80, 95, 100);
            }
        }

        public float FullHealth
        {
            get
            {
                return Grade(health, 95, 100);
            }
        }

        public float NoAcc
        {
            get
            {
                return ReverseGrade(acc, 0, 0.05f);
            }
        }
        public float VeryLowAcc
        {
            get
            {
                return Trapezoid(acc, 0, 0.05f, 0.2f, 0.25f);
            }
        }
        public float LowAcc
        {
            get
            {
                return Trapezoid(acc, 0.2f, 0.25f, 0.4f, 0.45f);
            }
        }
        public float MediumAcc
        {
            get
            {
                return Triangle(acc, 0.4f, 0.6f, 0.5f);
            }
        }
        public float HighAcc
        {
            get
            {
                return Trapezoid(acc, 0.55f, 0.6f, 0.75f, 0.8f);
            }
        }

        public float VeryHighAcc
        {
            get
            {
                return Trapezoid(acc, 0.75f, 0.8f, 0.95f, 1);
            }
        }

        public float PerfectAcc
        {
            get
            {
                return Grade(acc, 0.95f, 1);
            }
        }

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

        public float NoKills
        {
            get
            {
                return ReverseGrade(totalKills, 0, 10);
            }
        }
        public float FewKills
        {
            get
            {
                return Trapezoid(totalKills, 0, 10, 40, 50);
            }
        }
        public float SomeKills
        {
            get
            {
                return Trapezoid(totalKills, 40, 50, 80, 90);
            }
        }
        public float NormalKills
        {
            get
            {
                return Triangle(totalKills, 80, 120, 100);
            }
        }
        
        public float ManyKills
        {
            get
            {
                return Trapezoid(totalKills, 110, 120, 150, 160);
            }
        }

        public float VeryManyKills
        {
            get
            {
                return Trapezoid(totalKills, 150, 160, 190, 200);
            }
        }
        public float TooManyKills
        {
            get
            {
                return Grade(totalKills, 190, 200);
            }
        }
        
        // stationary, moving slowly, moving quickly, top speed
        public float NotMoving
        {
            get
            {
                return ReverseGrade(speed, 0, 1);
            }
        }
        public float BarelyMoving
        {
            get
            {
                return Trapezoid(speed, 0, 1, 4, 5);
            }
        }
        public float MovingSlowly
        {
            get
            {
                return Trapezoid(speed, 4, 5, 8, 9);
            }
        }
        public float Moving
        {
            get
            {
                return Triangle(speed, 8, 12, 10);
            }
        }
        public float MovingQuickly
        {
            get
            {
                return Trapezoid(speed, 11, 12, 15, 16);
            }
        }
        public float MovingVeryQuickly
        {
            get
            {
                return Trapezoid(speed, 15, 16, 19, 20);
            }
        }
        public float TopSpeed
        {
            get
            {
                return Grade(speed, 19, 20);
            }
        }

        public ShipInfo(Game game, Ship ship, EnemyManager enemyManager)
        {
            this.game = game;
            this.enemyManager = enemyManager;
        }

        public override void Update()
        {
            throw new NotImplementedException();
        }
    }
}
