package de.hsh.app;

/**
 * The Level enum represents the different difficulty levels available in the game.
 * Each level is characterized by specific spawn rates and object speeds that dictate
 * the gameplay's complexity and pace.
 *
 * This enum provides the following predefined levels:
 * - EASY: A beginner-friendly level with slower object speed and higher spawn rate.
 * - INTERMEDIATE: A moderately challenging level with balanced object speed and spawn rate.
 * - MASTER: The most challenging level with faster object speed and lower spawn rate.
 *
 * @author bashitha
 * @version 1.0
 */
public enum Level {
    EASY(1.5, 2.0),
    INTERMEDIATE(1.0, 3.5),
    MASTER(0.7, 4.0);

    private final double spawnRate;
    private final double objectSpeed;

    Level(double spawnRate, double objectSpeed) {
        this.spawnRate = spawnRate;
        this.objectSpeed = objectSpeed;
    }

    public double getSpawnRate() {
        return spawnRate;
    }

    public double getObjectSpeed() {
        return objectSpeed;
    }
}
