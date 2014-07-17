using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SpaceFist.AI.Abstract;
using SpaceFist.AI.ProjectileBehaviors;
using SpaceFist.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Entities
{
    /// <summary>
    /// Represents a projectile fired by either the player or an enemy.
    /// </summary>
    public class Projectile : Entity
    {
        private bool soundPlayed = false;
        public bool EnemyProjectile { get; set; }

        public ProjectileBehavior Behavior { get; set; }
        
        public Projectile(
            GameData  gameData, 
            Texture2D texture, 
            Vector2   position, 
            Vector2   unitVector, 
            int       speed, 
            bool      enemyProjectile = false
        ) : 
            base(
                gameData, 
                new Rectangle(
                    (int)position.X, 
                    (int)position.Y, 
                    texture.Width, 
                    texture.Height
                ), 
                new Physics(), 
                new NullInputComponent(), 
                new Sprite(texture),
                new Sound(gameData.SoundEffects["Laser"]),
                gameData.ScreenScale
            )
        {
            Velocity        = unitVector * speed;
            EnemyProjectile = enemyProjectile;

            Behavior = new NullBehavior();
        }

        // Plays a firing noise on the first update
        public override void Update() {
            var sound = (Sound) this.sound;
            
            if (!soundPlayed)
            {
                sound.play();
                soundPlayed = true;
            }

            Behavior.Update(this);

            base.Update();
        }
    }
}
