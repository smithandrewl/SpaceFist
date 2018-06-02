package com.spacefist.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.components.abst.GraphicsComponent
import com.spacefist.entities.Entity

/**
 * Given a texture and a frame width and height,
 * this component will draw a portion of the texture.
 *
 * Index is used to determine which frame of the image is being drawn.
 * This is used by Ship to draw itself turning. The image contains all of the images of the ship.
 * When the ship turns, it sets the Index property of its graphics component to draw the correct frame.
 *
 * Explosion.java also uses IndexedSprite as its graphics component to draw an animation
 */
class IndexedSprite
/**
 * Provides the ability to draw the cells of a horizontal texture atlas.
 *
 * @param texture The texture atlas
 * @param width The width of each frame
 * @param height The height of each frame
 * @param index The index of the currently visible frame
 */
(private val texture: Texture, private val width: Int, private val height: Int, var index: Int) : GraphicsComponent {

    override fun draw(gameData: GameData, obj: Entity) {

        val spriteBatch = gameData.spriteBatch
        spriteBatch.color = Color(obj.tint)

        // Calculate and draw the image at an offset (this causes the image to rotate around
        // its center and not its upper left corner
        val objRect = obj.rectangle

        val origin = Vector2(
                objRect.getWidth() / 2,
                objRect.getHeight() / 2
        )

        val position = Vector2(obj.x.toFloat(), obj.y.toFloat()).add(origin)
        val adjPosition = position.sub(gameData.camera)


        spriteBatch.draw(
                TextureRegion(texture, width * index, 0, width, height),
                //new TextureRegion(texture),
                adjPosition.x,
                adjPosition.y,
                origin.x,
                origin.y,
                objRect.getWidth(),
                objRect.getHeight(),
                gameData.screenScale,
                gameData.screenScale,
                obj.rotation
        )

        // FIXME: draw objects with their tint
        // obj.Tint,
        //spriteBatch.setColor(new Color(Color.WHITE));
        spriteBatch.flush()
    }

    override fun update(gameData: GameData, obj: Entity) {}
}

