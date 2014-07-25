package com.spacefist.particleengine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Date;

public class Particle
{
    private Texture texture;

    private float    scale;
    private float    rotation;
    private float    angularVelocity;
    private Vector2  velocity;
    private Date     creation;
    private int      ttl;
    private Color    tint;
    private Vector2  originalPos;
    private Vector2  position;

    public Particle(
            Texture texture, float scale, float rotation, float angularVelocity,
            Vector2 velocity, int ttl, Color tint, Vector2 originalPos)
    {
        this.originalPos     = originalPos;
        this.position        = originalPos;
        this.texture         = texture;
        this.scale           = scale;
        this.rotation        = rotation;
        this.angularVelocity = angularVelocity;
        this.velocity        = velocity;
        this.creation        = new Date();
        this.ttl = ttl;
        this.tint = tint;

    }

    public void Update()
{
    position = new Vector2(position.x + getVelocity().x, position.y + getVelocity().y);
    rotation = (float) Math.toRadians(Math.toDegrees(getRotation()) + getAngularVelocity());
}

    public Texture getTexture() {
        return texture;
    }

    public float getScale() {
        return scale;
    }

    public float getRotation() {
        return rotation;
    }

    public float getAngularVelocity() {
        return angularVelocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Date getCreation() {
        return creation;
    }

    public int getTtl() {
        return ttl;
    }

    public Color getTint() {
        return tint;
    }

    public int getX() {
        return (int) position.x;
    }

    public int getY() {
        return (int) position.y;
    }
}