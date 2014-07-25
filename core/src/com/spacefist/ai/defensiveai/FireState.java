package com.spacefist.ai.defensiveai;

import com.spacefist.GameData;
import com.spacefist.ai.abst.EnemyAI;
import com.spacefist.ai.abst.EnemyAIState;
import com.spacefist.ai.abst.FuzzyLogicEnabled;
import com.spacefist.ai.ShipEnemyInfo;
import com.spacefist.ai.ShipInfo;

import java.util.Date;

/// <summary>
/// This fuzzy state fires at the ship when the enemy is on the screen.
/// </summary>
public class FireState extends FuzzyLogicEnabled implements EnemyAIState
{
    private float rateOfFire = 0;

    // TODO: Convert ProjectileManager
    // private ProjectileManager projectileManager;

    private Date lastFire; // The last time this enemy fired at the player

    /// <summary>
    /// Creates a new FireState instance
    /// </summary>
    /// <param name="ai">The AI this state belongs to.</param>
    /// <param name="gameData">Common game data</param>
    public FireState(EnemyAI ai, GameData gameData)
    {
        this.ai       = ai;
        shipInfo      = ai.getShipInfo();
        shipEnemyInfo = ai.getShipEnemyInfo();
        lastFire      = new Date();

        // TODO: Convert projectileManager
        //this.projectileManager = gameData.ProjectileManager;
    }

    public void Update()
    {
        rateOfFire = shipEnemyInfo.getDistance().Defuzzify(700, 550, 300);

        // if this enemy is on screen, fire and wait a fuzzy amount of time before
        // firing again.
        if (ai.getShipEnemyInfo().isEnemyVisible())
        {
            Date now = new Date();
            // Fire at the ship every 200 to 600 milliseconds depending on how far away
            // the ship is.  The further the ship is, the faster the enemy will fire.
            if (((now.getTime() - lastFire.getTime()) / 1000) > rateOfFire)
            {
                int halfWidth  = (int) shipEnemyInfo.getEnemy().getRectangle().getWidth()  / 2;
                int halfHeight = (int) shipEnemyInfo.getEnemy().getRectangle().getHeight() / 2;

                //TODO: Convert ProjectileManager
                /*
                projectileManager.fireLaser(
                        ShipEnemyInfo.Enemy.X +halfWidth,
                        ShipEnemyInfo.Enemy.Y + halfHeight,
                        ShipEnemyInfo.LineOfSight,
                        true
                );
                */

                lastFire = now;
            }
        }
    }

    // The AI that this state belongs to
    private EnemyAI ai;
    // Fuzzy information about the player
    private ShipInfo shipInfo;
    // Fuzzy information about the player specific to this enemy
    private ShipEnemyInfo shipEnemyInfo;

    public EnemyAI getAi() {
        return ai;
    }

    public void setAi(EnemyAI ai) {
        this.ai = ai;
    }

    public ShipInfo getShipInfo() {
        return shipInfo;
    }

    public void setShipInfo(ShipInfo shipInfo) {
        this.shipInfo = shipInfo;
    }

    public ShipEnemyInfo getShipEnemyInfo() {
        return shipEnemyInfo;
    }

    public void setShipEnemyInfo(ShipEnemyInfo shipEnemyInfo) {
        this.shipEnemyInfo = shipEnemyInfo;
    }
}
