/***Doncai******/
using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Weapons
{
    /// <summary>
    /// Represents a cluster of three missiles.
    /// </summary>
    class Missile : Weapon
    {
        private ProjectileManager projectileManager;

        private Ship ship;
        private Game game;

        public Missile(Game game, Ship ship)
        {
            this.game = game;
            this.ship = ship;

            projectileManager = game.InPlayState.ProjectileManager;
        }

        /// <summary>
        /// Fires a missile in the direction the ship is facing.
        /// </summary>
        public void fire()
        {
            int projectileX = (int)(ship.X + (ship.Rectangle.Width / 2) - 6);
            int projectileY = (int)((ship.Y - (35 * game.ScreenScale)));

            projectileManager.fireMissile(projectileX, projectileY);
        }
    }
}
/***Doncai******/