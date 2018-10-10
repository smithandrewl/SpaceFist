package com.spacefist.state

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.TimeUtils
import com.spacefist.GameData
import com.spacefist.state.abst.GameState

/**
 * This state displays the main menu and handles input to
 * switch to several other states.
 */
class MenuState(private val gameData: GameData) : GameState {
    private var enteredAt: Long = 0

    private var skin: Skin? = null
    private var stage: Stage? = null

    override fun loadContent() {

        // backgroundRect = gameData.getResolution();

        ///HashMap<String, Texture> textures = gameData.getTextures();

        // background     = textures.get("BackgroundRed");
    }

    override fun draw() {
        //TODO: Use scene.ui to render the background image

        stage!!.draw()
    }

    override fun update() {

        val safeToProcessInput = TimeUtils.millis() - enteredAt > 300

        if (safeToProcessInput) {
            if (Gdx.input.isKeyPressed(Keys.ENTER)) {

                gameData.inPlayState.loadContent()
                gameData.currentState = gameData.inPlayState

            } else if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
                Gdx.app.exit()
            }
        }

        stage!!.act()
    }

    override fun enteringState() {
        enteredAt = TimeUtils.millis()

        gameData.songs["TitleScreen"]!!.setLooping(true)
        gameData.songs["TitleScreen"]!!.play()

        gameData.levelManager.init()
        gameData.levelManager.loadLevel(1)


        stage = Stage()
        skin = Skin(Gdx.files.internal("images/ui/uiskin.json"))

        Gdx.input.inputProcessor = stage

        val table = Table()
        table.setFillParent(true)
        stage!!.addActor(table)

        val newGame = TextButton("New Game", skin!!)
        val exit = TextButton("Exit", skin!!)

        table.add(newGame).width(BUTTON_WIDTH.toFloat()).pad(BUTTON_PADDING.toFloat())
        table.row()
        table.add(exit).width(BUTTON_WIDTH.toFloat()).pad(BUTTON_PADDING.toFloat())

        newGame.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                gameData.inPlayState.loadContent()
                gameData.setCurrentState(gameData.inPlayState)
            }
        })

        exit.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                Gdx.app.exit()
            }
        })
    }

    override fun exitingState() {
        gameData.songs["TitleScreen"]!!.stop()
        stage!!.dispose()
    }

    companion object {
        // private Texture   background;
        // private Rectangle backgroundRect;

        val BUTTON_PADDING = 3
        val BUTTON_WIDTH = 200
    }
}
