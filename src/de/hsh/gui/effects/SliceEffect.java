package de.hsh.gui.effects;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a slice trail effect that follows the mouse when slicing objects.
 */
public class SliceEffect {
    private List<Point2D> trailPoints;
    private long startTime;
    private static final long DURATION = 500; // milliseconds
    private Color color;
    private float maxAlpha = 0.8f;

    public SliceEffect(Color color) {
        this.trailPoints = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
        this.color = color;
    }

    public void addPoint(int x, int y) {
        trailPoints.add(new Point2D.Float(x, y));
        // Keep only last 20 points for performance
        if (trailPoints.size() > 20) {
            trailPoints.remove(0);
        }
    }

    public void render(Graphics2D g2) {
        if (trailPoints.size() < 2)
            return;

        long elapsed = System.currentTimeMillis() - startTime;
        float progress = Math.min(1.0f, elapsed / (float) DURATION);
        float alpha = maxAlpha * (1.0f - progress);

        if (alpha <= 0)
            return;

        // Create gradient stroke
        g2.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (int i = 0; i < trailPoints.size() - 1; i++) {
            Point2D p1 = trailPoints.get(i);
            Point2D p2 = trailPoints.get(i + 1);

            // Calculate alpha for this segment (fade from start to end)
            float segmentAlpha = alpha * (i / (float) trailPoints.size());
            Color segmentColor = new Color(
                    color.getRed() / 255f,
                    color.getGreen() / 255f,
                    color.getBlue() / 255f,
                    segmentAlpha);

            g2.setColor(segmentColor);
            g2.drawLine((int) p1.getX(), (int) p1.getY(),
                    (int) p2.getX(), (int) p2.getY());
        }
    }

    public boolean isExpired() {
        long elapsed = System.currentTimeMillis() - startTime;
        return elapsed >= DURATION;
    }

    public void reset() {
        trailPoints.clear();
        startTime = System.currentTimeMillis();
    }
}
