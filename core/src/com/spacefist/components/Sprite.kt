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
 * Sprite represents a static image at a position on-world.
 */
class Sprite
/**
 * Creates a new Sprite instance from a texture
 *
 * @param texture The image to draw
 */
(private val image: Texture) : GraphicsComponent {

    override fun update(gameData: GameData, obj: Entity) {

    }

    override fun draw(gameData: GameData, obj: Entity) {
        val spriteBatch = gameData.spriteBatch
        spriteBatch.color = Color(obj.tint)

        val objRectangle = obj.rectangle

        val origin = Vector2(
                objRectangle.getWidth() / 2,
                objRectangle.getHeight() / 2
        )

        val position = Vector2(
                obj.x.toFloat(),
                obj.y.toFloat()
        ).add(origin)

        // draw the texture at the location of the Entity obj
        val adjPos = position.sub(gameData.camera)

        spriteBatch.draw(
                TextureRegion(image),
                adjPos.x,
                adjPos.y,
                origin.x,
                origin.y,
                objRectangle.getWidth(),
                objRectangle.getHeight(),
                gameData.screenScale,
                gameData.screenScale,
                obj.rotation % 360
        )

        spriteBatch.color = Color(Color.WHITE)
        spriteBatch.flush()
    }
}
