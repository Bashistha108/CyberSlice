package de.hsh.persistence;

import java.io.*;
import java.util.List;

/**
 * Concrete implementation of the PersistenceFassade.
 * Handles reading and writing UserSettings to a file using Java Serialization.
 */
public class SettingsBroker implements PersistenceFassade {

    private static final String FILE_NAME = "settings.ser";
    private LeaderboardBroker leaderboardBroker;

    public SettingsBroker() {
        this.leaderboardBroker = new LeaderboardBroker();
    }

    @Override
    public void saveSettings(UserSettings settings) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(settings);
            System.out.println("Settings saved successfully to " + FILE_NAME);

        } catch (IOException i) {
            System.err.println("Error saving settings: " + i.getMessage());
            i.printStackTrace();
        }
    }

    @Override
    public UserSettings loadSettings() {
        UserSettings settings = null;
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
                    ObjectInputStream in = new ObjectInputStream(fileIn)) {

                settings = (UserSettings) in.readObject();
                System.out.println("Settings loaded successfully.");

            } catch (IOException | ClassNotFoundException i) {
                System.err.println("Error loading settings: " + i.getMessage());
                i.printStackTrace();
            }
        } else {
            System.out.println("No settings file found. Using defaults.");
        }

        // Return default settings if loading failed or file didn't exist
        if (settings == null) {
            settings = new UserSettings("Player 1", 50.0f);
        }

        return settings;
    }

    @Override
    public boolean addScore(LeaderboardEntry entry) {
        return leaderboardBroker.addScore(entry);
    }

    @Override
    public List<LeaderboardEntry> getTop10() {
        return leaderboardBroker.getTop10();
    }
}
