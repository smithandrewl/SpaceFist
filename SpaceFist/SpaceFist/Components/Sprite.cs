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
    /// <summary>
    /// Sprite represents a static image at a position on-world.
    /// </summary>
    public class Sprite : GraphicsComponent
    {
        private Texture2D image;

        /// <summary>
        /// Creates a new Sprite instance from a texture
        /// </summary>
        /// <param name="texture">The image to draw</param>
        public Sprite(Texture2D texture)
        {
            image = texture;
        }

        public void Update(GameData gameData, Entity obj) 
        {
            
        }

        public void Draw(GameData gameData, Entity obj) 
        {
            var spriteBatch = gameData.SpriteBatch;

            var origin   = new Vector2(obj.Rectangle.Width / 2, obj.Rectangle.Height / 2);
            var position = new Vector2(obj.X, obj.Y) + origin;

            // Draw the texture at the location of the Entity obj
            spriteBatch.Draw(
                image, 
                position - gameData.Camera, 
                null, 
                obj.Tint, 
                obj.Rotation, 
                origin, 
                gameData.ScreenScale, 
                SpriteEffects.None, 
                0f
            );
        }
    }
}
