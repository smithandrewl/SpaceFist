using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.ShipStates;
using SpaceFist.Weapons;

namespace SpaceFist.Managers
{
    public class PlayerManager
    {
        Game      game;
        RoundData roundData;

        private Ship ship;

        // Damage the ship takes per hit
        private const int HitDamage = 10;
        
        private Vector2 StartingVelocity = new Vector2(0, -2);
        
        public Boolean Alive
        {
            get
            {
                return ship.Alive;
            }
        }

        public PlayerManager(Game game)
        {
            this.game = game;
            roundData = game.InPlayState.RoundData;
        }

        public Ship Ship
        {
            get
            {
                return ship;
            }
        }
        
        public void Initialize()
        {
            ship.Initialize();
        }

        public void Update()
        {
            if (ship.Alive)
            {
                ship.Update();
            }
        }

        public void Draw()
        {
            if (ship.Alive)
            {
                ship.Draw();
            }
        }

        public Ship Spawn()
        {
            game.PlayerSpawn.Play();

            var resolution = game.GraphicsDevice.Viewport.Bounds;
            var camera     = game.InPlayState.Camera;

            // Start the ship at the bottom  in the center of the screen

            var startX = (int)((resolution.Width / 2) + camera.X);
            var startY = (int)((resolution.Height * .85) + camera.Y);

            if (ship != null)
            {
                ship.CurrentState = new SpawningState(ship);

                ship.X     = startX;
                ship.Y     = startY;
                ship.Alive = true;
            }
            else
            {
                ship = new Ship(game, new Vector2(startX, startY));
            }

            ship.Velocity = StartingVelocity;

           return ship;
        }

        public void HandleDeath()
        {
            ship.OnDeath();

            if (roundData.Lives > 0)
            {
                roundData.Lives--;
                ship.HealthPoints = 100;

                Spawn();
            }
            else
            {
                ship.Alive = false;
            }

            ship.Weapon = new LaserWeapon(game, ship);
        }

        internal void ShipHit()
        {
            ship.HealthPoints -= HitDamage;

            if (ship.HealthPoints <= 0)
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
    }
}
