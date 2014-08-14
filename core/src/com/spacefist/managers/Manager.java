package com.spacefist.managers;

import com.spacefist.GameData;
import com.spacefist.entities.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Manager<T extends Entity> implements Iterable<T> {

    protected List<T>  entities;
    protected GameData gameData;

    public Manager(GameData gameData) {
        this.gameData = gameData;
        entities      = new ArrayList<T>();
    }

    protected void Add(T entity) {
        entities.add(entity);
    }

    protected void Remove(T entity) {
        entities.remove(entity);
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
        List<T> collisions = new ArrayList<T>();

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
