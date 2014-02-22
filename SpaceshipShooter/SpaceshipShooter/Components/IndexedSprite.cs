using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Project2.Components.Abstract;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Project2.Components
{
    // Loads a sprite sheet and displays the sprite at the current index
    class IndexedSprite : GraphicsComponent
    {
        private String    path;
        private Texture2D texture;
        private int       width;
        private int       height;

        public int Index { get; set; }

        public IndexedSprite(String path, int width, int height, int index = 0)
        {
            this.path   = path;
            this.width  = width;
            this.height = height;
            Index       = index;
        }

        public void LoadContent(Game game)
        {
            texture = game.Content.Load<Texture2D>(path);
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
