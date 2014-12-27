package com.spacefist.entities.enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.ai.abst.EnemyAI;
import com.spacefist.components.NullInputComponent;
import com.spacefist.components.Physics;
import com.spacefist.components.Sprite;
import com.spacefist.entities.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * The parent class of all game enemies.
 */
public class Enemy extends Entity {
    /**
     * The AI that will control this enemy.
     */
    private EnemyAI ai;

    public Enemy(GameData gameData, @NotNull Texture enemyTexture, Sound sound, @NotNull Vector2 position) {
        super(
            gameData,
            new Rectangle(
                (int) position.x,
                (int) position.y,
                enemyTexture.getWidth(),
                enemyTexture.getHeight()
            ),
            new Physics(),
            new NullInputComponent(),
            new Sprite(enemyTexture),
            new com.spacefist.components.Sound(sound),
            0
        );

        setRotation((float) ((3 * Math.PI) / 2));
    }

    public EnemyAI getAi() {
        return ai;
    }

    public void setAi(EnemyAI ai) {
        this.ai = ai;
    }

    @Override
    public void update() {
        ai.update();
        super.update();
    }

    /**
     * Plays an explosion sound.
     */
    public void onDeath() {
        ((com.spacefist.components.Sound) getSound()).play();
    }
}
