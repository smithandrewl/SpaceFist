package com.spacefist.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.components.IndexedSprite;
import com.spacefist.components.NullInputComponent;
import com.spacefist.components.NullSoundComponent;
import com.spacefist.components.Physics;
import org.jetbrains.annotations.NotNull;

/**
 * Represents and draws an explosion at its x and y coordinates.
 *
 * The Explosion instance is "killed" after the animation finishes.
 **/
public class Explosion extends Entity {
    // The dimensions of the explosion
    private static final int HEIGHT = 122;
    private static final int WIDTH  = 122;

    // The number of frames / pictures in the animation
    private static final int LAST_FRAME = 9;

    // The time the explosion animation started
    // and the time to wait before drawing the next frame
    private long startTime;
    private long TimeBetweenFrames = 400000;

    /**
     * Creates a new Explosion instance at a specified location.
     *
     * @param gameData Common game data
     * @param position The on world location of the explosion</param>
    */
    public Explosion(@NotNull GameData gameData, @NotNull Vector2 position) {
        super(gameData,
            new Rectangle(
                (int) position.x,
                (int) position.y,
                WIDTH,
                HEIGHT
            ),
            new Physics(),
            new NullInputComponent(),
            new IndexedSprite(gameData.getTextures().get("Explosion"), WIDTH, HEIGHT, 0),
            new NullSoundComponent(),
            gameData.getScreenScale()
        );

        startTime = System.currentTimeMillis();
        setVelocity(new Vector2(0, 0));
    }

    @Override
    public void update() {
        IndexedSprite indexedSprite = (IndexedSprite) getGraphics();

        // If the animation is not finished, wait TimeBetweenFrames
        // before switching to the next image of the animation.
        if (indexedSprite.getIndex() <= LAST_FRAME) {
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

        super.update();
    }
}