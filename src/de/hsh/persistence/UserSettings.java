package de.hsh.persistence;

import java.io.Serializable;

/**
 * Data class representing the user's settings.
 * Implements Serializable to allow file storage.
 */
public class UserSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private String playerName;
    private float volume;

    public UserSettings(String playerName, float volume) {
        this.playerName = playerName;
        this.volume = volume;
    }

    // Getters and Setters
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}