using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SpaceFist.Components.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Components
{
    // Loads a sprite sheet and displays the sprite at the current index
    class IndexedSprite : GraphicsComponent
    {
        private Texture2D texture;
        private int       width;
        private int       height;

        public int Index { get; set; }

        public IndexedSprite(Texture2D texture, int width, int height, int index = 0)
        {
            this.width   = width;
            this.height  = height;
            Index        = index;
            this.texture = texture;
        }

        
        public void Draw(Game game, Entity obj)
        {
            var sourceRect  = new Rectangle(Index * width, 0, width, height);
            var spriteBatch = game.SpriteBatch;

            var origin = new Vector2(obj.Rectangle.Width / 2, obj.Rectangle.Height / 2);

            spriteBatch.Draw(texture, new Vector2(obj.X, obj.Y) + origin, 
                             sourceRect,  obj.Tint,
                             obj.Rotation, origin, 
                             game.ScreenScale, SpriteEffects.None, 0);
        }

        public void Update(Game game, Entity obj)
        {

        }
    }
}
