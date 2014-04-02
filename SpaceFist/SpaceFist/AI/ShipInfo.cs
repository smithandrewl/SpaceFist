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
        private int     killsAMinute;
        private int     collisionsPerMinute;
        private int     speed;

        private Entity possibleTarget;

        // fuzzy values
        
        // The players health
        public double VeryLowHealth { get; set; }
        public double LowHealth     { get; set; }
        public double MediumHealth  { get; set; }
        public double HighHealth    { get; set; }
        public double FullHealth    { get; set; }

        
        public double VeryLowAcc { get; set; }
        public double LowAcc     { get; set; }
        public double MediumAcc  { get; set; }
        public double HighAcc    { get; set; }
        public double PerfectAcc { get; set; }

        // How often the player shoots
        public double NeverShoots    { get; set; }
        public double SometimeShoots { get; set; }
        public double TriggerHappy   { get; set; }
        public double Maniac         { get; set; }

        // Weapon Strength
        public double WeakWeapon     { get; set; }
        public double NormalWeapon   { get; set; }
        public double PowerfulWeapon { get; set; }

        public double NoKills       { get; set; }
        public double SomeKills     { get; set; }
        public double ManyKills     { get; set; }
        public double VeryManyKills { get; set; }

        // perfect, some hits, normal, many hits, bumper cars
        public double NoBumps     { get; set; }
        public double SomeBumps   { get; set; }
        public double NormalBumps { get; set; }
        public double ManyBumps   { get; set; }
        public double BumperCars  { get; set; }


        // stationary, moving slowly, moving quickly, top speed
        public double NotMoving     { get; set; }
        public double MovingSlowly  { get; set; }
        public double Moving        { get; set; }
        public double MovingQuickly { get; set; }
        public double TopSpeed      { get; set; }

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
