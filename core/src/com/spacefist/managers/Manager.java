package com.spacefist.managers;

import com.badlogic.gdx.utils.Array;
import com.spacefist.GameData;
import com.spacefist.entities.Entity;

import java.util.Iterator;

public class Manager<T extends Entity> implements Iterable<T> {

    protected Array<T> entities;
    protected GameData gameData;

    public Manager(GameData gameData) {
        this.gameData = gameData;
        entities      = new Array<T>(false, 16);
    }

    protected void Add(T entity) {
        entities.add(entity);
    }

    protected void Remove(T entity) {
        entities.removeValue(entity,true);
    }

    public void Update() {
        for (T entity : entities) {
            if (entity.isAlive()) {
                entity.update();
            }
        }
    }

    public void Draw() {
        for(T entity : entities) {
            entity.draw();
        }
    }

    public Iterable<T> Collisions(Entity obj) {
        Array<T> collisions = new Array<T>(false, 16);

        for(T entity : entities) {
            if(entity.isAlive() && entity.getRectangle().overlaps(obj.getRectangle())) {
                collisions.add(entity);
            }
        }

        return collisions;
    }

    public void Clear() {
        entities.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return entities.iterator();
    }
}
