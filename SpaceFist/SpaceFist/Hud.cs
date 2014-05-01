using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist
{
    public class Hud
    {
        private const String ScoreFormat = "Score: {0} | Health: {1:P0} | Lives: {2}";
        private const String controlsMsg = "Controls: WASD to move, SPACE to fire, Q to quit";


        private String scoreDisplay = "";

        private Vector2       controlsPosition;
        private Game          game;
        private RoundData     roundData;
        private PlayerManager shipManager;
        private Vector2       scorePosition;
        private Rectangle     TopRect;
        private Rectangle     BottomRect;

        private Color color           = Color.LightGoldenrodYellow;
        private Color semiTransparent = new Color(255, 255, 255, .8f);

        public Hud(Game game, PlayerManager shipManager)
        {
            this.game        = game;
            this.roundData   = game.InPlayState.RoundData;
            this.shipManager = shipManager;

            var resolution = game.Resolution;

            controlsPosition = new Vector2(
                (resolution.Width * .5f) - (controlsMsg.Length * 5),
                resolution.Height * .975f
            );

            TopRect = new Rectangle(
                    0,
                    0,
                    game.HudTexture.Width,
                    game.HudTexture.Height
                );

            BottomRect = new Rectangle(
                    0,
                    game.Resolution.Height - game.HudTexture.Height,
                    game.HudTexture.Width,
                    game.HudTexture.Height
                );
        }

        public void Update()
        {
            scoreDisplay = String.Format(
                ScoreFormat, 
                roundData.Score, 
                shipManager.Ship.Health, 
                roundData.Lives
            );

            scorePosition = new Vector2(
                (game.Resolution.Width * .5f) - (scoreDisplay.Length * 5),
                 game.Resolution.Height * .001f
            );
        }

        public void Draw()
        {
            //Draw the top rectangle
            game.SpriteBatch.Draw(game.HudTexture, TopRect, semiTransparent);

            // Write the score to the screen
            game.SpriteBatch.DrawString(
                game.Font,
                scoreDisplay,
                scorePosition,
                color,
                0f,
                Vector2.Zero,
                game.ScreenScale,
                SpriteEffects.None,
                0
            );

            // Draw the bottom rectangle
            game.SpriteBatch.Draw(game.HudTexture, BottomRect, semiTransparent);

            // Write the controls message to the screen
            game.SpriteBatch.DrawString(
                game.Font,
                controlsMsg,
                controlsPosition,
                color,
                0f,
                Vector2.Zero,
                game.ScreenScale,
                SpriteEffects.None,
                0
            );
        }
    }
}
