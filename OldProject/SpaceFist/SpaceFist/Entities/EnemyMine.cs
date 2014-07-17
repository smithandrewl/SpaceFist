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
        public EnemyMine(GameData gameData, Vector2 position): base(
            gameData, 
            new Rectangle((int)position.X, (int)position.Y, gameData.Textures["EnemyMine"].Width, gameData.Textures["EnemyMine"].Height),
            new Physics(), 
            new NullInputComponent(), 
            new Sprite(gameData.Textures["EnemyMine"]), 
            new Sound(gameData.SoundEffects["Explosion"]))
        {

        }

        internal void Hit()
        {
            ((Sound)sound).play();
        }
    }
}
