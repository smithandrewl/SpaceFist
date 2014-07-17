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
        private GameData gameData;

        public Missile(GameData gameData)
        {
            this.gameData = gameData;

            projectileManager = gameData.ProjectileManager;
        }

        /// <summary>
        /// Fires a missile in the direction the ship is facing.
        /// </summary>
        public void fire()
        {
            Ship ship = gameData.Ship;

            int projectileX = (int)(ship.X + (ship.Rectangle.Width / 2) - 6);
            int projectileY = (int)((ship.Y - (35 * gameData.ScreenScale)));

            projectileManager.fireMissile(projectileX, projectileY);
        }
    }
}
/***Doncai******/