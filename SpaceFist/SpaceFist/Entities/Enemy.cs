using Microsoft.Xna.Framework;
using SpaceFist.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.AI;
using SpaceFist.AI.DummyAI;

namespace SpaceFist.Entities
{
    public class Enemy : Entity
    {
       
       // The dimensions of the enemy
       public  const int WIDTH = 91;
       public const int HEIGHT = 190;

       public EnemyAI AI {get; set;}

       public Enemy(Game game, Vector2 position) : 
           base(game, 
                new Rectangle((int) position.X, (int) position.Y, WIDTH, HEIGHT),
                new Physics(), 
                new NullInputComponent(), 
                new Sprite(game.EnemyTexture),
                new NullSoundComponent(), game.ScreenScale)
       {
           Rotation = (float) ((3 * Math.PI) / 2);
       }

       public override void Update()
       {
           AI.Update();
           base.Update();
       }
    }
}
