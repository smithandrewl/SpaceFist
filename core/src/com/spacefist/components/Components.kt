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
 *
 * @param texture The texture atlas
 * @param width The width of each frame
 * @param height The height of each frame
 * @param index The index of the currently visible frame
 */
class IndexedSprite(
        private val texture: Texture,
        private val width:   Int,
        private val height:  Int,
        var index:   Int
) : GraphicsComponent {
    override fun draw(gameData: GameData, obj: Entity) {
        val spriteBatch = gameData.spriteBatch
        val shapeRenderer = gameData.shapeRenderer;


        spriteBatch.color = Color(obj.tint)

        // Calculate and draw the image at an offset (this causes the image to rotate around
        // its center and not its upper left corner
        val objRect = obj.rectangle

        val origin = Vector2(objRect!!.getWidth() / 2, objRect.getHeight()  / 2)

        val position    = Vector2(obj.x.toFloat(), obj.y.toFloat()).add(origin)
        val adjPosition = position.sub(gameData.camera)


        if(gameData.debugDrawing) {
            gameData.shapeRenderer.rect(adjPosition.x, adjPosition.y, objRect!!.width, objRect!!.height)
        }
        //gameData.shapeRenderer.rect(adjPosition.x + (origin.x * gameData.screenScale) , adjPosition.y + (origin.y * gameData.screenScale), objRect!!.width * gameData.screenScale, objRect!!.height * gameData.screenScale)

        spriteBatch.draw(
                TextureRegion(texture, width * index, 0, width, height),
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
        obj.x        = obj.x + obj.velocity!!.x.toInt()
        obj.y        = obj.y - obj.velocity!!.y.toInt()
        obj.rotation = obj.rotation % 360
    }
}

/**
 * Tells the ship to move in response to user input.
 */
class ShipInput : InputComponent {
    private var spaceDown: Boolean = false
    private var aDown:     Boolean = false
    private var dDown:     Boolean = false

    override fun update(gameData: GameData, obj: Entity) {
        val ship = obj as Ship

        val upKey    = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)
        val leftKey  = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)
        val downKey  = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)
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
 *
 * @param sound The sound to play
 */
class Sound(internal var soundEffect: com.badlogic.gdx.audio.Sound) : SoundComponent {
    var volume = 0f
    /**
     * Plays the sound.
     */
    fun play() {
        soundEffect.play(volume)
    }

    override fun update(gameData: GameData, obj: Entity) {
        volume = gameData.soundVolume
    }
}

/**
 * Sprite represents a static image at a position on-world.
 *
 * @param texture The image to draw
 */
class Sprite(private val image: Texture) : GraphicsComponent {
    override fun update(gameData: GameData, obj: Entity) {}

    override fun draw(gameData: GameData, obj: Entity) {

        if(obj.rectangle == gameData.ship.rectangle) {
            return;
        }
        val spriteBatch = gameData.spriteBatch

        val shapeRenderer = gameData.shapeRenderer

        spriteBatch.color = Color(obj.tint)

        val objRectangle = obj.rectangle

        val origin = Vector2(
                objRectangle!!.getWidth()  / 2,
                objRectangle!!.getHeight() / 2
        )

        val position = Vector2(
                obj.x.toFloat(),
                obj.y.toFloat()
        ).add(origin)

        // draw the texture at the location of the Entity obj
        val adjPos = position.sub(gameData.camera)

        if(gameData.debugDrawing) {
            gameData.shapeRenderer.rect(adjPos.x, adjPos.y, objRectangle!!.width, objRectangle!!.height)
        }
        //gameData.shapeRenderer.rect(adjPos.x + (origin.x * gameData.screenScale) , adjPos.y + (origin.y * gameData.screenScale), objRectangle!!.width * gameData.screenScale, objRectangle!!.height * gameData.screenScale)


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
