package de.hsh.app;

import de.hsh.app.objects.*;
import de.hsh.persistence.PersistenceFassade;
import de.hsh.persistence.UserSettings;

import java.util.List;
import java.util.Random;
import javax.swing.Timer;

public class GameController {

    private GameModel model;
    private List<SpielObjekt> objektList;
    private Timer gameTime;
    private PersistenceFassade persistence;
    private Random random = new Random();
    private Timer spawnTimer;

    public GameController(GameModel gameModel, PersistenceFassade persistence) {
        this.model = gameModel;
        this.objektList = gameModel.getObjektListe();
        this.persistence = persistence;
        this.gameTime = new Timer(16, e -> gameLoop());
        this.spawnTimer = new Timer(1000, e -> spawnObjekt());
    }

    public void spielStarten(Level level) {
        model.setLevel(level);
        model.resetGame();
        loadInitialSettings();
        int spawnDelay = (int) (level.getSpawnRate() * 1000);
        spawnTimer.setDelay(spawnDelay);
        spawnTimer.setInitialDelay(0);
        gameTime.start();
        spawnTimer.start();
    }

    public void spielPausieren() {
        gameTime.stop();
        spawnTimer.stop();
    }

    public void spielFortsetzen() {
        gameTime.start();
        spawnTimer.start();
    }

    private void gameLoop() {
        for (int i = objektList.size() - 1; i >= 0; i--) {
            SpielObjekt obj = objektList.get(i);
            obj.move();
        }
        checkSpielEnde();
        model.setChanged();
        model.notifyObservers();
    }

    private void spawnObjekt() {
        float startX = random.nextFloat() * 500;
        float startY = 0;
        float speed = (float)model.getLevel().getObjectSpeed();
        SpielObjekt neuesObjekt;
        int typ = random.nextInt(7);
        switch (typ) {
            case 0: neuesObjekt = new Virus(startX, startY, speed); break;
            case 1: neuesObjekt = new Antivirus(startX, startY, speed); break;
            case 2: neuesObjekt = new Datei(startX, startY, speed); break;
            case 3: neuesObjekt = new UltraVirus(startX, startY, speed); break;
            case 4: neuesObjekt = new UltraAntivirus(startX, startY, speed); break;
            case 5: neuesObjekt = new Uhr(startX, startY, speed); break;
            default: neuesObjekt = new USBStick(startX, startY, speed); break;
        }
        objektList.add(neuesObjekt);
    }

    public void checkKollission(int x, int y) {
        if(objektList == null) return;
        for (int i = objektList.size() - 1; i >= 0; i--) {
            SpielObjekt obj = objektList.get(i);
            if (obj.isClicked(x, y)) {
                obj.onSlice(this);
                objektList.remove(i);
                break;
            }
        }
    }

    private void checkSpielEnde() {
        if (model.getLeben() <= 0 || model.getZeit() <= 0) {
            spielPausieren();
            System.out.println("Spiel beendet! Punkte: " + model.getPunkte());
        }
    }

    public void updatePunkte(int changePunkte) {
        int faktor = model.isPowerMode() ? 2 : 1;
        model.setPunkte(model.getPunkte() + (changePunkte * faktor));
    }

    public void updateLeben(int changeLeben) {
        if (changeLeben > 0 && model.getLeben() >= 3) {
            return;
        }
        model.setLeben(model.getLeben() + changeLeben);
    }

    public void updateZeit(int changeTime) {
        if (changeTime > 0 && model.getZeit() >= 60) {
            return;
        }
        model.setZeit(model.getZeit() + changeTime);
    }

    public void aktivierePowerModus() {
        if (model.isPowerMode()) return;
        model.setPowerMode(true);
        Timer powerModeTimer = new Timer(10000, e -> {
            model.setPowerMode(false);
            ((Timer)e.getSource()).stop();
        });
        powerModeTimer.setRepeats(false);
        powerModeTimer.start();
    }

    public void loadInitialSettings() {
        UserSettings settings = persistence.loadSettings();
        model.setPlayerName(settings.getPlayerName());
    }

    public void saveUserSettings(String playerName, float volume) {
        UserSettings settings = new UserSettings(playerName, volume);
        persistence.saveSettings(settings);
        model.setPlayerName(playerName);
    }
}
