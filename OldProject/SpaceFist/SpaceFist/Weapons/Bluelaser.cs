using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Weapons
{
    /// <summary>
    /// Represents a weapon which drops stationary mines when fired.
    /// </summary>
    class Bluelaser : Weapon
    {
        private ProjectileManager projectileManager;
        private GameData gameData;

        /// <summary>
        /// Creates a new Bluelaser instance.
        /// </summary>
        /// <param name="gameData">Common game data</param>
        /// <param name="ship">The players ship</param>
        public Bluelaser(GameData gameData)
        {
            this.gameData = gameData;

            projectileManager = gameData.ProjectileManager;
        }

        /// <summary>
        /// Drops a mine at the ships location.
        /// </summary>
        public void fire()
        {
            Ship ship = gameData.Ship;

            int projectileX = (int)(ship.X + (ship.Rectangle.Width / 2) - (20 * gameData.ScreenScale));
            int projectileY = (int)((ship.Y - (35 * gameData.ScreenScale)));

            projectileManager.fireBluelaser(projectileX, projectileY);
        }
    }
}
