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

            foreach (var projectile in projectiles)
            {
                var screen = game.GraphicsDevice.Viewport.TitleSafeArea;
                var rect = new Rectangle((int)game.InPlayState.Camera.X, (int)game.InPlayState.Camera.Y, screen.Width, screen.Height);

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

        public void fireLaser(int x, int y)
        {
            game.InPlayState.RoundData.ShotsFired++;
            // Place a new active laser at x, y
            Projectile projectile = new Projectile(game, game.LaserTexture, new Vector2(x, y), 10);

            projectiles.Add(projectile);
        }

        public void fireSampleWeapon(int x, int y)
        {
            game.InPlayState.RoundData.ShotsFired++;
            Projectile projectile = 
                new Projectile(game, game.SampleProjectileTexture, new Vector2(x, y), 40);
            
            projectiles.Add(projectile);
        }


        /*******Dongcai**********/
        public void fireBluelaser(int x, int y)
        {
            game.InPlayState.RoundData.ShotsFired++;
            Projectile projectile =
                new Projectile(game, game.MineTexture, new Vector2(x, y), 0);

            projectiles.Add(projectile);
        }
        /***************************************/

        public IEnumerable<Projectile> Collisions(Entity obj)
        {
            var collisions = 
                from  projectile in projectiles
                where projectile.Rectangle.Intersects(obj.Rectangle) select projectile;

            return collisions;
        }
    }
}
