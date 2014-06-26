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
using Framework.ParticleEngine;

namespace ParticleEngineTeset
{
    /// <summary>
    /// This is the main type for your game
    /// </summary>
    public class Game1 : Microsoft.Xna.Framework.Game
    {
        GraphicsDeviceManager graphics;
        SpriteBatch           spriteBatch;
        Texture2D             part;
        ParticleEmitter       plasmaBallEmitter;
        ParticleEmitter       explosionEmitter;
        SpriteFont            font;

        private Texture2D explosion;

        public Game1()
        {
            graphics = new GraphicsDeviceManager(this);
            Content.RootDirectory = "Content";
        }

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>
        protected override void Initialize()
        {
            // TODO: Add your initialization logic here

            base.Initialize();
        }

        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
            spriteBatch = new SpriteBatch(GraphicsDevice);

            part = Content.Load<Texture2D>("Particle");
            explosion = Content.Load<Texture2D>("Particle3");
            font = Content.Load<SpriteFont>("SpriteFont1");

            plasmaBallEmitter = new ParticleEmitter(
                part, 
                1000,
                10, 
                new Vector2(200, 100), 
                spriteBatch,
                new ParticleOptions(1.25f, 450, -360, 360, 0, 3, 1, 6)
            );

            explosionEmitter = new ParticleEmitter(
                explosion,
                500,
                25,
                new Vector2(400, 100),
                spriteBatch,
                new ParticleOptions(1.25f, 500, 0, 50, 0, 0, 1, 7)
            );
        }

        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// all content.
        /// </summary>
        protected override void UnloadContent()
        {
            // TODO: Unload any non ContentManager content here
        }

        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Update(GameTime gameTime)
        {
            // Allows the game to exit
            if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed)
                this.Exit();

            var mouse = Mouse.GetState();


            //explosionEmitter.Position = new Vector2(mouse.X, mouse.Y);

            // TODO: Add your update logic here
            plasmaBallEmitter.Update();
            explosionEmitter.Update();
            base.Update(gameTime);
        }

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Draw(GameTime gameTime)
        {
            spriteBatch.Begin();
            GraphicsDevice.Clear(Color.Black);

            // TODO: Add your drawing code here
            plasmaBallEmitter.Draw();
            spriteBatch.DrawString(font, "Plasma Ball", new Vector2(150, 150), Color.White);

            explosionEmitter.Draw();
            spriteBatch.DrawString(font, "Explosion", new Vector2(350, 150), Color.White);

            spriteBatch.End();
            base.Draw(gameTime);
        }
    }
}
