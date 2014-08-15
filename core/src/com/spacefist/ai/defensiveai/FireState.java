package com.spacefist.ai.defensiveai;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.spacefist.GameData;
import com.spacefist.ai.FuzzyVariable;
import com.spacefist.ai.ShipEnemyInfo;
import com.spacefist.ai.ShipInfo;
import com.spacefist.ai.abst.EnemyAI;
import com.spacefist.ai.abst.EnemyAIState;
import com.spacefist.ai.abst.FuzzyLogicEnabled;
import com.spacefist.managers.ProjectileManager;

import java.util.Date;

/**
 * This fuzzy state fires at the ship when the enemy is on the screen.
 */
public class FireState extends FuzzyLogicEnabled implements EnemyAIState {
    private float rateOfFire = 0;

    private ProjectileManager projectileManager;

    // The last time this enemy fired at the player
    private long lastFire;

    // The AI that this state belongs to
    private EnemyAI ai;

    // Fuzzy information about the player
    private ShipInfo shipInfo;

    // Fuzzy information about the player specific to this enemy
    private ShipEnemyInfo shipEnemyInfo;

    /**
     * Creates a new FireState instance
     *
     * @param ai The AI this state belongs to
     * @param gameData Common game data
     */
    public FireState(EnemyAI ai, GameData gameData) {
        this.ai       = ai;
        shipInfo      = ai.getShipInfo();
        shipEnemyInfo = ai.getShipEnemyInfo();
        lastFire      = TimeUtils.millis();

        this.projectileManager = gameData.getProjectileManager();
    }

    public void update() {
        FuzzyVariable distance = shipEnemyInfo.getDistance();

        rateOfFire = distance.defuzzify(700, 550, 300);

        // if this enemy is on screen, fire and wait a fuzzy amount of time before
        // firing again.
        if (ai.getShipEnemyInfo().isEnemyVisible()) {
            // Fire at the ship every 200 to 600 milliseconds depending on how far away
            // the ship is.  The further the ship is, the faster the enemy will fire.
            if ((TimeUtils.millis() - lastFire) / 1000 > rateOfFire) {
                Rectangle enemyRect = shipEnemyInfo.getEnemy().getRectangle();

                int halfWidth  = (int) enemyRect.getWidth() / 2;
                int halfHeight = (int) enemyRect.getHeight() / 2;

                projectileManager.fireLaser(
                        shipEnemyInfo.getEnemy().getX() + halfWidth,
                        shipEnemyInfo.getEnemy().getY() + halfHeight,
                        shipEnemyInfo.getLineOfSight(),
                        true
                );

                lastFire = TimeUtils.millis();
            }
        }
    }

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
