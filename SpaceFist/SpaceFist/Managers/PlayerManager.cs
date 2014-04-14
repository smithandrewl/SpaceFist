using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.ShipStates;

namespace SpaceFist.Managers
{
    public class PlayerManager
    {
        Game game;

        private int score = 0;
        private int lives = 2;

        private Ship ship;

        // Damage the ship takes per hit
        private const int HitDamage = 10;

        public int Score
        {
            get
            {
                return score;
            }
        }

        public int Lives
        {
            get
            {
                return lives;
            }
        }

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
            var resolution = game.GraphicsDevice.Viewport.Bounds;
            var camera = game.InPlayState.Camera;

            // Start the ship at the bottom  in the center of the screen

            var startX = (int)((resolution.Width / 2) + camera.X);
            var startY = (int)((resolution.Height * .75) + camera.Y);

            if (ship != null)
            {
                ship.CurrentState = new SpawningState(ship);

                ship.X = startX;
                ship.Y = startY;
                ship.Alive = true;
                ship.Velocity = Vector2.Zero;
            }
            else
            {
                ship = new Ship(game, new Vector2(startX, startY));
            }

           return ship;
        }

        public void HandleDeath()
        {
            if (lives > 0)
            {
                lives--;
                ship.HealthPoints = 100;

                Spawn();
            }
            else
            {
                ship.Alive = false;
            }
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
            score += 10;
        }

        internal void ResetScore()
        {
            score = 0;
        }

        internal void ResetLives()
        {
            lives = 2;
        }
    }
}
