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

        private Ship ship;
        private Game game;

        /// <summary>
        /// Creates a new Bluelaser instance.
        /// </summary>
        /// <param name="game">The game</param>
        /// <param name="ship">The players ship</param>
        public Bluelaser(Game game, Ship ship)
        {
            this.game = game;
            this.ship = ship;

            projectileManager = game.InPlayState.ProjectileManager;
        }

        /// <summary>
        /// Drops a mine at the ships location.
        /// </summary>
        public void fire()
        {
            int projectileX = (int)(ship.X + (ship.Rectangle.Width / 2) - (20 * game.ScreenScale));
            int projectileY = (int)((ship.Y - (35 * game.ScreenScale)));

            projectileManager.fireBluelaser(projectileX, projectileY);
        }
    }
}
