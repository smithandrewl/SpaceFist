using Microsoft.Xna.Framework;
using SpaceFist.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Entities
{
    /// <summary>
    /// Represents and draws an explosion at its x and y coordinates.
    /// 
    /// The Explosion instance is "killed" after the animation finishes.
    /// </summary>
    class Explosion : Entity
    {
        // The dimensions of the explosion
        private const int height = 122;
        private const int width  = 122;

        // The number of frames / pictures in the animation
        private const int lastFrame = 9;

        // The time the explosion animation started
        // and the time to wait before drawing the next frame
        private long startTime;
        private long TimeBetweenFrames = 400000; 

        public Explosion(Game game, Vector2 position): 
            base(game, 
                 new Rectangle((int)position.X, (int)position.Y, width, height), 
                 new Physics(), 
                 new NullInputComponent(), 
                 new IndexedSprite(game.ExplosionTexture, width, height), 
                 new NullSoundComponent(),
                 game.ScreenScale)
        {
            startTime = System.DateTime.Now.Ticks;
            Velocity  = new Vector2(0, 0);
        }

        public override void Update()
        {
            var indexedSprite = (IndexedSprite) graphics;

            // If the animation is not finished, wait TimeBetweenFrames
            // before switching to the next image of the animation.
            if(indexedSprite.Index <= lastFrame)
            {
                var curTime = DateTime.Now.Ticks;
                
                if((curTime - startTime) >= TimeBetweenFrames)
                {
                    // Tell the IndexedSprite component that it should be drawing the next frame
                    indexedSprite.Index++;
                    startTime = curTime; // Reset the time between frames
                } 
            }
            // If the animation is over mark the object as dead to (keep it from being updated and drawn)
            else if (Alive) 
            {
                Alive = false;
            }

            base.Update();
        }
    }
}
