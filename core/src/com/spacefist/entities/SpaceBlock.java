package com.spacefist.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.components.NullInputComponent;
import com.spacefist.components.Physics;
import com.spacefist.components.Sound;
import com.spacefist.components.Sprite;

/// <summary>
/// Represents a floating block or crate.
/// </summary>
public class SpaceBlock extends Entity {
    // Dimensions of the block
    private static final int Width  = 80;
    private static final int Height = 60;

    // The sound made when the ship hits a block
    private Sound thump;

    /// <summary>
    /// Creates a new SpaceBlock instance with a specified position and velocity.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    /// <param name="texture">The image for the block</param>
    /// <param name="position">The position of the block in the game world</param>
    /// <param name="velocity">The velocity of the block</param>
    public SpaceBlock(GameData gameData, Texture texture, Vector2 position, Vector2 velocity) {
        super(gameData,
            new Rectangle(
                (int) position.x,
                (int) position.y,
                Width,
                Height
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

    // Called when the block has been shot
    public void Destroy() {
        Sound sound = (Sound) getSound();

        sound.play();
        setAlive(false);
    }

    // Called when the block has been bumped by the ship
    public void Thump() {
        thump.play();
        setAlive(false);
    }
}
