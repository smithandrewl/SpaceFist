package com.spacefist.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.spacefist.GameData;
import com.spacefist.state.LogoState;
import com.spacefist.state.MenuState;
import com.spacefist.state.SplashScreenState;

import java.util.Map;

public class SpaceFistGame extends ApplicationAdapter {
    SpriteBatch batch;

    private static final String SpriteFontAsset = "Fonts/Raised";
    private static final String TitleFontAsset  = "Fonts/Title";

    private GameData gameData;

    @Override
    public void create () {
        batch = new SpriteBatch();

        gameData = new GameData(this);

        // Creates the game states when the game starts.
        // only one state is active at any given time.
        gameData.setSplashScreenState(new SplashScreenState(gameData));
        gameData.setMenuState(new MenuState(gameData));
        gameData.setLogoState(new LogoState(gameData));


        //gameData.setInPlayState(new InPlayState(GameData));
        /*
        GameData.GameOverState      = new GameOverState(GameData);
        */

        /*
        GameData.CreditsState       = new CreditsState(GameData);
        GameData.EndOfLevelState    = new EndOfLevelState(GameData);
        GameData.EndOfGameState     = new EndOfGameState(GameData);
        graphics          = new GraphicsDeviceManager(this);

        // Set the first state of the game to be displaying the splash screen.
        */

        gameData.setCurrentState(gameData.getLogoState());

        loadContent();
    }

    @Override
    public void render () {
        // Tell the current state to update itself
        gameData.getCurrentState().Update();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // Tell the current state to draw itself
        gameData.getCurrentState().Draw();
        batch.end();
    }

    @Override
    public void dispose() {
        for(Texture texture : gameData.getTextures().values()) {
            texture.dispose();
        }

        for(Music song : gameData.getSongs().values()) {
            song.dispose();
        }

        for(Sound soundEffect : gameData.getSoundEffects().values()) {
            soundEffect.dispose();
        }
    }

    public GameData getGameData() {
        return gameData;
    }

    private void LoadTextures()
    {
        Map<String, Texture> textures = gameData.getTextures();

        for(FileHandle fileHandle : Gdx.files.absolute("images/").list()) {
            if(fileHandle.isDirectory()) {
                for(FileHandle file : fileHandle.list(".png")) {
                    textures.put(file.nameWithoutExtension(), new Texture(file));
                }
            }
        }
    }

    private void LoadSongs()
    {
        Map<String, Music> songs = gameData.getSongs();

        for(FileHandle fileHandle : Gdx.files.absolute("sound/songs/").list()) {
            songs.put(fileHandle.nameWithoutExtension(), Gdx.audio.newMusic(fileHandle));
        }
    }

    private void LoadSoundEffects()
    {
        Map<String, Sound> soundEffects = gameData.getSoundEffects();

        for(FileHandle fileHandle : Gdx.files.absolute("sound/soundeffects/").list()) {
            soundEffects.put(fileHandle.nameWithoutExtension(), Gdx.audio.newSound(fileHandle));
        }
    }

    protected void loadContent()
    {
        int height = Gdx.graphics.getHeight();
        int width  = Gdx.graphics.getWidth();

        gameData.setResolution(new Rectangle(0, 0, width, height));
        gameData.setScreenScale(.5f);

        // Create a new SpriteBatch, which can be used to draw textures.
        gameData.setSpriteBatch(batch);

        // ----------------------------- Load the games assets -----------
        /*
        GameData.Font      = Content.Load<SpriteFont>(SpriteFontAsset);
        GameData.TitleFont = Content.Load<SpriteFont>(TitleFontAsset);
        */

        LoadTextures();
        LoadSongs();
        LoadSoundEffects();



        // GameData.InPlayState.LoadContent();
        // GameData.GameOverState.LoadContent();

        gameData.getSplashScreenState().LoadContent();
        gameData.getMenuState().LoadContent();
        gameData.getLogoState().LoadContent();
        /*
        GameData.EndOfLevelState.LoadContent();
        */

        gameData.getCurrentState().EnteringState();
    }
}
