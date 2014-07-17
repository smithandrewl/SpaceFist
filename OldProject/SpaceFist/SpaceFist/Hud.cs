using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using SpaceFist.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist
{
    /// <summary>
    /// Draws information about the ongoing game and the player to the screen.
    /// </summary>
    public class Hud
    {
        private const String ScoreFormat = "Score: {0} | Health: {1:P0} | Lives: {2}";
        private const String controlsMsg = "Controls: WASD to move, SPACE to fire, Q to quit";


        private String scoreDisplay = "";

        private Vector2       controlsPosition;
        private GameData      gameData;
        private RoundData     roundData;
        private PlayerManager shipManager;
        private Vector2       scorePosition;
        private Rectangle     TopRect;
        private Rectangle     BottomRect;

        private Color color           = Color.LightGoldenrodYellow;
        private Color semiTransparent = new Color(255, 255, 255, .8f);

        public Hud(GameData gameData, PlayerManager shipManager)
        {
            this.gameData    = gameData;
            this.roundData   = gameData.RoundData;
            this.shipManager = shipManager;

            var resolution = gameData.Resolution;

            controlsPosition = new Vector2(
                (resolution.Width * .5f) - (controlsMsg.Length * 5),
                resolution.Height * .975f
            );

            TopRect = new Rectangle(
                    0,
                    0,
                    gameData.Textures["Hud"].Width,
                    gameData.Textures["Hud"].Height
                );

            BottomRect = new Rectangle(
                    0,
                    gameData.Resolution.Height - gameData.Textures["Hud"].Height,
                    gameData.Textures["Hud"].Width,
                    gameData.Textures["Hud"].Height
                );
        }

        public void Update()
        {
            scoreDisplay = String.Format(
                ScoreFormat, 
                roundData.Score, 
                gameData.Ship.Health, 
                roundData.Lives
            );

            scorePosition = new Vector2(
                (gameData.Resolution.Width * .5f) - (scoreDisplay.Length * 5),
                 gameData.Resolution.Height * .001f
            );
        }

        public void Draw()
        {
            //Draw the top rectangle
            gameData.SpriteBatch.Draw(gameData.Textures["Hud"], TopRect, semiTransparent);

            // Write the score to the screen
            gameData.SpriteBatch.DrawString(
                gameData.Font,
                scoreDisplay,
                scorePosition,
                color,
                0f,
                Vector2.Zero,
                gameData.ScreenScale,
                SpriteEffects.None,
                0
            );

            // Draw the bottom rectangle
            gameData.SpriteBatch.Draw(gameData.Textures["Hud"], BottomRect, semiTransparent);

            // Write the controls message to the screen
            gameData.SpriteBatch.DrawString(
                gameData.Font,
                controlsMsg,
                controlsPosition,
                color,
                0f,
                Vector2.Zero,
                gameData.ScreenScale,
                SpriteEffects.None,
                0
            );
        }
    }
}
