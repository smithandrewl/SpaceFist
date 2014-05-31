using Microsoft.Xna.Framework;
using SpaceFist.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Entities
{
    public class EnemyMine : Entity
    {
        /*
         *   Game              game, 
            Rectangle         rectangle, 
            PhysicsComponent  physics, 
            InputComponent    input, 
            GraphicsComponent graphics,
            SoundComponent    sound,
            float             rotation = 0)*/
        public EnemyMine(Game game, Vector2 position): base(
            game, 
            new Rectangle((int)position.X, (int)position.Y, game.Textures["EnemyMine"].Width, game.Textures["EnemyMine"].Height),
            new Physics(), 
            new NullInputComponent(), 
            new Sprite(game.Textures["EnemyMine"]), 
            new Sound(game.SoundEffects["Explosion"]))
        {

        }

        internal void Hit()
        {
            ((Sound)sound).play();
        }
    }
}
