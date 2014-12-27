package com.spacefist.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.components.NullInputComponent;
import com.spacefist.components.Physics;
import com.spacefist.components.Sound;
import com.spacefist.components.Sprite;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a floating block or crate.
 */
public class SpaceBlock extends Entity {
    // Dimensions of the block
    private static final int WIDTH  = 80;
    private static final int HEIGHT = 60;

    // The sound made when the ship hits a block
    private Sound thump;
    /**
     * Creates a new SpaceBlock instance with a specified position and velocity.
     *
     * @param gameData Common game data
     * @param texture The image for the block
     * @param position The position of the block in the game world
     * @param velocity The velocity of the block
     */
    public SpaceBlock(@NotNull GameData gameData, Texture texture, @NotNull Vector2 position, Vector2 velocity) {
        super(gameData,
            new Rectangle(
                (int) position.x,
                (int) position.y,
                WIDTH,
                HEIGHT
            ),
            new Physics(),
            new NullInputComponent(),
            new Sprite(texture),
            new Sound(gameData.getSoundEffects().get("Explosion")),
            gameData.getScreenScale()
        );

        thump = new Sound(gameData.getSoundEffects().get("Explosion"));

        setVelocity(velocity);
    }

    /**
     * Called when the block has been shot
     */
    public void destroy() {
        Sound sound = (Sound) getSound();

        sound.play();
        setAlive(false);
    }

    /**
     * Called when the block has been bumped by the ship
    */
    public void thump() {
        thump.play();
        setAlive(false);
    }
}
