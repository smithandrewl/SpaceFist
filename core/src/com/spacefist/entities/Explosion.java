package com.spacefist.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.components.IndexedSprite;
import com.spacefist.components.NullInputComponent;
import com.spacefist.components.NullSoundComponent;
import com.spacefist.components.Physics;

/// <summary>
/// Represents and draws an explosion at its x and y coordinates.
///
/// The Explosion instance is "killed" after the animation finishes.
/// </summary>
public class Explosion extends Entity {
    // The dimensions of the explosion
    private static final int height = 122;
    private static final int width  = 122;

    // The number of frames / pictures in the animation
    private static final int lastFrame = 9;

    // The time the explosion animation started
    // and the time to wait before drawing the next frame
    private long startTime;
    private long TimeBetweenFrames = 400000;

    /// <summary>
    /// Creates a new Explosion instance at a specified location.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    /// <param name="position">The on world location of the explosion</param>
    public Explosion(GameData gameData, Vector2 position) {
        super(gameData,
            new Rectangle(
                (int) position.x,
                (int) position.y,
                width,
                height
            ),
            new Physics(),
            new NullInputComponent(),
            new IndexedSprite(gameData.getTextures().get("Explosion"), width, height, 0),
            new NullSoundComponent(),
            gameData.getScreenScale()
        );

        startTime = System.currentTimeMillis();
        setVelocity(new Vector2(0, 0));
    }

    @Override
    public void Update() {
        IndexedSprite indexedSprite = (IndexedSprite) getGraphics();

        // If the animation is not finished, wait TimeBetweenFrames
        // before switching to the next image of the animation.
        if (indexedSprite.getIndex() <= lastFrame) {
            long curTime = System.currentTimeMillis();

            if ((curTime - startTime) >= TimeBetweenFrames) {
                // Tell the IndexedSprite component that it should be drawing the next frame
                indexedSprite.setIndex(indexedSprite.getIndex() + 1);
                startTime = curTime; // Reset the time between frames
            }
        }
        // If the animation is over mark the object as dead to (keep it from being updated and drawn)
        else if (isAlive()) {
            setAlive(false);
        }

        super.Update();
    }
}