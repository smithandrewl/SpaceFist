using Microsoft.Xna.Framework;
using SpaceFist.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Entities
{
    // Represents an explosion
    // Draws an animation using an IndexedSprite and sprite sheet
    // The object is killed after the animation finishes
    class Explosion : Entity
    {
        private const int height = 122;
        private const int width  = 122;
        private const int lastFrame = 9;
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
            Velocity = new Vector2(0, 0);
        }

        public override void Update()
        {
            var indexedSprite = (IndexedSprite) graphics;

            if(indexedSprite.Index <= lastFrame)
            {
                var curTime = DateTime.Now.Ticks;
                
                if( (curTime - startTime) >= TimeBetweenFrames)
                {
                    indexedSprite.Index++;
                    startTime = curTime;
                } 
            }
            else if(Alive)
            {
                Alive = false;
            }

            base.Update();
        }
    }
}
