package de.hsh.gui.effects;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all active animations and effects in the game.
 */
public class AnimationManager {
    private List<ParticleEffect> particleEffects;
    private SliceEffect currentSliceEffect;

    public AnimationManager() {
        this.particleEffects = new ArrayList<>();
        this.currentSliceEffect = new SliceEffect(new Color(0, 255, 255, 200));
    }

    public void addParticleEffect(float x, float y, Color color, int particleCount) {
        particleEffects.add(new ParticleEffect(x, y, color, particleCount));
    }

    public void addSlicePoint(int x, int y) {
        currentSliceEffect.addPoint(x, y);
    }

    public void resetSliceEffect() {
        currentSliceEffect.reset();
    }

    public void update() {
        // Update particle effects
        for (int i = particleEffects.size() - 1; i >= 0; i--) {
            ParticleEffect effect = particleEffects.get(i);
            effect.update();
            if (effect.isFinished()) {
                particleEffects.remove(i);
            }
        }
    }

    public void render(Graphics2D g2) {
        // Render particle effects
        for (ParticleEffect effect : particleEffects) {
            effect.render(g2);
        }

        // Render slice effect
        currentSliceEffect.render(g2);
    }

    public void clear() {
        particleEffects.clear();
        currentSliceEffect.reset();
    }
}
