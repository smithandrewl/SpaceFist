package com.spacefist.managers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.entities.enemies.EnemyFighter;
import com.spacefist.entities.enemies.EnemyFreighter;
import com.spacefist.util.Func;
import com.spacefist.GameData;
import com.spacefist.entities.enemies.Enemy;

import java.util.ArrayList;
import java.util.List;

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
    public void SpawnEnemyFighters(int count)
    {
        SpawnEnemies(count, new Func<Vector2, Enemy>(){
            public Enemy call(Vector2 position) {
                return new EnemyFighter(gameData, position);
            }
        });
    }

    /// <summary>
    /// Spawns the specified number of enemyFreighters to the world.
    /// </summary>
    /// <param name="count">The number of freighters to spawn</param>
    public void SpawnEnemyFreighters(int count)
    {
        SpawnEnemies(count, new Func<Vector2, Enemy>() {
            public Enemy call(Vector2 position) {
                return new EnemyFreighter(gameData, position);
            }
        });
    }

    public void SpawnEnemy(int x, int y, Func<Vector2, Enemy> func)
    {
        float rotation = (float) Math.toRadians(180);
        Enemy enemy = func.call(new Vector2(x, y));
        enemy.setRotation(rotation);
        Add(enemy);
    }

    public void SpawnEnemy(int lowX, int highX, int lowY, int highY, Func<Vector2,Enemy> func)
    {
        int randX = MathUtils.random(lowX, highX);
        int randY = MathUtils.random(lowY, highY);

        SpawnEnemy(randX, randY, func);
    }
    /// <summary>
    /// Spawns a number of enemies to random locations on the screen
    /// given an enemy placement method.
    /// </summary>
    /// <param name="count">The number of enemies to spawn</param>
    /// <param name="func">A function to spawn a particular type of enemy</param>
    private void SpawnEnemies(int count, Func<Vector2, Enemy> func){
        SpawnEnemies(
            count,
            0,
            (int) gameData.getWorld().getWidth(),
            0,
            (int) Math.max(
                gameData.getWorld().getHeight() *.9f,
                gameData.getResolution().getHeight() / 2
            ),
            func
        );
    }

    public void SpawnEnemies(int count, int lowX, int highX, int lowY, int highY, Func<Vector2, Enemy> func)
    {
        for (int i = 0; i < count; i++)
        {
            SpawnEnemy(lowX, highX, lowY, highY, func);
        }
    }

    /// <summary>
    /// Returns a collection of all of the live enemies
    /// which are visible to the player.
    /// </summary>
    /// <returns></returns>
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

        List<Enemy> visibleEnemies = new ArrayList<Enemy>();

        for(Enemy enemy : this) {
            if (enemy.isAlive() && screenRect.overlaps(enemy.getRectangle())) {
                visibleEnemies.add(enemy);
            }
        }

        return visibleEnemies;
    }
}

