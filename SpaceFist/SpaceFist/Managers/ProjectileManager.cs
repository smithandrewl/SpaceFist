﻿using Microsoft.Xna.Framework;
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
    public class ProjectileManager : IEnumerable<Projectile>
    {
        private Random rand = new Random();

        List<Projectile> projectiles;
        Game game;

        /// <summary>
        /// Creates a new ProjectileManager instance
        /// </summary>
        /// <param name="game">The game</param>
        public ProjectileManager(Game game)
        {
            this.game = game;
            this.projectiles = new List<Projectile>();
        }

        public IEnumerator<Projectile> GetEnumerator()
        {
            return projectiles.GetEnumerator();
        }

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        public void Update()
        {

            foreach (var projectile in projectiles)
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

        public void Draw()
        {
            projectiles.ForEach(projectile => projectile.Draw());
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
                game.LaserTexture, 
                new Vector2(x, y), 
                direction, 
                10, 
                enemyLaser
            );
            
            projectile.Rotation = MathHelper.ToRadians(rotation);

            projectiles.Add(projectile);

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
                        game.SampleProjectileTexture,
                        new Vector2(x, y),
                        new Vector2(0, -1),
                        10
                    );

                    projectile.Behavior = new SeekingBehavior(
                        new Vector2(0, -1), 
                        new Vector2(x, y), 
                        target
                    );
                    
                    projectiles.Add(projectile);
                }
            }
        }

        /*******Dongcai**********/
        public void fireBluelaser(int x, int y)
        {
            game.InPlayState.RoundData.ShotsFired++;
         
            Projectile projectile = new Projectile(
                game, 
                game.MineTexture, 
                new Vector2(x, y), 
                new Vector2(0, 0), 
                0
            );

            projectiles.Add(projectile);
        }

        public void fireMissile(int x, int y)
        {
            game.InPlayState.RoundData.ShotsFired++;

            Projectile projectile = new Projectile(
                game, 
                game.MissileTexture, 
                new Vector2(x, y), 
                new Vector2(0, -1), 
                20
            );

            projectiles.Add(projectile);

            Projectile projectile1 = new Projectile(
                game, 
                game.MissileTexture, 
                new Vector2(x + 50, y), 
                new Vector2(0, -1), 
                10
            );

            projectiles.Add(projectile1);

            Projectile projectile2 = new Projectile(
                game, 
                game.MissileTexture, 
                new Vector2(x - 50, y), 
                new Vector2(0, -1), 
                10
            );

            projectiles.Add(projectile2);

        }
        /***************************************/

        /// <summary>
        /// Returns a collection of all of the projectiles colliding with the 
        /// the specified entity.
        /// </summary>
        /// <param name="obj">The entity to check for projectile collisions</param>
        /// <returns></returns>
        public IEnumerable<Projectile> Collisions(Entity obj)
        {
            var collisions = 
                from   projectile in projectiles
                where  projectile.Alive && projectile.Rectangle.Intersects(obj.Rectangle) 
                select projectile;

            return collisions;
        }

        /// <returns>
        /// Returns a collection of all of the live projectiles
        /// fired by the player.
        /// </returns>
        public IEnumerable<Projectile> PlayerProjectiles()
        {
            var playerProjs = 
                from   projectile in projectiles
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
                from   projectile in projectiles
                where  (projectile.EnemyProjectile == true) && projectile.Alive
                select projectile;

            return enemyProjs;
        }
    }
}
