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
using SpaceFist.Components.Abstract;

namespace SpaceFist
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

            var origin = new Vector2(obj.Rectangle.Width / 2, obj.Rectangle.Height / 2);

            spriteBatch.Draw(image, new Vector2(obj.X, obj.Y) + origin, 
                             null, obj.Tint, 
                             obj.Rotation, origin, 
                             game.ScreenScale, SpriteEffects.None, 0f);
        }
    }
}
