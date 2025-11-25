package de.hsh.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles persistence of leaderboard entries.
 */
public class LeaderboardBroker {

    private static final String FILE_NAME = "leaderboard.ser";
    private static final int MAX_ENTRIES = 10;

    /**
     * Loads the leaderboard from file.
     * 
     * @return List of leaderboard entries, or empty list if none exists
     */
    public List<LeaderboardEntry> loadLeaderboard() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
                    ObjectInputStream in = new ObjectInputStream(fileIn)) {

                leaderboard = (List<LeaderboardEntry>) in.readObject();
                System.out.println("Leaderboard loaded successfully.");

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading leaderboard: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No leaderboard file found. Starting fresh.");
        }

        return leaderboard;
    }

    /**
     * Saves the leaderboard to file.
     * 
     * @param leaderboard List of entries to save
     */
    public void saveLeaderboard(List<LeaderboardEntry> leaderboard) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(leaderboard);
            System.out.println("Leaderboard saved successfully.");

        } catch (IOException e) {
            System.err.println("Error saving leaderboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adds a new score to the leaderboard and maintains top 10.
     * 
     * @param entry The new entry to add
     * @return true if the entry made it to top 10, false otherwise
     */
    public boolean addScore(LeaderboardEntry entry) {
        List<LeaderboardEntry> leaderboard = loadLeaderboard();
        leaderboard.add(entry);
        Collections.sort(leaderboard);

        // Keep only top 10
        boolean madeIt = leaderboard.indexOf(entry) < MAX_ENTRIES;
        if (leaderboard.size() > MAX_ENTRIES) {
            leaderboard = leaderboard.subList(0, MAX_ENTRIES);
        }

        saveLeaderboard(leaderboard);
        return madeIt;
    }

    /**
     * Gets the top 10 scores.
     * 
     * @return List of top 10 entries
     */
    public List<LeaderboardEntry> getTop10() {
        List<LeaderboardEntry> leaderboard = loadLeaderboard();
        Collections.sort(leaderboard);
        return leaderboard.size() > MAX_ENTRIES ? leaderboard.subList(0, MAX_ENTRIES) : leaderboard;
    }
}
