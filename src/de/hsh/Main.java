package de.hsh;

import de.hsh.app.MainFrame;
import javax.swing.SwingUtilities;

/**
 * The Main class serves as the entry point of the application.
 * It initializes the main application frame and ensures
 * that it is executed on the Event Dispatch Thread (EDT) to
 * maintain thread safety in Swing applications.
 *
 * @author bashistha
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}