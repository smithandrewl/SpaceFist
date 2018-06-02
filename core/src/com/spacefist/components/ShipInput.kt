package com.spacefist.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.spacefist.GameData
import com.spacefist.components.abst.InputComponent
import com.spacefist.entities.Entity
import com.spacefist.entities.Ship

/**
 * Tells the ship to move in response to user input.
 */
class ShipInput : InputComponent {
    private var spaceDown: Boolean = false
    private var aDown: Boolean = false
    private var dDown: Boolean = false

    override fun update(gameData: GameData, obj: Entity) {
        val ship = obj as Ship

        val upKey = Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)
        val leftKey = Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)
        val downKey = Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)
        val rightKey = Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)

        val space = Gdx.input.isKeyPressed(Keys.SPACE)

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
