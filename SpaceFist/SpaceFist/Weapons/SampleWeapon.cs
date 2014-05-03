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
        private Game game;

        /// <summary>
        /// Creates a new SampleWeapon instance.
        /// </summary>
        /// <param name="game">The game</param>
        /// <param name="ship">The players ship</param>
        public SampleWeapon(Game game, Ship ship)
        {
            this.game = game;
            this.ship = ship;
        
            projectileManager = game.InPlayState.ProjectileManager;
        }

        /// <summary>
        /// Fires a rocket cluster
        /// </summary>
        public void fire()
        {
            int projectileX = (int)(ship.X + (ship.Rectangle.Width / 2) - (20 * game.ScreenScale));
            int projectileY = (int)((ship.Y - (35 * game.ScreenScale)));

            projectileManager.fireSampleWeapon(projectileX, projectileY);  
        }
    }
}
