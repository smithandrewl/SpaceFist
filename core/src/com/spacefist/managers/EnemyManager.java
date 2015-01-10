package com.spacefist.managers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.spacefist.GameData;
import com.spacefist.entities.enemies.Enemy;
import com.spacefist.entities.enemies.EnemyFighter;
import com.spacefist.entities.enemies.EnemyFreighter;
import com.spacefist.util.Func;
import org.jetbrains.annotations.NotNull;

/// <summary>
/// Keeps track of all of the enemies in the world.
/// </summary>
public class EnemyManager extends Manager<Enemy>
{
    /// <summary>
    /// Creates a new EnemyManager instance.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    public EnemyManager(GameData gameData)
    {
        super(gameData);
    }

    /// <summary>
    /// Spawns a number of enemy fighters to the world.
    /// </summary>
    /// <param name="count">The number of fighters to spawn</param>
    public void spawnEnemyFighters(int count)
    {
        spawnEnemies(count, new Func<Vector2, Enemy>() {
            @NotNull
            @Override
            public Enemy call(@NotNull Vector2 position) {
                return new EnemyFighter(gameData, position);
            }
        });
    }

    /// <summary>
    /// Spawns the specified number of enemyFreighters to the world.
    /// </summary>
    /// <param name="count">The number of freighters to spawn</param>
    public void spawnEnemyFreighters(int count)
    {
        spawnEnemies(count, new Func<Vector2, Enemy>() {
            @NotNull
            @Override
            public Enemy call(@NotNull Vector2 position) {
                return new EnemyFreighter(gameData, position);
            }
        });
    }

    public void spawnEnemy(int x, int y, @NotNull Func<Vector2, Enemy> func)
    {
        float rotation = (float) Math.toRadians(180);
        Enemy enemy = func.call(new Vector2(x, y));
        enemy.setRotation(rotation);
        add(enemy);
    }

    public void spawnEnemy(int lowX, int highX, int lowY, int highY, @NotNull Func<Vector2, Enemy> func)
    {
        int randX = MathUtils.random(lowX, highX);
        int randY = MathUtils.random(lowY, highY);

        spawnEnemy(randX, randY, func);
    }
    /// <summary>
    /// Spawns a number of enemies to random locations on the screen
    /// given an enemy placement method.
    /// </summary>
    /// <param name="count">The number of enemies to spawn</param>
    /// <param name="func">A function to spawn a particular type of enemy</param>
    private void spawnEnemies(int count, @NotNull Func<Vector2, Enemy> func){
        assert count >= 0;
        assert func != null;

        spawnEnemies(
                count,
                0,
                (int) gameData.getWorld().getWidth(),
                0,
                (int) Math.max(
                        gameData.getWorld().getHeight() * .9f,
                        gameData.getResolution().getHeight() / 2
                ),
                func
        );
    }

    public void spawnEnemies(int count, int lowX, int highX, int lowY, int highY, @NotNull Func<Vector2, Enemy> func)
    {
        for (int i = 0; i < count; i++)
        {
            spawnEnemy(lowX, highX, lowY, highY, func);
        }
    }

    /// <summary>
    /// Returns a collection of all of the live enemies
    /// which are visible to the player.
    /// </summary>
    /// <returns></returns>
    @NotNull
    public Iterable<Enemy> getVisibleEnemies()
    {
        Vector2 camera         = gameData.getCamera();
        Rectangle backgroundRect = gameData.getResolution();

        Rectangle screenRect = new Rectangle(
            (int)camera.x,
            (int)camera.y,
            backgroundRect.getWidth(),
            backgroundRect.getHeight()
        );

        Array<Enemy> visibleEnemies = new Array<Enemy>(false, 16);

        for(Enemy enemy : this) {
            if (enemy.isAlive() && screenRect.overlaps(enemy.getRectangle())) {
                visibleEnemies.add(enemy);
            }
        }

        return visibleEnemies;
    }
}

