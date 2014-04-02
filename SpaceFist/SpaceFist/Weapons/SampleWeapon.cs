using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Weapons
{
    class SampleWeapon : Weapon
    {
        private ProjectileManager projectileManager;

        private Ship ship;
        private Game game;

        public SampleWeapon(Game game, Ship ship)
        {
            this.game = game;
            this.ship = ship;
        
            projectileManager = game.InPlayState.ProjectileManager;
        }

        public void fire()
        {
            int projectileX = (int)(ship.X + (ship.Rectangle.Width / 2) - (20 * game.ScreenScale));
            int projectileY = (int)((ship.Y - (35 * game.ScreenScale)));

            projectileManager.fireSampleWeapon(projectileX, projectileY);  
        }
    }
}
