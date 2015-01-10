package com.spacefist.managers;

import com.badlogic.gdx.utils.Array;
import com.spacefist.GameData;
import com.spacefist.entities.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class Manager<T extends Entity> implements Iterable<T> {

    protected Array<T> entities;
    protected GameData gameData;

    public Manager(GameData gameData) {
        this.gameData = gameData;
        entities      = new Array<T>(false, 16);
    }

    protected void add(@NotNull T entity) {
        assert entity != null;

        entities.add(entity);
    }

    protected void remove(@NotNull T entity) {
        assert entity != null;

        entities.removeValue(entity,true);
    }

    public void update() {
        for (T entity : entities) {
            if (entity.isAlive()) {
                entity.update();
            }
        }
    }

    public void draw() {
        for(T entity : entities) {
            entity.draw();
        }
    }

    @NotNull
    public Iterable<T> collisions(@NotNull Entity obj) {
        Array<T> collisions = new Array<T>(false, 16);

        for(T entity : entities) {
            if(entity.isAlive() && entity.getRectangle().overlaps(obj.getRectangle())) {
                collisions.add(entity);
            }
        }

        return collisions;
    }

    public void clear() {
        entities.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return entities.iterator();
    }
}
