package de.hsh.app;

public enum Level {
    EASY(1.5, 2.0),
    INTERMEDIATE(1.0, 3.5),
    MASTER(0.7, 5.0);

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
