package com.spacefist.particleengine;

public class ParticleOptions
{
    private float  speed;
    private int    ttl;
    private int    minRotation;
    private int    maxRotation;
    private int    minAngularVelocity;
    private int    maxAngularVelocity;
    private float  minScale;
    private float  maxScale;


    public ParticleOptions(
            float speed,              int   ttl,
            int   minRotation,        int   maxRotation,
            int   minAngularVelocity, int   maxAngularVelocity,
            float minScale,           float maxScale)
    {
        this.speed              = speed;
        this.ttl                = ttl;
        this.minRotation        = minRotation;
        this.maxRotation        = maxRotation;
        this.minAngularVelocity = minAngularVelocity;
        this.maxAngularVelocity = maxAngularVelocity;
        this.minScale           = minScale;
        this.maxScale           = maxScale;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public int getMinRotation() {
        return minRotation;
    }

    public void setMinRotation(int minRotation) {
        this.minRotation = minRotation;
    }

    public int getMaxRotation() {
        return maxRotation;
    }

    public void setMaxRotation(int maxRotation) {
        this.maxRotation = maxRotation;
    }

    public int getMinAngularVelocity() {
        return minAngularVelocity;
    }

    public void setMinAngularVelocity(int minAngularVelocity) {
        this.minAngularVelocity = minAngularVelocity;
    }

    public int getMaxAngularVelocity() {
        return maxAngularVelocity;
    }

    public void setMaxAngularVelocity(int maxAngularVelocity) {
        this.maxAngularVelocity = maxAngularVelocity;
    }

    public float getMinScale() {
        return minScale;
    }

    public void setMinScale(float minScale) {
        this.minScale = minScale;
    }

    public float getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(float maxScale) {
        this.maxScale = maxScale;
    }
}
