package de.hsh.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.GridBagLayout; // [cite: 353, 2066]
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class SettingsPanel extends JPanel {
    private GUIController guiController;
    private JTextField nameField;
    private JButton saveButton;
    private JSlider volumeSlider;

    public SettingsPanel(GUIController guiController) {
        this.guiController = guiController;

        // Layout-Manager (GridBagLayout wird in den Doks empfohlen) [cite: 353, 2066]
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Komponenten initialisieren
        nameField = new JTextField(20);
        volumeSlider = new JSlider(0, 100, 75);
        saveButton = new JButton("Speichern");


        // Komponenten zum Panel hinzufügen
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Spielername:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("Lautstärke:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(volumeSlider, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            float volume = volumeSlider.getValue() / 100.0f;
            guiController.onSaveSettingsClicked(name, volume);
        });
    }
}