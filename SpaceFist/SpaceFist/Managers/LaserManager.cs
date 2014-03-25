using Microsoft.Xna.Framework;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    class LaserManager : IEnumerable<Laser>
    {
        List<Laser> lasers;
        Game game;

        public LaserManager(Game game)
        {
            this.game = game;
            this.lasers = new List<Laser>();
        }

        public IEnumerator<Laser> GetEnumerator()
        {
            return lasers.GetEnumerator();
        }

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        public void Update()
        {
            lasers.ForEach(laser => laser.Update());
        }

        public void Draw()
        {
            lasers.ForEach(laser => laser.Draw());
        }

        public void fireLaser(int x, int y)
        {
            // Place a new active laser at x, y
            Laser laser = new Laser(game, game.LaserTexture, new Vector2(x, y));

            lasers.Add(laser);
        }

        public IEnumerable<Laser> Collisions(GameObject obj)
        {
            var collisions = 
                from  laser in lasers
                where laser.Rectangle.Intersects(obj.Rectangle) select laser;

            return collisions;
        }
    }
}
