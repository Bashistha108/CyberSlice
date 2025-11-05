package de.hsh.app;

import java.util.List;
import java.util.Observable;

public class GameModel extends Observable {
    private int punkte;
    private int lebem;
    private int zeit;
    private Level level;
    private boolean isPowerMode;
    private String playerName;

    // Objektliste notwendig für das Obeserver Pattern, damit
    // das Spielpanel weiß was er zeichnen soll.
    private List<SpielObjekt> objektListe;


    public GameModel() {
        this.punkte = 0;
        this.lebem = 3;
        this.zeit = 90;
        this.isPowerMode = false;
        this.playerName = "Player";
    }

    public void resetGame(){
        this.punkte = 0;
        this.lebem = 3;
        this.zeit = 90;
        this.isPowerMode = false;
        this.objektListe.clear();
        setChanged();
        notifyObservers();
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public int getLebem() {
        return lebem;
    }

    public void setLebem(int lebem) {
        this.lebem = lebem;
    }

    public int getZeit() {
        return zeit;
    }

    public void setZeit(int zeit) {
        this.zeit = zeit;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isPowerMode() {
        return isPowerMode;
    }

    public void setPowerMode(boolean powerMode) {
        isPowerMode = powerMode;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
