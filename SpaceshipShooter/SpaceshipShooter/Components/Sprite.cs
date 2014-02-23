using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;
using SpaceshipShooter.Components.Abstract;

namespace SpaceshipShooter
{
    // Sprite represents a static image at a position on-screen 
    public class Sprite : GraphicsComponent
    {
        private Texture2D image;

        public Sprite(Texture2D texture)
        {
            image = texture;
        }

        public void Update(Game game, GameObject obj, GameTime time) 
        {
            
        }

        public void Draw(Game game, GameObject obj, GameTime time) 
        {
            var spriteBatch = game.SpriteBatch;
            
            spriteBatch.Draw(image, new Vector2(obj.X, obj.Y), 
                             null, Color.White, 
                             0f, new Vector2(0, 0), 
                             game.ScreenScale, SpriteEffects.None, 0f);
        }
    }
}
