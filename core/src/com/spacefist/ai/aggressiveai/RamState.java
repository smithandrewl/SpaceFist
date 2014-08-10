package com.spacefist.ai.aggressiveai;

import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.ai.FuzzyVariable;
import com.spacefist.ai.ShipEnemyInfo;
import com.spacefist.ai.ShipInfo;
import com.spacefist.ai.abst.EnemyAI;
import com.spacefist.ai.abst.EnemyAIState;
import com.spacefist.ai.abst.FuzzyLogicEnabled;
import com.spacefist.entities.Ship;
import com.spacefist.entities.enemies.Enemy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * A fuzzy state that rams the players ship.
 *
 * The likelyhood of ramming the players ship and the path the enemy takes are determined
 * by the degree that the state is active.
 */
public class RamState extends FuzzyLogicEnabled implements EnemyAIState {
    private static final int Speed = 6;

    private List<Vector2> wayPoints;
    private EnemyAI       ai;
    private Enemy         enemy;
    private GameData      gameData;
    private Date          lastUpdate;
    private Random        random;
    private float         membership;

    public RamState(EnemyAI ai, GameData gameData) {
        random        = new Random();
        this.ai       = ai;
        enemy         = ai.getShipEnemyInfo().getEnemy();
        wayPoints     = new ArrayList<Vector2>();
        lastUpdate    = new Date();
        this.gameData = gameData;
    }

    /**
     * Determines whether or not one point is near another (within 10 pixels).
     *
     * @param x1 The X coordinate of point 1
     * @param y1 The Y coordinate of point 1
     * @param x2 The X coordinate of point 2
     * @param y2 The Y coordinate of point 2
     * @return Returns true if the two points are within 10 pixels of each other
     */
    private static boolean Near(int x1, int y1, int x2, int y2) {
        int tolerance = 10;

        // TODO: Convert distance code in RamState
        // boolean xIsNear = MathHelper.Distance(x1, x2) <= tolerance;
        // boolean yIsNear = MathHelper.Distance(y1, y2) <= tolerance;
        boolean xIsNear = false;
        boolean yIsNear = false;

        return xIsNear && yIsNear;
    }

    /**
     * Updates the degree to which this state is active.
     */
    public void Update() {
        Ship ship = gameData.getShip();

        ShipInfo      shipInfo      = ai.getShipInfo();
        ShipEnemyInfo shipEnemyInfo = ai.getShipEnemyInfo();

        FuzzyVariable accuracy = shipInfo.getAccuracy();
        FuzzyVariable health   = shipInfo.getHealth();
        FuzzyVariable distance = shipEnemyInfo.getDistance();

        membership = Or(
            // If the player is doing too well
            And(
                accuracy.getHigh(),
                health.getHigh()
            ),
            // If the player is not too far away
            Not(distance.getHigh())
        );

        long millisecondsPassed = (new Date().getTime() - lastUpdate.getTime());

        // Keep up to 3 waypoints, updating them every 25 milliseconds
        if (millisecondsPassed > 25) {
            if (wayPoints.size() < 3) {
                // TODO: Convert rng code in RamState
                //int randX = random.Next(-10, 10);
                //int randY = random.Next(-10, 10);
                int randX = 0;
                int randY = 0;

                Vector2 shipLocation = new Vector2(
                    ship.getX() + randX,
                    ship.getY() + randY
                );

                if (!wayPoints.contains(shipLocation)) {
                    Vector2 lastPoint;

                    if (wayPoints.isEmpty()) {
                        lastPoint = new Vector2(enemy.getX(), enemy.getY());
                    } else {
                        lastPoint = wayPoints.get(wayPoints.size() - 1);
                    }

                    Vector2 newPoint = shipLocation.sub(lastPoint).nor();

                    float mult = 15 * membership;

                    // Using fuzzy logic, the generated way point will be more accurate
                    // the closer the enemy is to the ship.
                    newPoint = lastPoint.add(
                        new Vector2(
                            newPoint.x * mult,
                            newPoint.y * mult
                        )
                    );

                    wayPoints.add(newPoint);
                }
            }

            lastUpdate = new Date();
        }

        if (!wayPoints.isEmpty()) {
            Vector2 wayPoint = wayPoints.get(0);

            // If the enemy is close to the waypoint, remove the way point
            // and draw the enemy at rest.
            if (Near(enemy.getX(), enemy.getY(), (int) wayPoint.x, (int) wayPoint.y)) {
                wayPoints.remove(wayPoint);
            } else {

                // The line of sight vector
                Vector2 direction = wayPoint.sub(
                    new Vector2(
                        enemy.getX() * membership,
                        enemy.getY() * membership
                    )
                );

                // TODO: Convert interpolation code in RamState
                // var intX = MathHelper.Lerp(Enemy.Velocity.X, direction.X, .185f);
                // var intY = MathHelper.Lerp(Enemy.Velocity.Y, direction.Y, .185f);
                int intX = 0;
                int intY = 0;

                direction = new Vector2(intX, intY);

                // Convert the direction to a unit vector
                direction = direction.nor();

                // The rotation of the ship needed for it to face in the direction of the next waypoint
                float destRotation = (float) Math.toDegrees((float) (Math.atan2(direction.y, direction.x))) + 90;

                enemy.setRotation((float) Math.toRadians(destRotation));

                // Calculate a velocity to move along the line of sight at a magnitude of 5
                // TODO: Convert linear interpolation code in RamState
                //enemy.Velocity = (direction * (MathHelper.Lerp(Enemy.Velocity.Length(), Speed, .15f) * membership));
                enemy.setVelocity(Vector2.Zero);
            }
        }
    }

    public List<Vector2> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(List<Vector2> wayPoints) {
        this.wayPoints = wayPoints;
    }

    public EnemyAI getAi() {
        return ai;
    }

    public void setAi(EnemyAI ai) {
        this.ai = ai;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
}
