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


/**
 * Represents the main game panel responsible for rendering the current game state,
 * handling user interactions, and managing various game components, animations,
 * and game objects. The SpielPanel observes changes in the GameModel and updates the view accordingly.
 * It also interacts with the GUIController to handle game logic and game state transitions.
 *
 */
public class SpielPanel extends JPanel implements Observer {
    private GameModel gameModel;
    private GUIController guiController;
    private AnimationManager animationManager;
    private JButton pauseButton;
    private JButton backButton;
    private boolean isPaused = false;
    private Image virusImage;
    private Image antivirusImage;
    private Image clockImage;
    private Image fileImage;
    private Image powerImage;
    private Image lifeImage;

    public SpielPanel(GameModel gameModel, GUIController guiController) {
        this.gameModel = gameModel;
        this.guiController = guiController;
        this.animationManager = new AnimationManager();

        // Load virus image
        try {
            java.net.URL imgUrl = getClass().getResource("/de/hsh/images/virus.png");
            if (imgUrl != null) {
                virusImage = javax.imageio.ImageIO.read(imgUrl);
            } else {
                System.err.println("Virus image not found at /de/hsh/images/virus.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load antivirus image
        try {
            java.net.URL imgUrlAntivirus = getClass().getResource("/de/hsh/images/antivirus.png");
            if (imgUrlAntivirus != null) {
                antivirusImage = javax.imageio.ImageIO.read(imgUrlAntivirus);
                System.out.println("DEBUG: Antivirus image loaded successfully.");
            } else {
                System.err.println("Antivirus image not found at /de/hsh/images/antivirus.png");
            }
        } catch (Exception e) {
            System.err.println("Error loading antivirus image:");
            e.printStackTrace();
            System.err.println("Error loading antivirus image:");
            e.printStackTrace();
        }

        // Load clock image
        try {
            java.net.URL imgUrl = getClass().getResource("/de/hsh/images/clock.png");
            if (imgUrl != null)
                clockImage = javax.imageio.ImageIO.read(imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load file image
        try {
            java.net.URL imgUrl = getClass().getResource("/de/hsh/images/file.png");
            if (imgUrl != null)
                fileImage = javax.imageio.ImageIO.read(imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load power image
        try {
            java.net.URL imgUrl = getClass().getResource("/de/hsh/images/power.png");
            if (imgUrl != null)
                powerImage = javax.imageio.ImageIO.read(imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load life image
        try {
            java.net.URL imgUrl = getClass().getResource("/de/hsh/images/life.png");
            if (imgUrl != null)
                lifeImage = javax.imageio.ImageIO.read(imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

                    // Play slice sound
                    guiController.getAppFassade().getGameController().playSliceSound();
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

        // Game Over buttons (initially hidden)
        JButton restartButton = new JButton("RESTART");
        restartButton.setBounds(getWidth() / 2 - 220, getHeight() / 2 + 80, 200, 50);
        styleButton(restartButton, new Color(50, 150, 50));
        restartButton.addActionListener(e -> {
            guiController.onRestartGameClicked();
        });
        restartButton.setVisible(false);
        add(restartButton);

        JButton menuButton = new JButton("MENU");
        menuButton.setBounds(getWidth() / 2 + 20, getHeight() / 2 + 80, 200, 50);
        styleButton(menuButton, new Color(150, 50, 50));
        menuButton.addActionListener(e -> {
            restartButton.setVisible(false);
            menuButton.setVisible(false);
            guiController.onBackToMenuClicked();
        });
        menuButton.setVisible(false);
        add(menuButton);

        // Animation timer
        Timer animationTimer = new Timer(16, e -> {
            animationManager.update();

            // Show/hide game over buttons based on game state
            boolean gameOver = guiController.getAppFassade().getGameController().isGameOver();
            restartButton.setVisible(gameOver);
            menuButton.setVisible(gameOver);

            // Update button positions based on current panel size
            if (gameOver) {
                restartButton.setBounds(getWidth() / 2 - 220, getHeight() / 2 + 80, 200, 50);
                menuButton.setBounds(getWidth() / 2 + 20, getHeight() / 2 + 80, 200, 50);
            }

            repaint();
        });
        animationTimer.start();
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
              //  Color particleColor = getObjectColor(obj);
                animationManager.addParticleEffect(
                        obj.getPosX() + obj.getWidth() / 2,
                        obj.getPosY() + obj.getHeight() / 2,
                        Color.blue,
                        15);

                // Handle the slice
                guiController.onMausBewegung(x, y);
                break;
            }
        }
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
                    0, 0, new Color(245, 245, 255),
                    0, getHeight(), new Color(220, 230, 240));
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

        // Use image for Virus and UltraVirus if loaded
        if (virusImage != null && (obj instanceof Virus || obj instanceof UltraVirus)) {
            g2.drawImage(virusImage, x, y, size, size, null);

            return;
        }

        // Use image for Antivirus and UltraAntivirus if loaded
        if (antivirusImage != null && (obj instanceof Antivirus || obj instanceof UltraAntivirus)) {
            g2.drawImage(antivirusImage, x, y, size, size, null);

            return;
        }

        // Use image for Clock
        if (clockImage != null && obj instanceof Uhr) {
            g2.drawImage(clockImage, x, y, size, size, null);
            return;
        }

        // Use image for File
        if (fileImage != null && obj instanceof Datei) {
            g2.drawImage(fileImage, x, y, size, size, null);
            return;
        }

        // Use image for USBStick (Power)
        if (powerImage != null && obj instanceof USBStick) {
            g2.drawImage(powerImage, x, y, size, size, null);
            return;
        }

        // Use image for Life
        if (lifeImage != null && obj instanceof Life) {
            g2.drawImage(lifeImage, x, y, size, size, null);
            return;
        }

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
        g2.drawString("Points: " + gameModel.getPunkte(), 15, 25);

        // Lives
        g2.setColor(new Color(255, 100, 100));
        String hearts = "+".repeat(Math.max(0, gameModel.getLeben()));
        g2.drawString("Lives: " + hearts, 15, 50);

        // Time
        g2.setColor(new Color(100, 200, 255));
        g2.drawString("Time: " + gameModel.getZeit() + "s", 15, 75);

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
            g2.drawString("POWER MODE!", 230, 35);
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
        if (gameModel.getZeit() <= 0) {
            gameOverText = "TIME UP";
        }
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(gameOverText);
        g2.drawString(gameOverText, (getWidth() - textWidth) / 2, getHeight() / 2 - 80);

        // Player name
        g2.setColor(new Color(200, 200, 255));
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        String playerText = gameModel.getPlayerName();
        fm = g2.getFontMetrics();
        textWidth = fm.stringWidth(playerText);
        g2.drawString(playerText, (getWidth() - textWidth) / 2, getHeight() / 2 - 30);

        // Final score
        g2.setColor(new Color(255, 215, 0));
        g2.setFont(new Font("Arial", Font.BOLD, 36));
        String scoreText = "Score: " + gameModel.getPunkte();
        fm = g2.getFontMetrics();
        textWidth = fm.stringWidth(scoreText);
        g2.drawString(scoreText, (getWidth() - textWidth) / 2, getHeight() / 2 + 20);

        // Level
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        String levelText = "Level: " + gameModel.getLevel().name();
        fm = g2.getFontMetrics();
        textWidth = fm.stringWidth(levelText);
        g2.drawString(levelText, (getWidth() - textWidth) / 2, getHeight() / 2 + 50);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == gameModel) {
            this.repaint();
        }
    }
}