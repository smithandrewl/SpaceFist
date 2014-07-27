package com.spacefist.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.PickupHandler;
import com.spacefist.components.NullInputComponent;
import com.spacefist.components.Physics;
import com.spacefist.components.Sound;
import com.spacefist.components.Sprite;

/// <summary>
/// Represents a pickup item which runs a function when it collides with the players ship.
/// </summary>
public class Pickup extends Entity {
    private Sound         pickupSound;
    private PickupHandler pickupHandler;

    /// <summary>
    /// Creates a new Pickup instance which runs a function
    /// when it is picked up by the player.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    /// <param name="texture">The image of the pickup</param>
    /// <param name="sound">The sound to play when picked up</param>
    /// <param name="position">The initial location of the pickup</param>
    /// <param name="velocity">The initial velocity of the pickup</param>
    /// <param name="pickupHandler">The function to run on pickup</param>
    public Pickup(
        GameData      gameData,
        Texture       texture,
        com.badlogic.gdx.audio.Sound sound,
        Vector2       position,
        Vector2       velocity,
        PickupHandler pickupHandler
    ) {
        super(
            gameData,
            new Rectangle(
                (int) position.x,
                (int) position.y,
                texture.getWidth(),
                texture.getHeight()
            ),
            new Physics(),
            new NullInputComponent(),
            new Sprite(texture),
            new Sound(sound),
            gameData.getScreenScale()
        );

        pickupSound        = new Sound(sound);
        this.pickupHandler = pickupHandler;

        setVelocity(velocity);
    }

    // PickedUp calls pickupHandler when the pickup has been "picked up"
    // The pickup handler returns true if the pickup should be removed
    public boolean PickedUp(Ship ship) {
        boolean pickedUp = pickupHandler.handle(ship);

        if (pickedUp) {
            pickupSound.play();
        }

        return pickedUp;
    }
}
