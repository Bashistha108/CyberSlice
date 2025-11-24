package de.hsh.gui;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private GUIController guiController;
    private JTextField nameField;
    private JButton saveButton;
    private JButton backButton;
    private JSlider volumeSlider;

    public SettingsPanel(GUIController guiController) {
        this.guiController = guiController;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("SETTINGS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(0, 255, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(30, 10, 5, 10);

        // Player Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Player Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(nameField, gbc);

        // Volume
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.insets = new Insets(5, 10, 5, 10);
        JLabel volumeLabel = new JLabel("Volume:");
        volumeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(volumeLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        volumeSlider = new JSlider(0, 100, 75);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        add(volumeSlider, gbc);

        // Buttons panel
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        saveButton = createStyledButton("💾 SAVE", new Color(50, 150, 50));
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            float volume = volumeSlider.getValue() / 100.0f;
            guiController.onSaveSettingsClicked(name, volume);
        });
        buttonPanel.add(saveButton);

        backButton = createStyledButton("← BACK", new Color(100, 100, 100));
        backButton.addActionListener(e -> {
            guiController.getAppFassade().getGameModel().setChanged();
            guiController.getAppFassade().getGameModel().notifyObservers();
            ((JFrame) SwingUtilities.getWindowAncestor(this)).getContentPane()
                    .getComponent(0); // Trigger repaint
            CardLayout cl = (CardLayout) getParent().getLayout();
            cl.show(getParent(), "Hauptmenü");
        });
        buttonPanel.add(backButton);

        add(buttonPanel, gbc);
    }

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

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
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
}