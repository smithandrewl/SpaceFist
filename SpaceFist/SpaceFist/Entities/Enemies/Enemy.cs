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
    public class Enemy : Entity
    {
       public EnemyAI AI { get; set; }

       public Enemy(Game game, Texture2D enemyTexture, SoundEffect sound, Vector2 position) : 
           base(game, 
                new Rectangle(
                    (int) position.X, 
                    (int) position.Y, 
                    enemyTexture.Width, 
                    enemyTexture.Height
                ),
                new Physics(), 
                new NullInputComponent(), 
                new Sprite(enemyTexture),
                new Sound(sound), game.ScreenScale)
       {
           Rotation = (float) ((3 * Math.PI) / 2);
       }

       public override void Update()
       {
           AI.Update();
           base.Update();
       }

       public void OnDeath()
       {
           ((Sound)sound).play();
       }
    }
}
