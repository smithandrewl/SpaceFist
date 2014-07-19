package com.spacefist.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.components.Sound;
import com.spacefist.components.abst.GraphicsComponent;
import com.spacefist.components.abst.InputComponent;
import com.spacefist.components.abst.PhysicsComponent;
import com.spacefist.components.abst.SoundComponent;
import com.spacefist.state.abst.ShipState;
import com.spacefist.state.abst.StateMachine;
import com.spacefist.components.IndexedSprite;
import com.spacefist.weapons.abst.Weapon;

/// <summary>
    /// 
    /// </summary>
    public class Ship extends Entity implements StateMachine<ShipState>
    {
        /// <summary>
        /// The current behavior of the ship
        /// 
        /// The current states are spawning, normal and low health
        /// </summary>
        private ShipState state;

        private static final int maxHealthPoints = 100;

        private Weapon weapon;
        private int    healthPoints;

        /// <summary>
        /// The current state of the ship.  
        /// </summary>
        private ShipState CurrentState;

        // TODO: Remove me after constructor conversion
        /**
         * @param gameData  Common game data
         * @param rectangle The size and position of the entity
         * @param physics   The physics component to use
         * @param input     The input component to use
         * @param graphics  The graphics component to use
         * @param sound     The sound component to use
         * @param rotation  The rotation of the entity
         */
        public Ship(GameData gameData, Rectangle rectangle, PhysicsComponent physics, InputComponent input, GraphicsComponent graphics, SoundComponent sound, float rotation) {
            super(gameData, rectangle, physics, input, graphics, sound, rotation);
        }

        @Override
        public ShipState getCurrentState() {
            return state;
        }

        @Override
        public void setCurrentState(ShipState shipState) {
            state.ExitingState();
            shipState.EnteringState();
            state = shipState;
        }

        /// <summary>
        /// The ships current weapon.
        /// </summary>
        public Weapon getWeapon() {
            return weapon;
        }

        public void setWeapon(Weapon weapon) {
            this.weapon = weapon;
        }

        /// <summary>
        /// The number of health points the ship has left.
        /// </summary>

        public int getHealthPoints() {
            return healthPoints;
        }

        public void setHealthPoints(int healthPoints) {
            this.healthPoints = healthPoints;
        }

        /// <summary>
        /// 
        /// </summary>

        public float getHealth() {
            return getHealthPoints() / (float) maxHealthPoints;
        }

        // The dimensions of the ship
        private static final int Width  = 60;
        private static final int Height = 133;

        // The frame of the sprite sheet (ShipSheet.png) to draw
        // Frame 0 is the ship turning left
        // Frame 4 is the ship in its normal state
        // Frame 7 is the ship turning right
        private static final int LeftIndex   = 0;
        private static final int AtRestIndex = 4;
        private static final int RightIndex  = 7;

        private static final int MaxVelocity = 20;

        private IndexedSprite indexedSprite;

        // The predefined velocities to use when changing position
        private Vector2 LeftVelocity     = new Vector2(-1, 0);
        private Vector2 RightVelocity    = new Vector2(1, 0);
        private Vector2 ForwardVelocity  = new Vector2(0, -1);
        private Vector2 BackwardVelocity = new Vector2(0, 1);

        /*
        TODO: Convert
        /// <summary>
        /// Creates a new Ship instance at the specified location.
        /// </summary>
        /// <param name="gameData">Common game data</param>
        /// <param name="position">The location of the ship in the game world.</param>
        public Ship(GameData gameData, Vector2 position)
            : base(gameData, 
                   new Rectangle(
                       (int)position.X, 
                       (int)position.Y, 
                       (int)(Width  * (gameData.ScreenScale / 2)), 
                       (int)(Height * (gameData.ScreenScale / 2))
                   ),
                   new Physics(), 
                   new ShipInput(), 
                   new IndexedSprite(gameData.Textures["ShipSheet"], Width, Height, 4),
                   new Sound(gameData.SoundEffects["PlayerDeath"]),
                   gameData.ScreenScale)
        {
            indexedSprite = (IndexedSprite) graphics;
            HealthPoints  = 100;

            // Start out with the laser weapon
            Weapon = new LaserWeapon(gameData);

            // Start the ship in the spawning state
            state = new SpawningState(gameData);
        }
        */
        /// <summary>
        /// Plays the ships destruction sound effect.
        /// </summary>
        public void OnDeath()
        {
            ((Sound) getSound()).play();
        }

        /// <summary>
        /// Resets the ship to the default state (drawn normally (no flashing or fading)).
        /// </summary>
        public void ResetState()
        {
            // CurrentState = new NormalState(gameData);
            Reset();
        }

        /// <summary>
        /// Updates the ship
        /// </summary>
        public void Update()
        {
            CurrentState.Update();
            super.Update();
        }

        /// <summary>
        /// Fire the active weapon
        /// </summary>
        public void Fire()
        {
            weapon.fire();
        }

        public void Reset()
        {
            // This causes the ship to be drawn in its default state (not turning left or right)
            indexedSprite.setIndex(AtRestIndex);
        }

        /// <summary>
        /// Changes the velocity by "velocity" and then clamps the value between the minimum and maximum velocities
        /// allowed.
        /// </summary>
        private void IncrementVelocity(Vector2 velocity)
        {
            /*
            TODO: Convert

            var xVel = MathHelper.Clamp(
                Velocity.X + velocity.X, 
                -MaxVelocity, 
                MaxVelocity
            );
            
            var yVel = MathHelper.Clamp(
                Velocity.Y + velocity.Y, 
                -MaxVelocity, 
                MaxVelocity
            );

            Velocity = new Vector2(xVel, yVel);
            */
        }

        /// <summary>
        /// Causes the ship to move to the left.
        /// </summary>
        public void Left()
        {
            // This tells indexedSprite to draw the ship turning left
            indexedSprite.setIndex(LeftIndex);

            // Change to a left moving velocity
            IncrementVelocity(LeftVelocity);
        }

        /// <summary>
        /// Causes the ship to move to the right.
        /// </summary>
        public void Right()
        {
            // This tells indexedSprite to draw the ship turning right
            indexedSprite.setIndex(RightIndex);

            // Change to a right moving velocity
            IncrementVelocity(RightVelocity);
        }

        /// <summary>
        /// Causes the ship to move forward
        /// </summary>
        public void Forward()
        {
            // This tells indexedSprite to draw the ship normally (not turning)
            indexedSprite.setIndex(AtRestIndex);

            // Change to a forward moving velocity
            IncrementVelocity(ForwardVelocity);
        }

        public void Backward()
        {
            // This tells indexedSprite to draw the ship normally (not turning)
            indexedSprite.setIndex(AtRestIndex);

            // Change to a backwards moving velocity
            IncrementVelocity(BackwardVelocity);
        }
    }