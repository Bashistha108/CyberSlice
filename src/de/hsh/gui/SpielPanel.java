package de.hsh.gui;

import de.hsh.app.GameModel;
import de.hsh.app.SpielObjekt;
import de.hsh.app.objects.*;
import de.hsh.gui.effects.AnimationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Observable;
import java.util.Observer;

public class SpielPanel extends JPanel implements Observer {
    private GameModel gameModel;
    private GUIController guiController;
    private AnimationManager animationManager;
    private JButton pauseButton;
    private JButton backButton;
    private JButton restartButton;
    private JButton menuButtonGameOver;
    private boolean isPaused = false;
    private boolean gameOverButtonsAdded = false;

    public SpielPanel(GameModel gameModel, GUIController guiController) {
        this.gameModel = gameModel;
        this.guiController = guiController;
        this.animationManager = new AnimationManager();

        setLayout(null); // Use absolute positioning for buttons

        this.gameModel.addObserver(this);

        // Mouse listener for slicing
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private boolean isDragging = false;

            @Override
            public void mousePressed(MouseEvent e) {
                isDragging = true;
                animationManager.resetSliceEffect();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging && !isPaused) {
                    animationManager.addSlicePoint(e.getX(), e.getY());
                    handleSlice(e.getX(), e.getY());
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);

        // Create pause button
        pauseButton = new JButton("Pause");
        pauseButton.setBounds(10, 120, 100, 35);
        styleButton(pauseButton, new Color(100, 100, 255));
        pauseButton.addActionListener(e -> togglePause());
        add(pauseButton);

        // Create back to menu button
        backButton = new JButton("Menu");
        backButton.setBounds(10, 165, 100, 35);
        styleButton(backButton, new Color(255, 100, 100));
        backButton.addActionListener(e -> {
            guiController.onBackToMenuClicked();
        });
        add(backButton);

        // Create game over buttons (initially hidden)
        restartButton = new JButton("Restart");
        restartButton.setBounds(getWidth() / 2 - 110, getHeight() / 2 + 80, 100, 40);
        styleButton(restartButton, new Color(50, 150, 50));
        restartButton.addActionListener(e -> {
            hideGameOverButtons();
            guiController.onRestartClicked();
        });
        restartButton.setVisible(false);
        add(restartButton);

        menuButtonGameOver = new JButton("Menu");
        menuButtonGameOver.setBounds(getWidth() / 2 + 10, getHeight() / 2 + 80, 100, 40);
        styleButton(menuButtonGameOver, new Color(150, 50, 50));
        menuButtonGameOver.addActionListener(e -> {
            hideGameOverButtons();
            guiController.onBackToMenuClicked();
        });
        menuButtonGameOver.setVisible(false);
        add(menuButtonGameOver);

        // Animation timer
        Timer animationTimer = new Timer(16, e -> {
            animationManager.update();
            updateGameOverButtons();

            repaint();
        });
        animationTimer.start();
    }

    private void updateGameOverButtons() {
        boolean isGameOver = guiController.getAppFassade().getGameController().isGameOver();
        if (isGameOver && !gameOverButtonsAdded) {
            showGameOverButtons();
        } else if (!isGameOver && gameOverButtonsAdded) {
            hideGameOverButtons();
        }
    }

    private void showGameOverButtons() {
        gameOverButtonsAdded = true;
        restartButton.setVisible(true);
        menuButtonGameOver.setVisible(true);
        // Update button positions based on current size
        restartButton.setBounds(getWidth() / 2 - 110, getHeight() / 2 + 80, 100, 40);
        menuButtonGameOver.setBounds(getWidth() / 2 + 10, getHeight() / 2 + 80, 100, 40);
    }

