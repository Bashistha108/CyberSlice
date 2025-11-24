package de.hsh.gui.effects;

import java.awt.*;

/**
 * Represents a single particle in an explosion effect.
 */
public class Particle {
    private float x, y;
    private float vx, vy;
    private float life;
    private float maxLife;
    private Color color;
    private float size;

    public Particle(float x, float y, float vx, float vy, Color color, float size, float life) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.color = color;
        this.size = size;
        this.life = life;
        this.maxLife = life;
    }

    public void update(float deltaTime) {
        // Apply velocity
        x += vx * deltaTime;
        y += vy * deltaTime;

        // Apply gravity
        vy += 300 * deltaTime;

        // Apply air resistance
        vx *= 0.98f;
        vy *= 0.98f;

        // Decrease life
        life -= deltaTime;
    }

    public void render(Graphics2D g2) {
        if (life <= 0)
            return;

        float alpha = Math.max(0, Math.min(1, life / maxLife));
        Color particleColor = new Color(
                color.getRed() / 255f,
                color.getGreen() / 255f,
                color.getBlue() / 255f,
                alpha);

        g2.setColor(particleColor);
        int renderSize = (int) (size * (life / maxLife));
        g2.fillOval((int) x - renderSize / 2, (int) y - renderSize / 2, renderSize, renderSize);
    }

    public boolean isDead() {
        return life <= 0;
    }
}
