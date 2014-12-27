package com.spacefist.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.components.abst.GraphicsComponent;
import com.spacefist.entities.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * Sprite represents a static image at a position on-world.
 */
public class Sprite implements GraphicsComponent {
    private Texture image;

    /**
     * Creates a new Sprite instance from a texture
     *
     * @param texture The image to draw
    */
    public Sprite(Texture texture) {
        image = texture;
    }

    @Override
    public void update(GameData gameData, Entity obj) {

    }

    @Override
    public void draw(@NotNull GameData gameData, @NotNull Entity obj) {
        SpriteBatch spriteBatch  = gameData.getSpriteBatch();
        spriteBatch.setColor(new Color(obj.getTint()));

        Rectangle   objRectangle = obj.getRectangle();

        Vector2 origin = new Vector2(
            objRectangle.getWidth()  / 2,
            objRectangle.getHeight() / 2
         );

        Vector2 position = new Vector2(
            obj.getX(),
            obj.getY()
        ).add(origin);

        // Draw the texture at the location of the Entity obj
        Vector2 adjPos = position.sub(gameData.getCamera());

        spriteBatch.draw(
                new TextureRegion(image),
                adjPos.x,
                adjPos.y,
                origin.x,
                origin.y,
                objRectangle.getWidth(),
                objRectangle.getHeight(),
                gameData.getScreenScale(),
                gameData.getScreenScale(),
                obj.getRotation()
        );

        spriteBatch.setColor(new Color(Color.WHITE));
        spriteBatch.flush();
    }
}
