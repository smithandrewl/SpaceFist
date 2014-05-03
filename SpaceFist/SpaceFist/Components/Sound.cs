using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;

using SpaceFist.Components.Abstract;

namespace SpaceFist.Components
{
    /// <summary>
    /// A simple sound component that plays a sound effect.
    /// </summary>
    class Sound : SoundComponent
    {
        SoundEffect soundEffect;

        /// <summary>
        /// Creates a new Sound instance from a SoundEffect.
        /// </summary>
        /// <param name="sound">The sound to play</param>
        public Sound(SoundEffect sound)
        {
            this.soundEffect = sound;
        }
        
        /// <summary>
        /// Plays the sound.
        /// </summary>
        public void play()
        {
            soundEffect.Play();
        }

        public void Update(Game game, Entity obj)
        {
        }
    }
}
