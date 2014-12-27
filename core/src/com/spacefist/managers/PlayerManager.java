package com.spacefist.managers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.RoundData;
import com.spacefist.entities.Ship;
import com.spacefist.state.shipstates.SpawningState;
import com.spacefist.weapons.LaserWeapon;
import org.jetbrains.annotations.NotNull;

/// <summary>
/// Handles player and ship interaction with the game world.
/// </summary>
public class PlayerManager
{
    GameData  gameData;
    RoundData roundData;

    // Damage the ship takes per hit
    private static final int HIT_DAMAGE = 10;

    // Start the players ship moving at a velocity greater than the camer scrolls
    @NotNull
    private Vector2 StartingVelocity = new Vector2(0, -2);

    public boolean isAlive()
    {
        return gameData.getShip().isAlive();
    }

    /// <summary>
    /// Creates a new PlayerManager instance.
    /// </summary>
    /// <param name="gameData">Common game data</param>
    public PlayerManager(@NotNull GameData gameData)
    {
        this.gameData = gameData;
        roundData     = gameData.getRoundData();
    }

    public void Initialize()
    {
        gameData.getShip().initialize();
    }

    public void Update()
    {
        if (gameData.getShip().isAlive())
        {
            gameData.getShip().update();
        }
    }

    public void Draw()
    {
        if (gameData.getShip().isAlive())
        {
            gameData.getShip().draw();
        }
    }

    public Ship Spawn()
    {
        gameData.getSoundEffects().get("PlayerSpawn").play();

        Rectangle resolution = gameData.getResolution();
        Vector2 camera     = gameData.getCamera();

        // Start the ship at the bottom  in the center of the screen

        float xOffset = resolution.getWidth() / 2;
        double yOffset = resolution.getHeight() * .80;

        int startX = (int)(xOffset + camera.x);
        int startY = (int)(yOffset + camera.y);

        if (gameData.getShip() != null) {
            gameData.getShip().setCurrentState(new SpawningState(gameData));

            gameData.getShip().setX(startX);
            gameData.getShip().setY(startY);
            gameData.getShip().setAlive(true);
        }
        else {
            gameData.setShip(new Ship(gameData, new Vector2(startX, startY)));
            gameData.getShip().getCurrentState().enteringState();
        }

        gameData.getShip().setHealthPoints(100);
        gameData.getShip().setVelocity(StartingVelocity);

        return gameData.getShip();
    }

    public void HandleDeath()
    {
        gameData.getShip().onDeath();

        if (roundData.getLives() > 0)
        {
            roundData.setLives(roundData.getLives() - 1);
            gameData.getShip().setHealthPoints(100);

            Spawn();
        }
        else {
            gameData.getShip().setAlive(false);
        }

        gameData.getShip().setWeapon(new LaserWeapon(gameData));
    }

    public void ShipHit() {
        int healthPoints = gameData.getShip().getHealthPoints();
        gameData.getShip().setHealthPoints(healthPoints - HIT_DAMAGE);

        if (gameData.getShip().getHealthPoints() <= 0)
        {
            HandleDeath();
        }
    }

    public void Scored()
    {
        roundData.setScore(roundData.getScore() + 10);
    }

    public void ResetScore()
    {
        roundData.setScore(0);
    }

    public void ResetLives()
    {
        roundData.setLives(2);
    }

    // Replaces the ships current weapon
    // with the laser weapon.
    public void ResetWeapon() {
        gameData.getShip().setWeapon(new LaserWeapon(gameData));
    }
}
