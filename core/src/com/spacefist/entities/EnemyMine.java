package com.spacefist.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.components.NullInputComponent;
import com.spacefist.components.Physics;
import com.spacefist.components.Sound;
import com.spacefist.components.Sprite;
import org.jetbrains.annotations.NotNull;

public class EnemyMine extends Entity {
    public EnemyMine(@NotNull GameData gameData, @NotNull Vector2 position) {
        super(
            gameData,
            new Rectangle(
                (int) position.x,
                (int) position.y,
                gameData.getTextures().get("EnemyMine").getWidth(),
                gameData.getTextures().get("EnemyMine").getHeight()
            ),
            new Physics(),
            new NullInputComponent(),
            new Sprite(gameData.getTextures().get("EnemyMine")),
            new Sound(gameData.getSoundEffects().get("Explosion")),
            0
        );
    }

    public void hit() {
        ((Sound) getSound()).play();
    }
}
