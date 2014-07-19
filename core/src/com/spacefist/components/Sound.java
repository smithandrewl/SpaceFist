package com.spacefist.components;

import com.spacefist.GameData;
import com.spacefist.components.abst.SoundComponent;
import com.spacefist.entities.Entity;

    /// <summary>
    /// A simple sound component that plays a sound effect.
    /// </summary>
    public class Sound implements SoundComponent {
        com.badlogic.gdx.audio.Sound soundEffect;

        /// <summary>
        /// Creates a new Sound instance from a SoundEffect.
        /// </summary>
        /// <param name="sound">The sound to play</param>
        public Sound(com.badlogic.gdx.audio.Sound sound)
        {
            this.soundEffect = sound;
        }
        
        /// <summary>
        /// Plays the sound.
        /// </summary>
        public void play()
        {
            soundEffect.play();
        }

        public void Update(GameData gameData, Entity obj)
        {
        }
    }