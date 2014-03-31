using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Weapons
{
    class LaserWeapon : Weapon
    {
        private ProjectileManager projectileManager;
        private Game game;
        private Ship ship;

        public LaserWeapon(Game game, Ship ship)
        {
            this.game = game;
            this.ship = ship;
            this.projectileManager = game.InPlayState.ProjectileManager;
        }

        public void fire()
        {
            projectileManager.fireLaser((int) (ship.X + (ship.Rectangle.Width / 2) - (20 * game.ScreenScale)), (int) ((ship.Y - (35 * game.ScreenScale))));  
        }
    }
}
