using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SpaceshipShooter.Components.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceshipShooter.Components
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

        public void Draw(Game game, GameObject obj, Microsoft.Xna.Framework.GameTime time)
        {
            var sourceRect  = new Rectangle(Index * width, 0, width, height);
            var spriteBatch = game.SpriteBatch;

            
            spriteBatch.Draw(texture, new Vector2(obj.X, obj.Y), 
                             sourceRect, Color.White, 
                             0f, new Vector2(0, 0), 
                             game.ScreenScale, SpriteEffects.None, 0);
        }

        public void Update(Game game, GameObject obj, Microsoft.Xna.Framework.GameTime time)
        {

        }
    }
}
