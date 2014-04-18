using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using SpaceFist.Components.Abstract;
using SpaceFist.Components;
using SpaceFist.State.Abstract;
using SpaceFist.State.ShipStates;
using SpaceFist.Weapons;

namespace SpaceFist  
{
    /// <summary>
    /// 
    /// </summary>
    public class Ship : Entity, StateMachine<ShipState>
    {
        /// <summary>
        /// The current behavior of the ship
        /// 
        /// The current states are spawning, normal and low health
        /// </summary>
        private ShipState state;

        private const int maxHealthPoints = 100;

        public Weapon Weapon { get; set; }

        public ShipState CurrentState
        {
            get
            {
                return state;
            }
            set
            {
                state.ExitingState();
                value.EnteringState();
                state = value;
            }
        }

        /// <summary>
        /// 
        /// </summary>
        public int HealthPoints { get; set; }

        /// <summary>
        /// 
        /// </summary>
        public float Health
        {
            get
            {
                return HealthPoints / (float) maxHealthPoints;
            }
        }

        // The dimensions of the ship
        private const int Width       = 60;
        private const int Height      = 133;

        // The frame of the sprite sheet (ShipSheet.png) to draw
        // Frame 0 is the ship turning left
        // Frame 4 is the ship in its normal state
        // Frame 7 is the ship turning right
        private const int LeftIndex   = 0;
        private const int AtRestIndex = 4;
        private const int RightIndex  = 7;

        private const int MaxVelocity = 20;

        private IndexedSprite indexedSprite;

        // The predefined velocities to use when changing position
        private Vector2 LeftVelocity     = new Vector2(-1, 0); 
        private Vector2 RightVelocity    = new Vector2(1, 0);
        private Vector2 ForwardVelocity  = new Vector2(0, -1);
        private Vector2 BackwardVelocity = new Vector2(0, 1);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="game"></param>
        /// <param name="position"></param>
        public Ship(Game game, Vector2 position)
            : base(game, 
                   new Rectangle((int)position.X, (int)position.Y, Width, Height),
                   new Physics(), 
                   new ShipInput(), 
                   new IndexedSprite(game.ShipSheet, Width, Height, 4),
                   new Sound(game.PlayerDeath),
                   game.ScreenScale)
        {
            indexedSprite = (IndexedSprite) graphics;
            HealthPoints  = 100;

            // Start out with the laser weapon
            Weapon = new LaserWeapon(game, this);

            // Start the ship in the spawning state
            state = new SpawningState(this);
            state.EnteringState();
        }
        public void OnDeath()
        {
            ((Sound)sound).play();
        }

        public void ResetState()
        {
            CurrentState = new NormalState(this);
            Reset();
        }

        /// <summary>
        /// Updates the ship
        /// </summary>
        public override void Update()
        {
            CurrentState.Update();
            base.Update();
        }

        public void Fire()
        {
            Weapon.fire();
        }

        public void Reset()
        {
            // This causes the ship to be drawn in its default state (not turning left or right)
            indexedSprite.Index = AtRestIndex;
        }

        /// <summary>
        /// Changes the velocity by "velocity" and then clamps the value between the minimum and maximum velocities
        /// allowed.
        /// </summary>
        private void IncrementVelocity(Vector2 velocity)
        {
            var xVel = MathHelper.Clamp(Velocity.X + velocity.X, -MaxVelocity, MaxVelocity);
            var yVel = MathHelper.Clamp(Velocity.Y + velocity.Y, -MaxVelocity, MaxVelocity);

            Velocity = new Vector2(xVel, yVel);
        }

        /// <summary>
        /// Causes the ship to move to the left.
        /// </summary>
        public void Left()
        {
            // This tells indexedSprite to draw the ship turning left
            indexedSprite.Index = LeftIndex;

            // Change to a left moving velocity
            IncrementVelocity(LeftVelocity);
        }

        /// <summary>
        /// Causes the ship to move to the right.
        /// </summary>
        public void Right()
        {
            // This tells indexedSprite to draw the ship turning right
            indexedSprite.Index = RightIndex;

            // Change to a right moving velocity
            IncrementVelocity(RightVelocity);
        }

        /// <summary>
        /// Causes the ship to move forward
        /// </summary>
        public void Forward()
        {
            // This tells indexedSprite to draw the ship normally (not turning)
            indexedSprite.Index = AtRestIndex;

            // Change to a forward moving velocity
            IncrementVelocity(ForwardVelocity);
        }

        public void Backward()
        {
            // This tells indexedSprite to draw the ship normally (not turning)
            indexedSprite.Index = AtRestIndex;

            // Change to a backwards moving velocity
            IncrementVelocity(BackwardVelocity);
        }
    }
}
