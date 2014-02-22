using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;

using SpaceshipShooter.Components.Abstract;

namespace SpaceshipShooter.Components
{
    class Sound : SoundComponent
    {
        String      path;
        SoundEffect soundEffect;

        public Sound(String path)
        {
            this.path = path;
        }
        
        public void play()
        {
            soundEffect.Play();
        }

        public void LoadContent(Game game)
        {
            soundEffect = game.Content.Load<SoundEffect>(path);
        }

        public void Update(Game game, GameObject obj, Microsoft.Xna.Framework.GameTime time)
        {
        }
    }
}
