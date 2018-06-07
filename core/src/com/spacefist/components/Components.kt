package com.spacefist.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.spacefist.GameData
import com.spacefist.components.abst.GraphicsComponent
import com.spacefist.components.abst.InputComponent
import com.spacefist.components.abst.PhysicsComponent
import com.spacefist.components.abst.SoundComponent
import com.spacefist.entities.Entity
import com.spacefist.entities.Ship

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
                objRect!!.getWidth() / 2,
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

/**
 * For use by entities that do not use input.
 */
class NullInputComponent : InputComponent {
    override fun update(gameData: GameData, obj: Entity) {}
}
/**
 * For use by entities that do not have a sound
 * The background for example
 */
class NullSoundComponent : SoundComponent {
    fun loadContent(gameData: GameData) {}
    override fun update(gameData: GameData, obj: Entity) {}
}

/**
 * Updates The game objects position using its properties.
 */
class Physics : PhysicsComponent {
    override fun update(gameData: GameData, obj: Entity) {
        obj.x = obj.x + obj.velocity!!.x.toInt()
        obj.y = obj.y - obj.velocity!!.y.toInt()
        obj.rotation = obj.rotation % 360
    }
}

/**
 * Tells the ship to move in response to user input.
 */
class ShipInput : InputComponent {
    private var spaceDown: Boolean = false
    private var aDown: Boolean = false
    private var dDown: Boolean = false

    override fun update(gameData: GameData, obj: Entity) {
        val ship = obj as Ship

        val upKey = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)
        val leftKey = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)
        val downKey = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)
        val rightKey = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)

        val space = Gdx.input.isKeyPressed(Input.Keys.SPACE)

        if (upKey) {
            ship.forward()
        }

        if (!leftKey && aDown) {
            aDown = false
            ship.reset()
        }

        if (leftKey) {
            aDown = true
            ship.left()
        }

        if (!rightKey && dDown) {
            dDown = false
            ship.reset()
        }

        if (rightKey) {
            dDown = true
            ship.right()
        }

        if (downKey) {
            ship.backward()
        }

        if (space) {
            if (!spaceDown) {
                spaceDown = true
                ship.fire()
            }
        }

        if (!space) {
            spaceDown = false
        }
    }
}

/**
 * A simple sound component that plays a sound effect.
 */
class Sound
/**
 * Creates a new Sound instance from a SoundEffect.
 *
 * @param sound The sound to play
 */
(internal var soundEffect: com.badlogic.gdx.audio.Sound) : SoundComponent {

    /**
     * Plays the sound.
     */
    fun play() {
        soundEffect.play()
    }

    override fun update(gameData: GameData, obj: Entity) {}
}



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
                objRectangle!!.getWidth() / 2,
                objRectangle!!.getHeight() / 2
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
                objRectangle!!.getWidth(),
                objRectangle.getHeight(),
                gameData.screenScale,
                gameData.screenScale,
                obj.rotation % 360
        )

        spriteBatch.color = Color(Color.WHITE)
        spriteBatch.flush()
    }
}
