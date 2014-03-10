using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using SpaceshipShooter.Components.Abstract;
using SpaceshipShooter.Components;

namespace SpaceshipShooter  
{
    class Ship : GameObject
    {

        private const int maxHealthPoints = 100;

        public int HealthPoints { get; set; }

        public float Health
        {
            get
            {
                return HealthPoints / (float) maxHealthPoints;
            }
        }

        private const int Width       = 60;
        private const int Height      = 133;
        private const int LeftIndex   = 0;
        private const int AtRestIndex = 4;
        private const int RightIndex  = 7;
        private const int MaxVelocity = 20;

        private IndexedSprite indexedSprite;

        private Vector2 LeftVelocity     = new Vector2(-1, 0); 
        private Vector2 RightVelocity    = new Vector2(1, 0);
        private Vector2 ForwardVelocity  = new Vector2(0, -1);
        private Vector2 BackwardVelocity = new Vector2(0, 1);

        public Ship(Game game, Vector2 position)
            : base(game, 
                   new Rectangle((int)position.X, (int)position.Y, Width, Height),
                   new Physics(), 
                   new ShipInput(), 
                   new IndexedSprite(game.ShipSheet, Width, Height, 4),
                   new NullSoundComponent(),
                   game.ScreenScale)
        {
            indexedSprite = (IndexedSprite) graphics;
            HealthPoints = 100;
        }

        public void Fire()
        {
            Game.InPlayState.fireLaser((int) (X + (Width / 2) - (20 * game.ScreenScale)), (int) ((Y - (35 * game.ScreenScale))));  
        }

        public void Reset()
        {
            indexedSprite.Index = AtRestIndex;
        }

        // Changes the velocity by "velocity" and then clamps the value between the minimum and maximum velocities
        // allowed
        private void IncrementVelocity(Vector2 velocity)
        {
            var xVel = MathHelper.Clamp(Velocity.X + velocity.X, -MaxVelocity, MaxVelocity);
            var yVel = MathHelper.Clamp(Velocity.Y + velocity.Y, -MaxVelocity, MaxVelocity);

            Velocity = new Vector2(xVel, yVel);
        }

        public void Left()
        {
            indexedSprite.Index = LeftIndex;
            IncrementVelocity(LeftVelocity);
        }

        public void Right()
        {
            indexedSprite.Index = RightIndex;
            IncrementVelocity(RightVelocity); ;
        }

        public void Forward()
        {
            indexedSprite.Index = AtRestIndex;
            IncrementVelocity(ForwardVelocity);
        }

        public void Backward()
        {
            indexedSprite.Index = AtRestIndex;
            IncrementVelocity(BackwardVelocity);
        }
    }
}
