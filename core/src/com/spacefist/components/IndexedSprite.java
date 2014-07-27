package com.spacefist.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.components.abst.GraphicsComponent;
import com.spacefist.entities.Entity;

// Given a texture and a frame width and height,
// this component will draw a portion of the texture.
//
// Index is used to determine which frame of the image is being drawn.
// This is used by Ship to draw itself turning. The image contains all of the images of the ship.
// When the ship turns, it sets the Index property of its graphics component to draw the correct frame.
//
// Explosion.java also uses IndexedSprite as its graphics component to draw an animation
public class IndexedSprite implements GraphicsComponent {
    private Texture texture;
    private int     width;
    private int     height;
    private int     index;

    /// <summary>
    /// Provides the ability to draw the cells of a horizontal texture atlas.
    /// </summary>
    /// <param name="texture">The texture atlas</param>
    /// <param name="width">The width of each frame</param>
    /// <param name="height">The height of each frame</param>
    /// <param name="index">The index of the currently visible frame</param>
    public IndexedSprite(Texture texture, int width, int height, int index) {
        this.width   = width;
        this.height  = height;
        this.index   = index;
        this.texture = texture;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void Draw(GameData gameData, Entity obj) {
        // Calculate the portion of the texture to draw given the current index
        Rectangle   sourceRect  = new Rectangle(index * width, 0, width, height);
        SpriteBatch spriteBatch = gameData.getSpriteBatch();

        // Calculate and draw the image at an offset (this causes the image to rotate around
        // its center and not its upper left corner
        Vector2 origin   = new Vector2(
            obj.getRectangle().getWidth()  / 2,
            obj.getRectangle().getHeight() / 2
        );

        Vector2 position    = new Vector2(obj.getX(), obj.getY()).add(origin);
        Vector2 adjPosition = position.sub(gameData.getCamera());

        spriteBatch.draw(
                new TextureRegion(texture),
                adjPosition.x,
                adjPosition.y,
                origin.x,
                origin.y,
                obj.getRectangle().getWidth(),
                obj.getRectangle().getHeight(),
                gameData.getScreenScale(),
                gameData.getScreenScale(),
                obj.getRotation(),
                true
        );

        // TODO: Add object tinting
        // obj.Tint,
    }

    public void update(GameData gameData, Entity obj) { }
}

