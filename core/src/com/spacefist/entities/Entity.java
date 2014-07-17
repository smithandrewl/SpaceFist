package com.spacefist.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.components.abst.GraphicsComponent;
import com.spacefist.components.abst.InputComponent;
import com.spacefist.components.abst.PhysicsComponent;
import com.spacefist.components.abst.SoundComponent;

/**
 * The base class of all entities.  Anything that moves and is drawn in the game is
 * a subclass of the Entity class.
 *
 * Instead of performing tasks such as drawing or handling input,
 * the Entity base class keeps references to classes implementing known interfaces.  It then calls methods of these interfaces
 * such as Update and Draw.
 *
 * What this means is that the Entity base class can update and draw itself without knowning how it is being updated or drawn.
 *
 * On update, the Entity base class calls the Update method on the graphics, physics, input and sound components.
 * On draw, the Entity base class calls the Draw method on the graphics component.
 *
 * Each Entity Subclass provides the exact implementations of the components in its constructor.
 *
 * For example, The block class uses Sprite as its GraphicsComponent because it only needs to draw a single image.
 * The Explosion and Ship classes use the IndexedSprite component as its GraphicsComponent because it needs to draw
 * itself in multiple ways (each frame of the explosion or when the ship turns).
 *
 * As much as possible, the Entity class contains data about the entity, while the components operate on the data.
 */
public class Entity
{
    private GameData          gameData;
    private Rectangle         rectangle;
    private PhysicsComponent  physics;
    private InputComponent    input;
    private GraphicsComponent graphics;
    private SoundComponent    sound;

    /**
     * The direction and speed of the entity
     */
    private Vector2 velocity;

    /**
     * Whether the entity is in play or not. The default Update and Draw methods
     * only update and draw when the entity is Alive.
     */
    private boolean alive;

    /**
     * A hue which can be used by a graphics component when drawning the entity.
     */
    private Color tint;

    /**
     * This property provides an easy way to get and set the X coordinate of the rectangle associated with this entity.
     */
    public int getX() {
        return (int) rectangle.getX();
    }

    public void setX(int x) {
        rectangle.setX(x);
    }

    /**
     * This property provides an easy way to get and set the Y coordinate of the rectangle associated with this entity.
     */
    public int getY() {
        return (int) rectangle.getY();
    }

    public void setY(int y) {
        rectangle.setY(y);
    }

    /**
     * This represents the rotation of the entity.
     */
    private float rotation;

    /**
     * The location, height and width of this entity.
     */
    public Rectangle getRectangle() {
            return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public GameData getGameData() {
        return gameData;
    }

    /**
     *
     * @param gameData Common game data
     * @param rectangle The size and position of the entity
     * @param physics The physics component to use
     * @param input The input component to use
     * @param graphics The graphics component to use
     * @param sound The sound component to use
     * @param rotation The rotation of the entity
     */
    public Entity(
        GameData          gameData,
        Rectangle         rectangle,
        PhysicsComponent  physics,
        InputComponent    input,
        GraphicsComponent graphics,
        SoundComponent    sound,
        float             rotation)
    {
        setAlive(true);
        this.gameData  = gameData;
        this.rectangle = rectangle;
        this.setPhysics(physics);
        this.setInput(input);
        this.setGraphics(graphics);
        this.setSound(sound);

        setTint(Color.WHITE);
    }

    public void Initialize()
    {

    }

    /**
     * If the ship is alive, update all of its components
     */
    public void Update()
    {
        if (isAlive())
        {
            getGraphics().Update(gameData, this);
            getInput().Update(gameData, this);
            getPhysics().Update(gameData, this);
            getSound().Update(gameData, this);
        }
    }

    /**
     * If the ship is alive, call draw on its graphics component
     */
    public void Draw()
    {
        if (isAlive())
        {
            getGraphics().Draw(gameData, this);
        }
    }

    public PhysicsComponent getPhysics() {
        return physics;
    }

    public void setPhysics(PhysicsComponent physics) {
        this.physics = physics;
    }

    public InputComponent getInput() {
        return input;
    }

    public void setInput(InputComponent input) {
        this.input = input;
    }

    public GraphicsComponent getGraphics() {
        return graphics;
    }

    public void setGraphics(GraphicsComponent graphics) {
        this.graphics = graphics;
    }

    public SoundComponent getSound() {
        return sound;
    }

    public void setSound(SoundComponent sound) {
        this.sound = sound;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Color getTint() {
        return tint;
    }

    public void setTint(Color tint) {
        this.tint = tint;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}