    private void hideGameOverButtons() {
        gameOverButtonsAdded = false;
        restartButton.setVisible(false);
        menuButtonGameOver.setVisible(false);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            guiController.onPauseClicked();
            pauseButton.setText("Resume");
        } else {
            guiController.onResumeClicked();
            pauseButton.setText("Pause");
        }
    }

    private void handleSlice(int x, int y) {
        for (int i = gameModel.getObjektListe().size() - 1; i >= 0; i--) {
            SpielObjekt obj = gameModel.getObjektListe().get(i);
            if (obj.isClicked(x, y)) {
                // Create particle effect
                Color particleColor = getObjectColor(obj);
                animationManager.addParticleEffect(
                        obj.getPosX() + obj.getWidth() / 2,
                        obj.getPosY() + obj.getHeight() / 2,
                        particleColor,
                        15);

                // Handle the slice
                guiController.onMausBewegung(x, y);
                break;
            }
        }
    }

    private Color getObjectColor(SpielObjekt obj) {
        if (obj instanceof Virus)
            return Virus.getColor();
        if (obj instanceof UltraVirus)
            return UltraVirus.getColor();
        if (obj instanceof Antivirus)
            return Antivirus.getColor();
        if (obj instanceof UltraAntivirus)
            return UltraAntivirus.getColor();
        if (obj instanceof Datei)
            return Datei.getColor();
        if (obj instanceof Uhr)
            return Uhr.getColor();
        if (obj instanceof USBStick)
            return USBStick.getColor();
        return Color.WHITE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Background gradient
            GradientPaint bgGradient = new GradientPaint(
                    0, 0, new Color(10, 10, 30),
                    0, getHeight(), new Color(30, 10, 50));
            g2.setPaint(bgGradient);
            g2.fillRect(0, 0, getWidth(), getHeight());

            // Draw game objects with enhanced visuals
            for (SpielObjekt obj : gameModel.getObjektListe()) {
                drawEnhancedObject(g2, obj);
            }

            // Draw animations
            animationManager.render(g2);

            // Draw HUD
            drawHUD(g2);

            // Draw game over overlay if needed
            if (guiController.getAppFassade().getGameController().isGameOver()) {
                drawGameOverOverlay(g2);
            }

        } finally {
            g2.dispose();
        }
    }

    private void drawEnhancedObject(Graphics2D g2, SpielObjekt obj) {
        int x = (int) obj.getPosX();
        int y = (int) obj.getPosY();
        int size = obj.getWidth();

        Color baseColor = getObjectColor(obj);

        // Draw shadow
        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillOval(x + 3, y + 3, size, size);

        // Draw gradient fill
        RadialGradientPaint gradient = new RadialGradientPaint(
                x + size / 2, y + size / 2, size / 2,
                new float[] { 0.0f, 0.7f, 1.0f },
                new Color[] {
                        brighten(baseColor, 1.3f),
                        baseColor,
                        darken(baseColor, 0.7f)
                });
        g2.setPaint(gradient);
        g2.fillOval(x, y, size, size);

        // Draw outline
        g2.setColor(brighten(baseColor, 1.5f));
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x, y, size, size);

        // Draw label
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        String label = getObjectLabel(obj);
        FontMetrics fm = g2.getFontMetrics();
        int labelWidth = fm.stringWidth(label);
        g2.drawString(label, x + (size - labelWidth) / 2, y + size / 2 + 4);
    }

    private String getObjectLabel(SpielObjekt obj) {
        if (obj instanceof Virus)
            return "V";
        if (obj instanceof UltraVirus)
            return "UV";
        if (obj instanceof Antivirus)
            return "A";
        if (obj instanceof UltraAntivirus)
            return "UA";
        if (obj instanceof Datei)
            return "F";
        if (obj instanceof Uhr)
            return "⏰";
        if (obj instanceof USBStick)
            return "⚡";
        return "?";
    }

    private Color brighten(Color color, float factor) {
        int r = Math.min(255, (int) (color.getRed() * factor));
        int g = Math.min(255, (int) (color.getGreen() * factor));
        int b = Math.min(255, (int) (color.getBlue() * factor));
        return new Color(r, g, b);
    }

    private Color darken(Color color, float factor) {
        int r = (int) (color.getRed() * factor);
        int g = (int) (color.getGreen() * factor);
        int b = (int) (color.getBlue() * factor);
        return new Color(r, g, b);
    }

    private void drawHUD(Graphics2D g2) {
        // HUD background panel
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fill(new RoundRectangle2D.Float(5, 5, 200, 110, 15, 15));

        // HUD border
        g2.setColor(new Color(100, 200, 255, 200));
        g2.setStroke(new BasicStroke(2));
        g2.draw(new RoundRectangle2D.Float(5, 5, 200, 110, 15, 15));

        g2.setFont(new Font("Arial", Font.BOLD, 16));

        // Points
        g2.setColor(new Color(255, 215, 0));
        g2.drawString("⭐ Points: " + gameModel.getPunkte(), 15, 25);

        // Lives
        g2.setColor(new Color(255, 100, 100));
        String hearts = "❤".repeat(Math.max(0, gameModel.getLeben()));
        g2.drawString("Lives: " + hearts, 15, 50);

        // Time
        g2.setColor(new Color(100, 200, 255));
        g2.drawString("⏱ Time: " + gameModel.getZeit() + "s", 15, 75);

        // Level
        g2.setColor(new Color(200, 200, 200));
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.drawString("Level: " + gameModel.getLevel().name(), 15, 100);

        // Power mode indicator
        if (gameModel.isPowerMode()) {
            g2.setColor(new Color(255, 215, 0, 200));
            g2.fill(new RoundRectangle2D.Float(220, 10, 150, 40, 10, 10));
            g2.setColor(new Color(255, 100, 0));
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString("⚡ POWER MODE!", 230, 35);
        }
    }

    private void drawGameOverOverlay(Graphics2D g2) {
        // Semi-transparent overlay
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Game Over text
        g2.setColor(new Color(255, 50, 50));
        g2.setFont(new Font("Arial", Font.BOLD, 60));
        String gameOverText = "GAME OVER";
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(gameOverText);
        g2.drawString(gameOverText, (getWidth() - textWidth) / 2, getHeight() / 2 - 50);

        fm = g2.getFontMetrics();
        // Final score
        g2.setColor(new Color(255, 215, 0));
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        String scoreText = "Final Score: " + gameModel.getPunkte();
        textWidth = fm.stringWidth(scoreText);
        g2.drawString(scoreText, (getWidth() - textWidth) / 2, getHeight() / 2 + 20);

        // Instructions
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        String instruction = "Click 'Menu' to return";
        fm = g2.getFontMetrics();
        textWidth = fm.stringWidth(instruction);
        g2.drawString(instruction, (getWidth() - textWidth) / 2, getHeight() / 2 + 70);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == gameModel) {
            this.repaint();
        }
    }
}