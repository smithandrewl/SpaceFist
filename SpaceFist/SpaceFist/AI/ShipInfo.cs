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
        public float NoHealth       { get; set; }
        public float VeryLowHealth  { get; set; }
        public float LowHealth      { get; set; }
        public float MediumHealth   { get; set; }
        public float HighHealth     { get; set; }
        public float VeryHighHealth { get; set; }
        public float FullHealth     { get; set; }


        public float NoAcc       { get; set; }
        public float VeryLowAcc  { get; set; }
        public float LowAcc      { get; set; }
        public float MediumAcc   { get; set; }
        public float HighAcc     { get; set; }
        public float VeryHighAcc { get; set; }
        public float PerfectAcc  { get; set; }

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

        public float NoKills       { get; set; }
        public float FewKills      { get; set; }
        public float SomeKills     { get; set; }
        public float NormalKills   { get; set; }
        public float ManyKills     { get; set; }
        public float VeryManyKills { get; set; }
        public float TooManyKills  { get; set; }
        
        // perfect, some hits, normal, many hits, bumper cars
        public float NoBumps       { get; set; }
        public float FewBumps      { get; set; }
        public float SomeBumps     { get; set; }
        public float NormalBumps   { get; set; }
        public float ManyBumps     { get; set; }
        public float VeryManyBumps { get; set; }
        public float BumperCars    { get; set; }

        // stationary, moving slowly, moving quickly, top speed
        public float NotMoving         { get; set; }
        public float BarelyMoving      { get; set; }
        public float MovingSlowly      { get; set; }
        public float Moving            { get; set; }
        public float MovingQuickly     { get; set; }
        public float MovingVeryQuickly { get; set; }
        public float TopSpeed          { get; set; }

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
