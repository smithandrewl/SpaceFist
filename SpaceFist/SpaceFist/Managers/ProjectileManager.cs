using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    public class ProjectileManager : IEnumerable<Projectile>
    {
        List<Projectile> projectiles;
        Game game;

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
            projectiles.ForEach(projectile => projectile.Update());
        }

        public void Draw()
        {
            projectiles.ForEach(projectile => projectile.Draw());
        }

        public void fireLaser(int x, int y)
        {
            // Place a new active laser at x, y
            Projectile projectile = new Projectile(game, game.LaserTexture, new Vector2(x, y));

            projectiles.Add(projectile);
        }

        public IEnumerable<Projectile> Collisions(Entity obj)
        {
            var collisions = 
                from  projectile in projectiles
                where projectile.Rectangle.Intersects(obj.Rectangle) select projectile;

            return collisions;
        }
    }
}
