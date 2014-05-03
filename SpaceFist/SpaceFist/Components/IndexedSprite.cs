using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SpaceFist.Components.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Components
{
    // Given a texture and a frame width and height,
    // this component will draw a portion of the texture.
    //
    // Index is used to determine which frame of the image is being drawn.
    // This is used by Ship to draw itself turning. The image contains all of the images of the ship.
    // When the ship turns, it sets the Index property of its graphics component to draw the correct frame.
    //
    // Explosion.cs also uses IndexedSprite as its graphics component to draw an animation
    class IndexedSprite : GraphicsComponent
    {
        private Texture2D texture;
        private int       width;
        private int       height;

        public int Index { get; set; }

        /// <summary>
        /// Provides the ability to draw the cells of a horizontal texture atlas.
        /// </summary>
        /// <param name="texture">The texture atlas</param>
        /// <param name="width">The width of each frame</param>
        /// <param name="height">The height of each frame</param>
        /// <param name="index">The index of the currently visible frame</param>
        public IndexedSprite(Texture2D texture, int width, int height, int index = 0)
        {
            this.width   = width;
            this.height  = height;
            Index        = index;
            this.texture = texture;
        }
        
        public void Draw(Game game, Entity obj)
        {
            // Calculate the portion of the texture to draw given the current index
            var sourceRect  = new Rectangle(Index * width, 0, width, height);
            var spriteBatch = game.SpriteBatch;

            // Calculate and draw the image at an offset (this causes the image to rotate around 
            // its center and not its upper left corner
<<<<<<< HEAD
=======

>>>>>>> 1627f196e7707136c4f509c8182908f40de0fc5b
            var origin   = new Vector2(obj.Rectangle.Width / 2, obj.Rectangle.Height / 2);
            var position = new Vector2(obj.X, obj.Y) + origin;

            spriteBatch.Draw(
                texture, 
                position - game.InPlayState.Camera, 
                sourceRect,  
                obj.Tint,
                obj.Rotation, origin, 
                game.ScreenScale, 
                SpriteEffects.None, 
                0
            );
        }

        public void Update(Game game, Entity obj)
        {
        }
    }
}
