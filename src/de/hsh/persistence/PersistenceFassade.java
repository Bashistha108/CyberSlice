package de.hsh.persistence;

/**
 * Interface defining the contract for the persistence layer.
 * Allows saving and loading of user settings.
 */
public interface PersistenceFassade {

    /**
     * Saves the provided user settings.
     * @param settings The UserSettings object to save.
     */
    void saveSettings(UserSettings settings);

    /**
     * Loads the user settings.
     * @return The loaded UserSettings object, or a default if none exists.
     */
    UserSettings loadSettings();
}