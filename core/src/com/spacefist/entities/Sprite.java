package com.spacefist.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.components.abst.GraphicsComponent;
import com.spacefist.entities.Entity;

/// <summary>
/// Sprite represents a static image at a position on-world.
/// </summary>
public class Sprite implements GraphicsComponent {
    private Texture image;

    /// <summary>
    /// Creates a new Sprite instance from a texture
    /// </summary>
    /// <param name="texture">The image to draw</param>
    public Sprite(Texture texture) {
        image = texture;
    }

    public void Update(GameData gameData, Entity obj) {

    }

    public void Draw(GameData gameData, Entity obj) {
        SpriteBatch spriteBatch = gameData.getSpriteBatch();

        Vector2 origin   = new Vector2(obj.getRectangle().getWidth() / 2, obj.getRectangle().getHeight() / 2);
        Vector2 position = new Vector2(obj.getX(), obj.getY()).add(origin);


        // Draw the texture at the location of the Entity obj
        Vector2 adjPos = position.sub(gameData.getCamera());

        spriteBatch.draw(
                new TextureRegion(image),
                adjPos.x,
                adjPos.y,
                origin.x,
                origin.y,
                obj.getRectangle().getWidth(),
                obj.getRectangle().getHeight(),
                gameData.getScreenScale(),
                gameData.getScreenScale(),
                obj.getRotation(),
                true
        );

                // TODO: add tinting
                // obj.Tint
    }
}
