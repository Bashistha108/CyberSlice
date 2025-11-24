package de.hsh.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class GameModel extends Observable {
    private int punkte;
    private int leben;
    private int zeit;
    private Level level;
    private boolean isPowerMode;
    private String playerName;


    private List<SpielObjekt> objektListe;

    public GameModel() {
        this.punkte = 0;
        this.leben = 3;
        this.zeit = 90;
        this.isPowerMode = false;
        this.playerName = "Player";
        this.objektListe = new ArrayList<>();
    }

    public void resetGame(){
        this.punkte = 0;
        this.leben = 3;
        this.zeit = 90;
        this.isPowerMode = false;
        if(objektListe != null){
            this.objektListe.clear();
        }
        setChanged();
        notifyObservers();
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public int getLeben() {
        return leben;
    }

    public void setLeben(int leben) {
        this.leben = leben;
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

    public List<SpielObjekt> getObjektListe() {
        return objektListe;
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

}
