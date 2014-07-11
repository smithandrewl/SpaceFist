using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.ShipStates;
using SpaceFist.Weapons;

namespace SpaceFist.Managers
{
    /// <summary>
    /// Handles player and ship interaction with the game world.
    /// </summary>
    public class PlayerManager
    {
        GameData  gameData;
        RoundData roundData;

        // Damage the ship takes per hit
        private const int HitDamage = 10;
        
        // Start the players ship moving at a velocity greater than the camer scrolls
        private Vector2 StartingVelocity = new Vector2(0, -2);
        
        public Boolean Alive
        {
            get
            {
                return gameData.Ship.Alive;
            }
        }

        /// <summary>
        /// Creates a new PlayerManager instance.
        /// </summary>
        /// <param name="gameData">Common game data</param>
        public PlayerManager(GameData gameData)
        {
            this.gameData = gameData;
            roundData     = gameData.RoundData;
        }
        
        public void Initialize()
        {
            gameData.Ship.Initialize();
        }

        public void Update()
        {
            if (gameData.Ship.Alive)
            {
                gameData.Ship.Update();
            }
        }

        public void Draw()
        {
            if (gameData.Ship.Alive)
            {
                gameData.Ship.Draw();
            }
        }

        public Ship Spawn()
        {
            gameData.SoundEffects["PlayerSpawn"].Play();

            var resolution = gameData.Resolution;
            var camera     = gameData.Camera;

            // Start the ship at the bottom  in the center of the screen

            var startX = (int)((resolution.Width / 2) + camera.X);
            var startY = (int)((resolution.Height * .85) + camera.Y);

            if (gameData.Ship != null)
            {
                gameData.Ship.CurrentState = new SpawningState(gameData);

                gameData.Ship.X     = startX;
                gameData.Ship.Y     = startY;
                gameData.Ship.Alive = true;
            }
            else
            {
                gameData.Ship = new Ship(gameData, new Vector2(startX, startY));
                gameData.Ship.CurrentState.EnteringState();
            }

            gameData.Ship.HealthPoints = 100;
            gameData.Ship.Velocity     = StartingVelocity;

           return gameData.Ship;
        }

        public void HandleDeath()
        {
            gameData.Ship.OnDeath();

            if (roundData.Lives > 0)
            {
                roundData.Lives--;
                gameData.Ship.HealthPoints = 100;

                Spawn();
            }
            else
            {
                gameData.Ship.Alive = false;
            }

            gameData.Ship.Weapon = new LaserWeapon(gameData);
        }

        internal void ShipHit()
        {
            gameData.Ship.HealthPoints -= HitDamage;

            if (gameData.Ship.HealthPoints <= 0)
            {
                HandleDeath();
            }
        }

        internal void Scored()
        {
            roundData.Score += 10;
        }

        internal void ResetScore()
        {
            roundData.Score = 0;
        }

        internal void ResetLives()
        {
            roundData.Lives = 2;
        }

        // Replaces the ships current weapon
        // with the laser weapon.
        internal void ResetWeapon()
        {
            gameData.Ship.Weapon = new LaserWeapon(gameData);
        }
    }
}
