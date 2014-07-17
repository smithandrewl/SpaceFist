using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Weapons
{
    /// <summary>
    /// The default weapon
    /// </summary>
    class LaserWeapon : Weapon
    {
        private ProjectileManager projectileManager;
        
        private GameData gameData;

        /// <summary>
        /// Creates a new LaserWeapon instance
        /// </summary>
        /// <param name="gameData">Common game data</param>
        public LaserWeapon(GameData gameData)
        {
            this.gameData = gameData;
        
            this.projectileManager = gameData.ProjectileManager;
        }

        public void fire()
        {
            Ship ship = gameData.Ship;
            int projectileX = (int) (ship.X + (ship.Rectangle.Width / 2));
            int projectileY = (int) ((ship.Y - (35 * gameData.ScreenScale)));

            projectileManager.fireLaser(projectileX, projectileY);  
        }
    }
}
