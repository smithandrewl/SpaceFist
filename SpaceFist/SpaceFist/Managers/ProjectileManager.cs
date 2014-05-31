using Microsoft.Xna.Framework;
using SpaceFist.AI.ProjectileBehaviors;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    /// <summary>
    /// Keeps track of the projectiles in the world.
    /// </summary>
    public class ProjectileManager : Manager<Projectile>
    {
        private Random rand = new Random();

        /// <summary>
        /// Creates a new ProjectileManager instance
        /// </summary>
        /// <param name="game">The game</param>
        public ProjectileManager(Game game): base(game)
        {
        }

        public override void Update()
        {

            foreach (var projectile in entities)
            {
                var resolution = game.Resolution;
                
                var rect = new Rectangle(
                    (int)game.InPlayState.Camera.X, 
                    (int)game.InPlayState.Camera.Y, 
                    resolution.Width, 
                    resolution.Height
                );

                // Mark offscreen live projectiles as dead and
                // only update onscreen projectiles.
                if (projectile.Alive)
                {
                    if (rect.Contains(projectile.Rectangle))
                    {
                        projectile.Update();
                    }
                    else
                    {
                        projectile.Alive = false;
                    }
                }
            }
        }

        /// <summary>
        /// Places a laser at the specified location and fires it. 
        /// </summary>
        /// <param name="x">The X value of the location</param>
        /// <param name="y">The Y value of the location</param>
        public void fireLaser(int x, int y)
        {
            fireLaser(x, y, new Vector2(0, -1));
        }

        /// <summary>
        /// Fires a laser from the specified location in the specified direction.
        /// </summary>
        /// <param name="x">The X value of the location</param>
        /// <param name="y">The Y value of the location</param>
        /// <param name="direction">The direction to send the projectile</param>
        /// <param name="enemyLaser">Whether this laser belongs to an enemy</param>
        public void fireLaser(int x, int y, Vector2 direction, bool enemyLaser = false)
        {
            game.InPlayState.RoundData.ShotsFired++;

            float rotation = ((float) MathHelper.ToDegrees((float) Math.Atan2(direction.Y, direction.X)) + 90);

            // Place a new active laser at x, y
            Projectile projectile = new Projectile(
                game, 
                game.Textures["Laser"], 
                new Vector2(x, y), 
                direction, 
                9, 
                enemyLaser
            );
            
            projectile.Rotation = MathHelper.ToRadians(rotation);

            Add(projectile);

        }

        /// <summary>
        /// Fires a rocket cluster from the specified location
        /// </summary>
        /// <param name="x">The X component of the location</param>
        /// <param name="y">The Y component of the location</param>
        public void fireSampleWeapon(int x, int y)
        {
            var onScreen = new List<Entity>(game.InPlayState.EnemyManager.VisibleEnemies());
            onScreen.AddRange(game.InPlayState.BlockManager.VisibleBlocks());

            onScreen = new List<Entity>(onScreen.Where(entity => entity.Y < y));

            if (onScreen.Count != 0)
            {
                // Mark several onscreen entities as targets
                // and send rockets to intercept them.
                for (int i = 0; i < 4; i++)
                {
                    var idx = rand.Next(onScreen.Count);
                    Entity target = onScreen[idx];

                    // Mark targeted entities by tinting them red
                    target.Tint = Color.Crimson;

                    game.InPlayState.RoundData.ShotsFired++;

                    Projectile projectile = new Projectile(
                        game,
                        game.Textures["SampleWeapon"],
                        new Vector2(x, y),
                        new Vector2(0, -1),
                        10
                    );

                    projectile.Behavior = new SeekingBehavior(
                        new Vector2(0, -1), 
                        new Vector2(x, y), 
                        target
                    );
                    
                    Add(projectile);
                }
            }
        }

        /*******Dongcai**********/
        public void fireBluelaser(int x, int y)
        {
            game.InPlayState.RoundData.ShotsFired++;
         
            Projectile projectile = new Projectile(
                game, 
                game.Textures["Mine"], 
                new Vector2(x, y), 
                new Vector2(0, 0), 
                0
            );

            Add(projectile);
        }

        public void fireMissile(int x, int y)
        {
            game.InPlayState.RoundData.ShotsFired++;

            Projectile projectile = new Projectile(
                game, 
                game.Textures["Missile"], 
                new Vector2(x, y), 
                new Vector2(0, -1), 
                20
            );

            Add(projectile);

            Projectile projectile1 = new Projectile(
                game, 
                game.Textures["Missile"], 
                new Vector2(x + 50, y), 
                new Vector2(0, -1), 
                10
            );

            Add(projectile1);

            Projectile projectile2 = new Projectile(
                game, 
                game.Textures["Missile"], 
                new Vector2(x - 50, y), 
                new Vector2(0, -1), 
                10
            );

            Add(projectile2);

        }
        /***************************************/

        /// <returns>
        /// Returns a collection of all of the live projectiles
        /// fired by the player.
        /// </returns>
        public IEnumerable<Projectile> PlayerProjectiles()
        {
            var playerProjs = 
                from   projectile in entities
                where  (projectile.EnemyProjectile == false) && projectile.Alive
                select projectile;

            return playerProjs;
        }

        /// <summary>
        /// Returns a collection of all of the live projectiles
        /// fired by an enemy.
        /// </summary>
        /// <returns></returns>
        public IEnumerable<Projectile> EnemyProjectiles()
        {
            var enemyProjs =
                from   projectile in entities
                where  (projectile.EnemyProjectile == true) && projectile.Alive
                select projectile;

            return enemyProjs;
        }
    }
}
