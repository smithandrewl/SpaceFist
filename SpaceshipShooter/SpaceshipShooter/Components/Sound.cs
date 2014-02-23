using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;

using SpaceshipShooter.Components.Abstract;

namespace SpaceshipShooter.Components
{
    class Sound : SoundComponent
    {
        SoundEffect soundEffect;

        public Sound(SoundEffect sound)
        {
            this.soundEffect = sound;
        }
        
        public void play()
        {
            soundEffect.Play();
        }

        public void Update(Game game, GameObject obj, Microsoft.Xna.Framework.GameTime time)
        {
        }
    }
}
