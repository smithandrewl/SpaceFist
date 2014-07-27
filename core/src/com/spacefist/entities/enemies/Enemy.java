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

/// <summary>
/// The parent class of all game enemies.
/// </summary>
public class Enemy extends Entity {
    /// <summary>
    /// The AI that will control this enemy.
    /// </summary>
    private EnemyAI ai;

    public Enemy(GameData gameData, Texture enemyTexture, Sound sound, Vector2 position) {
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
    public void Update() {
        getAi().Update();
        super.Update();
    }

    /// <summary>
    /// Plays an explosion sound.
    /// </summary>
    public void OnDeath() {
        ((com.spacefist.components.Sound) getSound()).play();
    }
}
