using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using SpaceshipShooter.Components.Abstract;

namespace SpaceshipShooter
{
    // The base class of all entities
    // uses interfaces (PhysicsComponent, InputComponent) to allow
    // game objects to be constructed with custom component implementations
    // GraphicsComponent could be a Sprite or Indexed sprite for example
    public class GameObject
    {
        protected Game               game;
        protected Rectangle          rectangle;
        protected PhysicsComponent   physics;
        protected InputComponent     input;
        protected GraphicsComponent  graphics;
        protected SoundComponent     sound;

        public Vector2 Velocity { get; set; }
        public bool    Alive    { get; set; }

        public Color Tint;

        public int X {
            get { return rectangle.X;  }
            set { rectangle.X = value; } 
        }

        public int Y {
            get { return rectangle.Y;  }
            set { rectangle.Y = value; }
        }

        public float Rotation { get; set; }
        
        public Rectangle Rectangle
        {
            get { return rectangle; }
            set { rectangle = value; }
        }

        public Game Game
        {
            get { return game; }
        }

        public GameObject(Game              game, 
                          Rectangle         rectangle, 
                          PhysicsComponent  physics, 
                          InputComponent    input, 
                          GraphicsComponent graphics,
                          SoundComponent    sound,
                          float             scale,
                          float rotation = 0)
        {
            Alive          = true;
            this.game      = game;
            this.rectangle = rectangle;
            this.physics   = physics;
            this.input     = input;
            this.graphics  = graphics;
            this.sound     = sound;

            Tint = Color.White;
        }

        public virtual void Initialize()
        {

        }

        public virtual void Update(GameTime time)
        {
            if (Alive)
            {
                graphics.Update(game, this, time);
                input.Update(game, this, time);
                physics.Update(game, this, time);
                sound.Update(game, this, time);
            }
        }

        public virtual void Draw(GameTime time)
        {
            if (Alive)
            {
                graphics.Draw(game, this, time);
            }
        }
    }
}
