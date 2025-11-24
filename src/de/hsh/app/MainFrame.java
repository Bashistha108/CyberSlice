package de.hsh.app;

import de.hsh.gui.GUIController;
import de.hsh.gui.SettingsPanel;
import de.hsh.gui.SpielPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Das Hauptfenster (JFrame) der Anwendung.
 * Es verwendet ein CardLayout, um zwischen den verschiedenen Ansichten zu
 * wechseln.
 */
public class MainFrame extends JFrame {
    private GUIController guiController;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        super("Cyber Slice - Clean Your System!");

        // 1. Controller initialisieren
        this.guiController = new GUIController(this);

        // 2. Fassade und Model holen
        CyberSliceFassade fassade = guiController.getAppFassade();
        GameModel gameModel = fassade.getGameModel();

        // Update screen dimensions in game controller
        fassade.getGameController().setScreenDimensions(800, 600);

        // 3. Haupt-Layout (CardLayout)
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 4. Panels erstellen
        JPanel hauptMenuPanel = createHauptMenuPanel();
        SpielPanel spielPanel = new SpielPanel(gameModel, guiController);
        SettingsPanel settingsPanel = new SettingsPanel(guiController);
        JPanel levelAuswahlPanel = createLevelAuswahlPanel();
        JPanel anleitungPanel = createAnleitungPanel();

        // 5. Panels zum CardLayout hinzufügen
        cardPanel.add(hauptMenuPanel, "Hauptmenü");
        cardPanel.add(spielPanel, "Spiel");
        cardPanel.add(settingsPanel, "Einstellungen");
        cardPanel.add(levelAuswahlPanel, "LevelAuswahl");
        cardPanel.add(anleitungPanel, "Anleitung");

        // 6. Menü-Leiste
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> guiController.onExitClicked());
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);

        // 7. Frame konfigurieren
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(cardPanel);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    /**
     * Erstellt das Hauptmenü-Panel mit verbessertem Design.
     */
    private JPanel createHauptMenuPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(20, 20, 50),
                        0, getHeight(), new Color(50, 20, 80));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("CYBER SLICE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(new Color(0, 255, 255));
        panel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Clean Your System!");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        subtitleLabel.setForeground(new Color(200, 200, 255));
        panel.add(subtitleLabel, gbc);

        gbc.insets = new Insets(30, 50, 5, 50);

        // Play Button
        JButton playButton = createStyledButton("▶ PLAY (EASY)", new Color(50, 150, 50));
        playButton.addActionListener(e -> guiController.onSpielStartenClicked(Level.EASY));
        panel.add(playButton, gbc);

        gbc.insets = new Insets(5, 50, 5, 50);

        // Level Selection Button
        JButton levelButton = createStyledButton("🎯 SELECT LEVEL", new Color(100, 100, 200));
        levelButton.addActionListener(e -> guiController.onLevelAuswahlClicked());
        panel.add(levelButton, gbc);

        // Settings Button
        JButton settingsButton = createStyledButton("⚙ SETTINGS", new Color(150, 100, 50));
        settingsButton.addActionListener(e -> showPanel("Einstellungen"));
        panel.add(settingsButton, gbc);

        // Instructions Button
        JButton anleitungButton = createStyledButton("❓ INSTRUCTIONS", new Color(100, 150, 150));
        anleitungButton.addActionListener(e -> guiController.onAnleitungClicked());
        panel.add(anleitungButton, gbc);

        // Exit Button
        JButton exitButton = createStyledButton("✖ EXIT", new Color(150, 50, 50));
        exitButton.addActionListener(e -> guiController.onExitClicked());
        panel.add(exitButton, gbc);

        return panel;
    }

    /**
     * Creates level selection panel.
     */
    private JPanel createLevelAuswahlPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(20, 20, 50),
                        0, getHeight(), new Color(50, 20, 80));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("SELECT LEVEL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 255, 255));
        panel.add(titleLabel, gbc);

        gbc.insets = new Insets(30, 50, 10, 50);

        // Easy Level
        JButton easyButton = createStyledButton("🟢 EASY", new Color(50, 150, 50));
        easyButton.addActionListener(e -> guiController.onSpielStartenClicked(Level.EASY));
        panel.add(easyButton, gbc);

        gbc.insets = new Insets(10, 50, 10, 50);

        // Intermediate Level
        JButton intermediateButton = createStyledButton("🟡 INTERMEDIATE", new Color(200, 150, 50));
        intermediateButton.addActionListener(e -> guiController.onSpielStartenClicked(Level.INTERMEDIATE));
        panel.add(intermediateButton, gbc);

        // Master Level
        JButton masterButton = createStyledButton("🔴 MASTER", new Color(150, 50, 50));
        masterButton.addActionListener(e -> guiController.onSpielStartenClicked(Level.MASTER));
        panel.add(masterButton, gbc);

        gbc.insets = new Insets(30, 50, 10, 50);

        // Back Button
        JButton backButton = createStyledButton("← BACK", new Color(100, 100, 100));
        backButton.addActionListener(e -> showPanel("Hauptmenü"));
        panel.add(backButton, gbc);

        return panel;
    }

    /**
     * Creates instructions panel.
     */
    private JPanel createAnleitungPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(20, 20, 50),
                        0, getHeight(), new Color(50, 20, 80));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("HOW TO PLAY", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 255, 255));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea instructionsArea = new JTextArea();
        instructionsArea.setEditable(false);
        instructionsArea.setOpaque(false);
        instructionsArea.setForeground(Color.WHITE);
        instructionsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsArea.setText(
                "\n  🎯 OBJECTIVE:\n" +
                        "  Slice viruses and avoid slicing antiviruses and files!\n\n" +
                        "  🖱 CONTROLS:\n" +
                        "  • Drag your mouse across objects to slice them\n" +
                        "  • Watch for the slice trail effect\n\n" +
                        "  📦 OBJECTS:\n" +
                        "  • V (Red) - Virus: +10 points\n" +
                        "  • UV (Orange) - Ultra Virus: +20 points, +1 life\n" +
                        "  • A (Green) - Antivirus: -10 points, -1 life (DON'T SLICE!)\n" +
                        "  • UA (Dark Green) - Ultra Antivirus: -20 points, -2 lives (DON'T SLICE!)\n" +
                        "  • F (Gray) - File: -5 points (DON'T SLICE!)\n" +
                        "  • ⏰ (Cyan) - Clock: +10 seconds\n" +
                        "  • ⚡ (Gold) - USB Stick: Activates POWER MODE (2x points!)\n\n" +
                        "  ⚠ WARNING:\n" +
                        "  • You lose a life if a virus falls off the screen\n" +
                        "  • Game ends when time runs out or you lose all lives\n\n" +
                        "  Good luck, and clean that system! 💻");
        instructionsArea.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.add(instructionsArea, BorderLayout.CENTER);

        JButton backButton = createStyledButton("← BACK TO MENU", new Color(100, 100, 100));
        backButton.addActionListener(e -> showPanel("Hauptmenü"));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    /**
     * Methode zum Wechseln der Ansicht (CardLayout).
     */
    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }
}