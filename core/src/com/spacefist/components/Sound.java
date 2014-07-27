package com.spacefist.components;

import com.spacefist.GameData;
import com.spacefist.components.abst.SoundComponent;
import com.spacefist.entities.Entity;

/**
 * A simple sound component that plays a sound effect.
 */
public class Sound implements SoundComponent {
    com.badlogic.gdx.audio.Sound soundEffect;

    /**
     * Creates a new Sound instance from a SoundEffect.
     *
     * @param sound The sound to play
     */
    public Sound(com.badlogic.gdx.audio.Sound sound) {
        this.soundEffect = sound;
    }

    /**
     * Plays the sound.
     */
    public void play() {
        soundEffect.play();
    }

    public void update(GameData gameData, Entity obj) { }
}