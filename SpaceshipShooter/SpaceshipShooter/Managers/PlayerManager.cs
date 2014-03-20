using Microsoft.Xna.Framework;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceshipShooter.Managers
{
    public class PlayerManager
    {
        Game game;

        private int score = 0;
        private int lives = 2;
        private Ship ship;

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

        public void Update(GameTime gameTime)
        {
            if (ship.Alive)
            {
                ship.Update(gameTime);

            }
        }

        public void Draw(GameTime gameTime)
        {
            if (ship.Alive)
            {
                ship.Draw(gameTime);
            }
        }

        public Ship Spawn()
        {
            var resolution = game.GraphicsDevice.Viewport.Bounds;

            // Start the ship at the bottom in the center of the screen
           ship = new Ship(game, new Vector2((float)(resolution.Width / 2),
                                             (float)(resolution.Height * .80)));
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

            ship.HealthPoints -= 10;

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
