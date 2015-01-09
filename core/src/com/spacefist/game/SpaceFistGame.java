package com.spacefist.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.spacefist.GameData;
import com.spacefist.state.*;
import com.spacefist.state.abst.GameState;

import java.util.HashMap;
import java.util.Map;

public class SpaceFistGame extends ApplicationAdapter {
    private static final String SPRITE_FONT_ASSET = "Fonts/Raised";
    private static final String TITLE_FONT_ASSET  = "Fonts/Title";
    SpriteBatch batch;
    private GameData gameData;

    @Override
    public void create() {
        batch = new SpriteBatch();

        gameData = new GameData(this);

        // Creates the game states when the game starts.
        // only one state is active at any given time.
        gameData.setSplashScreenState(new SplashScreenState(gameData));
        gameData.setMenuState(new MenuState(gameData));
        gameData.setLogoState(new LogoState(gameData));
        gameData.setInPlayState(new InPlayState(gameData));
        gameData.setGameOverState(new GameOverState(gameData));
        gameData.setCreditsState(new CreditsState(gameData));
        gameData.setEndOfLevelState(new EndOfLevelState(gameData));
        gameData.setEndOfGameState(new EndOfGameState(gameData));

        gameData.setCurrentState(gameData.getLogoState());

        loadContent();
    }

    @Override
    public void render() {
        // Tell the current state to update itself
        GameState currentState = gameData.getCurrentState();

        currentState.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // Tell the current state to draw itself
        currentState.draw();
        batch.end();
    }

    @Override
    public void dispose() {
        gameData.dispose();
    }

    public GameData getGameData() {
        return gameData;
    }

    private void loadTextures() {
        Map<String, Texture> textures = gameData.getTextures();

        FileHandle directory = Gdx.files.absolute("images/");
        for (FileHandle fileHandle : directory.list()) {
            if (fileHandle.isDirectory()) {
                for (FileHandle file : fileHandle.list(".png")) {
                    textures.put(file.nameWithoutExtension(), new Texture(file));
                }
            }
        }
    }

    private void loadSongs() {
        assert gameData.getSongs() != null;

        Map<String, Music> songs = gameData.getSongs();
        FileHandle directory = Gdx.files.absolute("sound/songs/");

        for (FileHandle fileHandle : directory.list()) {
            songs.put(fileHandle.nameWithoutExtension(), Gdx.audio.newMusic(fileHandle));
        }
    }

    private void loadSoundEffects() {
        assert gameData.getSoundEffects() != null;

        Map<String, Sound> soundEffects = gameData.getSoundEffects();
        FileHandle directory = Gdx.files.absolute("sound/soundeffects/");

        for (FileHandle fileHandle : directory.list()) {
            soundEffects.put(fileHandle.nameWithoutExtension(), Gdx.audio.newSound(fileHandle));
        }
    }

    protected void loadContent() {
        int height = Gdx.graphics.getHeight();
        int width = Gdx.graphics.getWidth();

        gameData.setResolution(new Rectangle(0, 0, width, height));
        gameData.setScreenScale(0.5f);

        // Create a new SpriteBatch, which can be used to draw textures.
        gameData.setSpriteBatch(batch);

        // ----------------------------- Load the games assets -----------
        gameData.setFont(new BitmapFont(Gdx.files.absolute("fonts/font.fnt")));

        //GameData.TitleFont = Content.Load<SpriteFont>(TITLE_FONT_ASSET);

        loadTextures();
        loadSongs();
        loadSoundEffects();


        // GameData.InPlayState.loadContent();
        gameData.getGameOverState().loadContent();

        gameData.getSplashScreenState().loadContent();
        gameData.getMenuState().loadContent();
        gameData.getLogoState().loadContent();
        gameData.getEndOfLevelState().loadContent();

        gameData.getCurrentState().enteringState();
    }
}
