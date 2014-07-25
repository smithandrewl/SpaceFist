package com.spacefist.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.ai.abst.ProjectileBehavior;
import com.spacefist.ai.projectilebehaviors.NullBehavior;
import com.spacefist.components.NullInputComponent;
import com.spacefist.components.Physics;
import com.spacefist.components.Sound;
import com.spacefist.components.Sprite;
import com.spacefist.components.abst.SoundComponent;

/// <summary>
/// Represents a projectile fired by either the player or an enemy.
/// </summary>
public class Projectile extends Entity
{
    private boolean soundPlayed = false;

    private boolean enemyProjectile;

    private ProjectileBehavior behavior;

    public Projectile(
            GameData gameData,
            Texture texture,
            Vector2 position,
            Vector2   unitVector,
            int       speed,
            boolean      enemyProjectile
    )
    {

        super(
                gameData,
                new Rectangle(
                        (int) position.x,
                        (int) position.y,
                        texture.getWidth(),
                        texture.getHeight()
                ),
                new Physics(),
                new NullInputComponent(),
                new Sprite(texture),
                new Sound(gameData.getSoundEffects().get("Laser")),
                gameData.getScreenScale()
        );

        setVelocity(new Vector2(unitVector.x * speed, unitVector.y * speed));
        this.enemyProjectile = enemyProjectile;

        behavior = new NullBehavior();
    }

    // Plays a firing noise on the first update
    @Override
    public void Update() {
        SoundComponent sound = (Sound) this.getSound();

        if (!soundPlayed)
        {
            ((Sound) sound).play();
            soundPlayed = true;
        }

        behavior.Update(this);

        super.Update();
    }
}