using Microsoft.Xna.Framework;
using SpaceFist.AI.ProjectileBehaviors;
using SpaceFist.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    public class Manager<T>: IEnumerable<T> where T : Entity
    {
        private Random rand = new Random();

        protected List<T> entities;
        protected GameData gameData;

        public Manager(GameData gameData)
        {
            this.gameData = gameData;
            this.entities = new List<T>();
        }

        protected void Add(T entity)
        {
            entities.Add(entity);
        }

        protected void Remove(T entity)
        {
            entities.Remove(entity);
        }

        public IEnumerator<T> GetEnumerator()
        {
            return entities.GetEnumerator();
        }

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        public virtual void Update()
        {

            foreach (var entity in entities)
            {
                if (entity.Alive)
                {
                    entity.Update();
                }
            }
        }

        public virtual void Draw()
        {
            entities.ForEach(projectile => projectile.Draw());
        }

        public IEnumerable<T> Collisions(Entity obj)
        {
            var collisions =
                from entity in entities
                where entity.Alive && entity.Rectangle.Intersects(obj.Rectangle)
                select entity;

            return collisions;
        }

        public virtual void Clear()
        {
            entities.Clear();
        }
    }
}
