package de.hsh.persistence;

import java.util.List;

/**
 * Interface defining the contract for the persistence layer.
 * Allows saving and loading of user settings and leaderboard.
 */
public interface PersistenceFassade {

    void saveSettings(UserSettings settings);
    UserSettings loadSettings();
    boolean addScore(LeaderboardEntry entry);
    List<LeaderboardEntry> getTop10();
}