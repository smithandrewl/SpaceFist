using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Weapons
{
    /// <summary>
    /// Represents a weapon which fires intercepting rockets.
    /// </summary>
    class SampleWeapon : Weapon
    {
        private ProjectileManager projectileManager;

        private Ship ship;
        private GameData gameData;

        /// <summary>
        /// Creates a new SampleWeapon instance.
        /// </summary>
        /// <param name="gameData">Common game data</param>
        /// <param name="ship">The players ship</param>
        public SampleWeapon(GameData gameData, Ship ship)
        {
            this.gameData = gameData;
            this.ship = ship;
        
            projectileManager = gameData.ProjectileManager;
        }

        /// <summary>
        /// Fires a rocket cluster
        /// </summary>
        public void fire()
        {
            int projectileX = (int)(ship.X + (ship.Rectangle.Width / 2) + 2);
            int projectileY = (int)((ship.Y - (35 * gameData.ScreenScale)));

            projectileManager.fireSampleWeapon(projectileX, projectileY);  
        }
    }
}
