package de.hsh.persistence;

import java.util.List;

/**
 * Interface defining the contract for the persistence layer.
 * Allows saving and loading of user settings and leaderboard.
 */
public interface PersistenceFassade {

    /**
     * Saves the provided user settings.
     * 
     * @param settings The UserSettings object to save.
     */
    void saveSettings(UserSettings settings);

    /**
     * Loads the user settings.
     * 
     * @return The loaded UserSettings object, or a default if none exists.
     */
    UserSettings loadSettings();

    /**
     * Adds a score to the leaderboard.
     * 
     * @param entry The leaderboard entry to add
     * @return true if the entry made it to top 10
     */
    boolean addScore(LeaderboardEntry entry);

    /**
     * Gets the top 10 scores from the leaderboard.
     * 
     * @return List of top 10 leaderboard entries
     */
    List<LeaderboardEntry> getTop10();
}