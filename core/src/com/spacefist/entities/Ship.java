package com.spacefist.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.components.IndexedSprite;
import com.spacefist.components.Physics;
import com.spacefist.components.ShipInput;
import com.spacefist.components.Sound;
import com.spacefist.state.abst.ShipState;
import com.spacefist.state.abst.StateMachine;
import com.spacefist.state.shipstates.NormalState;
import com.spacefist.state.shipstates.SpawningState;
import com.spacefist.weapons.LaserWeapon;
import com.spacefist.weapons.abst.Weapon;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class Ship extends Entity implements StateMachine<ShipState> {
    private static final int MAX_HEALTH_POINTS = 100;

    // The dimensions of the ship
    private static final int WIDTH  = 60;
    private static final int HEIGHT = 133;

    // The frame of the sprite sheet (ShipSheet.png) to draw
    // Frame 0 is the ship turning left
    // Frame 4 is the ship in its normal state
    // Frame 7 is the ship turning right
    private static final int LEFT_INDEX    = 0;
    private static final int AT_REST_INDEX = 4;
    private static final int RIGHT_INDEX   = 7;
    private static final int MAX_VELOCITY  = 20;

    /**
     * The current behavior of the ship
     *
     * The current states are spawning, normal and low health
     */
    private ShipState state;
    private Weapon    weapon;

    /**
     * The number of health points the ship has left.
     */
    private int healthPoints;

    private IndexedSprite indexedSprite;

    // The predefined velocities to use when changing position
    private Vector2 LeftVelocity     = new Vector2(-1, 0);
    private Vector2 RightVelocity    = new Vector2(1, 0);
    private Vector2 ForwardVelocity  = new Vector2(0, -1);
    private Vector2 BackwardVelocity = new Vector2(0, 1);

    /**
     * Creates a new Ship instance at the specified location.
     *
     * @param gameData Common game data
     * @param position The location of the ship in the game world
     */
    public Ship(GameData gameData, Vector2 position) {
        super(
            gameData,
            new Rectangle(
                (int) position.x,
                (int) position.y,
                    WIDTH,
                    HEIGHT
            ),
            new Physics(),
            new ShipInput(),
            new IndexedSprite(gameData.getTextures().get("ShipSheet"), WIDTH, HEIGHT, 4),
            new Sound(gameData.getSoundEffects().get("PlayerDeath")),
            gameData.getScreenScale()
        );

        indexedSprite = (IndexedSprite) getGraphics();

        healthPoints = 100;
        weapon       = new LaserWeapon(gameData);

        // Start the ship in the spawning state
        state = new SpawningState(gameData);
    }

    @Override
    public ShipState getCurrentState() {
        return state;
    }

    @Override
    public void setCurrentState(ShipState shipState) {
        state.exitingState();
        shipState.enteringState();
        state = shipState;
    }

    /**
     * @return The ships current weapon.
     */
    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public float getHealth() {
        return healthPoints / (float) MAX_HEALTH_POINTS;
    }

    /**
     * Plays the ships destruction sound effect.
     */
    public void onDeath() {
        ((Sound) getSound()).play();
    }

    /**
     * Resets the ship to the default state (drawn normally (no flashing or fading)).
     */
    public void resetState() {
        setCurrentState(new NormalState(gameData));
        reset();
    }

    /**
     * Updates the ship
     */
    @Override
    public void update() {
        state.update();
        super.update();
    }

    /**
     * Fire the active weapon
     */
    public void fire() {
        weapon.fire();
    }

    public void reset() {
        // This causes the ship to be drawn in its default state (not turning left or right)
        indexedSprite.setIndex(AT_REST_INDEX);
    }

    /**
     * Changes the velocity by "velocity" and then clamps the value between the minimum and maximum velocities
     * allowed.
     */
    private void incrementVelocity(Vector2 velocity) {
        float xVel = clamp(
            getVelocity().x + velocity.x,
            -MAX_VELOCITY,
            MAX_VELOCITY
        );

        float yVel = clamp(
            getVelocity().y + velocity.y,
            -MAX_VELOCITY,
            MAX_VELOCITY
        );

        setVelocity(new Vector2(xVel, yVel));
    }

    /**
     * Causes the ship to move to the left.
     */
    public void left() {
        // This tells indexedSprite to draw the ship turning left
        indexedSprite.setIndex(LEFT_INDEX);

        // Change to a left moving velocity
        incrementVelocity(LeftVelocity);
    }

    /**
     * Causes the ship to move to the right.
     */
    public void right() {
        // This tells indexedSprite to draw the ship turning right
        indexedSprite.setIndex(RIGHT_INDEX);

        // Change to a right moving velocity
        incrementVelocity(RightVelocity);
    }

    /**
     * Causes the ship to move forward
     */
    public void forward() {
        // This tells indexedSprite to draw the ship normally (not turning)
        indexedSprite.setIndex(AT_REST_INDEX);

        // Change to a forward moving velocity
        incrementVelocity(ForwardVelocity);
    }

    public void backward() {
        // This tells indexedSprite to draw the ship normally (not turning)
        indexedSprite.setIndex(AT_REST_INDEX);

        // Change to a backwards moving velocity
        incrementVelocity(BackwardVelocity);
    }
}
