package de.hsh.gui.effects;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages a particle explosion effect when objects are sliced.
 */
public class ParticleEffect {
    private List<Particle> particles;
    private long lastUpdateTime;
    private static final Random random = new Random();

    public ParticleEffect(float x, float y, Color color, int particleCount) {
        this.particles = new ArrayList<>();
        this.lastUpdateTime = System.currentTimeMillis();

        // Create particles with random velocities
        for (int i = 0; i < particleCount; i++) {
            float angle = (float) (Math.random() * Math.PI * 2);
            float speed = 100 + random.nextFloat() * 200;
            float vx = (float) Math.cos(angle) * speed;
            float vy = (float) Math.sin(angle) * speed - 100; // Bias upward

            float size = 4 + random.nextFloat() * 6;
            float life = 0.5f + random.nextFloat() * 0.5f;

            particles.add(new Particle(x, y, vx, vy, color, size, life));
        }
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - lastUpdateTime) / 1000.0f;
        lastUpdateTime = currentTime;

        // Update all particles
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.update(deltaTime);
            if (p.isDead()) {
                particles.remove(i);
            }
        }
    }

    public void render(Graphics2D g2) {
        for (Particle p : particles) {
            p.render(g2);
        }
    }

    public boolean isFinished() {
        return particles.isEmpty();
    }
}
