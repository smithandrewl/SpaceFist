using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using SpaceFist.Components.Abstract;

namespace SpaceFist
{   
    /// <summary>
    /// The base class of all entities.  Anything that moves and is drawn in the game is 
    /// a subclass of the Entity class.
    /// 
    /// Instead of performing tasks such as drawing or handling input,
    /// the Entity base class keeps references to classes implementing known interfaces.  It then calls methods of these interfaces
    /// such as Update and Draw.
    /// 
    /// What this means is that the Entity base class can update and draw itself without knowning how it is being updated or drawn.
    /// 
    /// On update, the Entity base class calls the Update method on the graphics, physics, input and sound components.
    /// On draw, the Entity base class calls the Draw method on the graphics component.
    /// 
    /// Each Entity Subclass provides the exact implementations of the components in its constructor.
    /// 
    /// For example, The block class uses Sprite as its GraphicsComponent because it only needs to draw a single image.
    /// The Explosion and Ship classes use the IndexedSprite component as its GraphicsComponent because it needs to draw
    /// itself in multiple ways (each frame of the explosion or when the ship turns).
    /// 
    /// As much as possible, the Entity class contains data about the entity, while the components operate on the data.
    /// </summary>
    public class Entity
    {
        protected GameData           gameData;
        protected Rectangle          rectangle;
        protected PhysicsComponent   physics;
        protected InputComponent     input;
        protected GraphicsComponent  graphics;
        protected SoundComponent     sound;

        /// <summary>
        /// The direction and speed of the entity
        /// </summary>
        public Vector2 Velocity { get; set; }
        
        /// <summary>
        /// Whether the entity is in play or not. The default Update and Draw methods
        /// only update and draw when the entity is Alive.
        /// </summary>
        public bool Alive { get; set; }

        /// <summary>
        /// A hue which can be used by a graphics component when drawning the entity.
        /// </summary>
        public Color Tint;

        /// <summary>
        /// This property provides an easy way to get and set the X coordinate of the rectangle associated with this entity.
        /// </summary>
        public int X 
        {
            get 
            { 
                return rectangle.X;  
            }
            set 
            { 
                rectangle.X = value; 
            } 
        }

        /// <summary>
        /// This property provides an easy way to get and set the Y coordinate of the rectangle associated with this entity.
        /// </summary>
        public int Y {
            get 
            { 
                return rectangle.Y;  
            }
            set 
            { 
                rectangle.Y = value; 
            }
        }

        /// <summary>
        /// This represents the rotation of the entity.
        /// </summary>
        public float Rotation { get; set; }
        
        /// <summary>
        /// The location, height and width of this entity.
        /// </summary>
        public Rectangle Rectangle
        {
            get 
            { 
                return rectangle; 
            }
            set 
            { 
                rectangle = value; 
            }
        }

        public GameData GameData
        {
            get 
            { 
                return gameData; 
            }
        }

        /// <param name="gameData">Common game data</param>
        /// <param name="rectangle">The size and position of the entity</param>
        /// <param name="physics">The physics component to use</param>
        /// <param name="input">The input component to use</param>
        /// <param name="graphics">The graphics component to use</param>
        /// <param name="sound">The sound component to use</param>
        /// <param name="rotation">The rotation of the entity.  The default rotation is 0 degrees.</param>
        public Entity(
            GameData          gameData, 
            Rectangle         rectangle, 
            PhysicsComponent  physics, 
            InputComponent    input, 
            GraphicsComponent graphics,
            SoundComponent    sound,
            float             rotation = 0)
        {
            Alive          = true;
            this.gameData  = gameData;
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

        /// <summary>
        /// If the ship is alive, update all of its components
        /// </summary>
        public virtual void Update()
        {
            if (Alive)
            {
                graphics.Update(gameData, this);
                input.Update(gameData, this);
                physics.Update(gameData, this);
                sound.Update(gameData, this);
            }
        }

        /// <summary>
        /// If the ship is alive, call draw on its graphics component
        /// </summary>
        public virtual void Draw()
        {
            if (Alive)
            {
                graphics.Draw(gameData, this);
            }
        }
    }
}
