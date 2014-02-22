using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Project2.Components;

namespace Project2.Entities
{
    public class SpaceBlock : GameObject
    {
        private const int Width  = 80;
        private const int Height = 60;

        private const String ImageAsset          = @"Images\Sprites\Block";
        private const String ExplosionSoundAsset = @"Sound\explosion";
        private const String ThumpSoundAsset     = @"Sound\thump";

        private Sound thump;

        public SpaceBlock(Game game, Vector2 position, Vector2 velocity) : 
            base(game, 
                 new Rectangle((int) position.X, (int) position.Y, Width, Height), 
                 new Physics(), 
                 new NullInputComponent(), 
                 new Sprite(ImageAsset),
                 new Sound(ExplosionSoundAsset),
                 game.ScreenScale)
        {
            thump = new Sound(ThumpSoundAsset);

            Velocity = velocity;
        }

        public override void LoadContent()
        {
            thump.LoadContent(game);
            base.LoadContent();
        }

        public void Destroy()
        {
            var sound = (Sound) this.sound;

            sound.play();
            Alive = false;
        }

        public void Thump()
        {
            thump.play();
            Alive = false;
        }
    }
}
