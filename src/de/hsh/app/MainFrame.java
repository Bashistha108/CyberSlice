package de.hsh.app;
import de.hsh.gui.GUIController;
import de.hsh.gui.SettingsPanel;
import de.hsh.gui.SpielPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout; // Benötigt für das Menu
import java.awt.GridBagConstraints;
import java.awt.Insets;
import de.hsh.app.Level; // Level muss aus dem app-Paket importiert werden


/**
 * Das Hauptfenster (JFrame) der Anwendung.
 * Es verwendet ein CardLayout, um zwischen den verschiedenen Ansichten zu wechseln (Menu, Spiel, Einstellungen).
 */
public class MainFrame extends JFrame {
    private GUIController guiController;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        super("Cyber Slice - Clean Your System!");

        // 1. Controller initialisieren (braucht Referenz auf dieses Frame)
        this.guiController = new GUIController(this);

        // 2. Fassade und Model holen (muss aus dem gleichen Paket sein, da es keine Impl. ist)
        CyberSliceFassade fassade = guiController.getAppFassade();
        GameModel gameModel = fassade.getGameModel();

        // 3. Haupt-Layout (CardLayout)
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 4. Panels erstellen
        JPanel hauptMenuPanel = createHauptMenuPanel();
        SpielPanel spielPanel = new SpielPanel(gameModel, guiController);
        SettingsPanel settingsPanel = new SettingsPanel(guiController);

        // 5. Panels zum CardLayout hinzufügen
        cardPanel.add(hauptMenuPanel, "Hauptmenü");
        cardPanel.add(spielPanel, "Spiel");
        cardPanel.add(settingsPanel, "Einstellungen");

        // 6. Menü-Leiste
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Spiel");
        JMenuItem exitItem = new JMenuItem("Beenden");
        exitItem.addActionListener(e -> guiController.onExitClicked());
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);

        // 7. Frame konfigurieren
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(cardPanel);
        this.pack();
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
    }

    /**
     * Erstellt das Hauptmenü-Panel gemäß Mockup [cite: 2846-2851].
     */
    private JPanel createHauptMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Titel
        // TODO: JLabel mit Titel "Cyber Slice - Clean Your System!" einfügen

        // Play-Button
        JButton playButton = new JButton("Play (Start EASY)");
        // WICHTIG: Verwende den korrekten Level-Enum aus dem App-Paket
        playButton.addActionListener(e -> guiController.onSpielStartenClicked(Level.EASY));
        panel.add(playButton, gbc);

        // Level-Auswahl-Button
        JButton levelButton = new JButton("Level Auswählen");
        levelButton.addActionListener(e -> guiController.onLevelAuswahlClicked());
        panel.add(levelButton, gbc);

        // Einstellungen-Button
        JButton settingsButton = new JButton("Einstellungen");
        settingsButton.addActionListener(e -> showPanel("Einstellungen"));
        panel.add(settingsButton, gbc);

        // Anleitung-Button
        JButton anleitungButton = new JButton("Anleitung (?)");
        anleitungButton.addActionListener(e -> guiController.onAnleitungClicked());
        panel.add(anleitungButton, gbc);

        // Exit-Button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> guiController.onExitClicked());
        panel.add(exitButton, gbc);

        return panel;
    }

    /**
     * Methode zum Wechseln der Ansicht (CardLayout).
     */
    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }
}