package de.hsh.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single entry in the leaderboard.
 */
public class LeaderboardEntry implements Serializable, Comparable<LeaderboardEntry> {

    private static final long serialVersionUID = 1L;

    private String playerName;
    private int score;
    private String level;
    private LocalDateTime date;

    public LeaderboardEntry(String playerName, int score, String level) {
        this.playerName = playerName;
        this.score = score;
        this.level = level;
        this.date = LocalDateTime.now();
    }

    // Getters
    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public String getLevel() {
        return level;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return date.format(formatter);
    }

    @Override
    public int compareTo(LeaderboardEntry other) {
        // Sort by score descending
        return Integer.compare(other.score, this.score);
    }

    @Override
    public String toString() {
        return String.format("%s - %d points (%s) - %s",
                playerName, score, level, getFormattedDate());
    }
}
