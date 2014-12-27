package com.spacefist.particleengine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.spacefist.GameData;

import java.util.ArrayList;
import java.util.List;

/*
*         Examples:
*
*          plasmaBallEmitter = new ParticleEmitter(
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
*/
public class ParticleEmitter {
    private Vector2         position;
    private List<Particle>  particles;
    private long            creation;
    private int             maxParticles;
    private int             freq;
    private Vector2         center;
    private SpriteBatch     spriteBatch;
    private boolean         alive;
    private long            lastEmission;
    private Texture         texture;
    private ParticleOptions particleOptions;
    private GameData        gameData;

    @SuppressWarnings("UnnecessaryThis")
    public ParticleEmitter(
        GameData        gameData,
        Texture         texture,
        int             maxParticles,
        int             freq,
        Vector2         center,
        SpriteBatch     spriteBatch,
        ParticleOptions particleOptions
    ) {
        this.gameData = gameData;

        particles            = new ArrayList<Particle>();
        this.center          = center;
        this.spriteBatch     = spriteBatch;
        alive                = true;
        this.creation        = TimeUtils.millis();
        lastEmission         = TimeUtils.millis();
        this.maxParticles    = maxParticles;
        this.freq            = freq;
        this.texture         = texture;
        this.particleOptions = particleOptions;
    }

    public long getCreation() {
        return creation;
    }

    public void update() {
        if (alive) {
            // add more particles if needed
            if ((particles.size() < maxParticles) && (((TimeUtils.millis() - lastEmission) / 1000) > freq)) {
                for (int i = 0; i < 3; i++) {
                    int degrees = MathUtils.random(particleOptions.getMinRotation(), particleOptions.getMaxRotation());

                    float   speed    = particleOptions.getSpeed();
                    Vector2 velocity = new Vector2((float) (speed * Math.cos(degrees)), (float) (speed * Math.sin(degrees)));
                    float rotation = (float) Math.toRadians(MathUtils.random(particleOptions.getMinRotation(), particleOptions.getMaxRotation()));
                    float angularVelocity = MathUtils.random(particleOptions.getMinAngularVelocity(), particleOptions.getMaxAngularVelocity());
                    float scale = MathUtils.random(((int) particleOptions.getMinScale()), (int) particleOptions.getMaxScale());

                    particles.add(
                        new Particle(
                            texture,
                            scale,
                            rotation,
                            angularVelocity,
                            velocity,
                            particleOptions.getTtl(),
                            Color.WHITE, position
                        )
                    );
                }

                lastEmission = TimeUtils.millis();
            }

            // update all particles
            for (Particle particle : particles) {
                particle.update();
            }

            List<Particle> particlesToRemove = new ArrayList<Particle>();

            for (Particle particle : particles) {
                if (((TimeUtils.millis() - particle.getCreation()) / 1000) > particle.getTtl()) {
                    particlesToRemove.add(particle);
                }
            }

            // remove expired particles
            for (Particle particle : particlesToRemove) {
                particles.remove(particle);
            }
        }
    }

    public void draw() {
        if (alive) {
            for (Particle particle : particles) {

                // draw each particle (particles fade as they reach ttl
                float transparency = (float) ((TimeUtils.millis() - particle.getCreation()) / 1000) / particle.getTtl();

                Vector2 drawAt = new Vector2(particle.getX(), particle.getY()).sub(gameData.getCamera());
                Color   color  = new Color(particle.getTint().r, particle.getTint().g, particle.getTint().b, transparency);

                spriteBatch.draw(
                        new TextureRegion(particle.getTexture()),
                        drawAt.x,
                        drawAt.y,
                        particle.getTexture().getWidth() / 2,
                        particle.getTexture().getHeight() / 2,
                        particle.getTexture().getWidth(),
                        particle.getTexture().getHeight(),
                        particle.getScale(),
                        particle.getScale(),
                        particle.getRotation(),
                        false
                );
            }
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
