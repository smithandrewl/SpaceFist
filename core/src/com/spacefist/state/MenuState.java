package com.spacefist.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.TimeUtils;
import com.spacefist.GameData;
import com.spacefist.state.abst.GameState;

import java.util.HashMap;

/**
 * This state displays the main menu and handles input to
 * switch to several other states.
 */
public class MenuState implements GameState {
    private GameData  gameData;
    private long      enteredAt;
    // private Texture   background;
    // private Rectangle backgroundRect;

    public static final int BUTTON_PADDING = 3;
    public static final int BUTTON_WIDTH = 200;

    private Skin  skin;
    private Stage stage;

    public MenuState(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void loadContent() {

        // backgroundRect = gameData.getResolution();

        ///HashMap<String, Texture> textures = gameData.getTextures();

        // background     = textures.get("BackgroundRed");
    }

    @Override
    public void draw() {
        //TODO: Use scene.ui to render the background image

        stage.draw();
    }

    @Override
    public void update() {

        boolean safeToProcessInput = (TimeUtils.millis() - enteredAt) > 300;

        if (safeToProcessInput) {
            if (Gdx.input.isKeyPressed(Keys.ENTER)) {

                gameData.getInPlayState().loadContent();
                gameData.setCurrentState(gameData.getInPlayState());

            } else if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
                Gdx.app.exit();
            }
        }

        stage.act();
    }

    @Override
    public void enteringState() {
        enteredAt = TimeUtils.millis();

        gameData.getSongs().get("TitleScreen").setLooping(true);
        gameData.getSongs().get("TitleScreen").play();

        gameData.getLevelManager().Init();
        gameData.getLevelManager().LoadLevel(1);


        stage = new Stage();
        skin = new Skin(Gdx.files.internal("images/ui/uiskin.json"));

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        final TextButton newGame = new TextButton("New Game", skin);
        final TextButton credits = new TextButton("Credits", skin);
        final TextButton exit    = new TextButton("Exit", skin);

        table.add(newGame).width(BUTTON_WIDTH).pad(BUTTON_PADDING);
        table.row();
        table.add(credits).width(BUTTON_WIDTH).pad(BUTTON_PADDING);
        table.row();
        table.add(exit).width(BUTTON_WIDTH).pad(BUTTON_PADDING);

        newGame.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                gameData.getInPlayState().loadContent();
                gameData.setCurrentState(gameData.getInPlayState());
            }
        });

        credits.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameData.setCurrentState(gameData.getCreditsState());
            }
        });

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void exitingState() {
        gameData.getSongs().get("TitleScreen").stop();
        stage.dispose();
    }
}
