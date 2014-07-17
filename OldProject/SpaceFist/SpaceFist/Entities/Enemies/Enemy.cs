using Microsoft.Xna.Framework;
using SpaceFist.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.AI;
using SpaceFist.AI.DummyAI;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Audio;

namespace SpaceFist.Entities
{
    /// <summary>
    /// The parent class of all game enemies.
    /// </summary>
    public class Enemy : Entity
    {
       /// <summary>
       /// The AI that will control this enemy.
       /// </summary>
       public EnemyAI AI { get; set; }

       public Enemy(GameData gameData, Texture2D enemyTexture, SoundEffect sound, Vector2 position) : 
           base(gameData, 
                new Rectangle(
                    (int) position.X, 
                    (int) position.Y, 
                    enemyTexture.Width, 
                    enemyTexture.Height
                ),
                new Physics(), 
                new NullInputComponent(), 
                new Sprite(enemyTexture),
                new Sound(sound), 
                gameData.ScreenScale)
       {
           Rotation = (float) ((3 * Math.PI) / 2);
       }

       public override void Update()
       {
           AI.Update();
           base.Update();
       }

        /// <summary>
        /// Plays an explosion sound.
        /// </summary>
       public void OnDeath()
       {
           ((Sound)sound).play();
       }
    }
}