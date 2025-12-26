package de.hsh.app;

import de.hsh.app.objects.*;
import de.hsh.util.SoundManager;
import de.hsh.persistence.PersistenceFassade;
import de.hsh.persistence.UserSettings;

import java.util.List;
import java.util.Random;
import javax.swing.Timer;

public class GameController {

    private GameModel model;
    private List<SpielObjekt> objektList;
    private Timer gameTime;
    private Timer countdownTimer;
    private PersistenceFassade persistence;
    private Random random = new Random();
    private Timer spawnTimer;
    private int screenWidth = 800;
    private int screenHeight = 600;
    private boolean isGameOver = false;
    private SoundManager soundManager;

    public GameController(GameModel gameModel, PersistenceFassade persistence) {
        this.model = gameModel;
        this.objektList = gameModel.getObjektListe();
        this.persistence = persistence;
        this.gameTime = new Timer(16, e -> gameLoop());
        this.spawnTimer = new Timer(1000, e -> spawnObjekt());
        this.countdownTimer = new Timer(1000, e -> decrementTime());
        this.soundManager = new SoundManager();
    }

    public void spielStarten(Level level) {
        model.setLevel(level);
        model.resetGame();
        isGameOver = false;
        loadInitialSettings();
        int spawnDelay = (int) (level.getSpawnRate() * 1000);
        spawnTimer.setDelay(spawnDelay);
        spawnTimer.setInitialDelay(0);
        gameTime.start();
        spawnTimer.start();
        spawnTimer.start();
        countdownTimer.start();
        soundManager.playBackgroundMusic();
    }

    public void spielPausieren() {
        gameTime.stop();
        spawnTimer.stop();
        spawnTimer.stop();
        countdownTimer.stop();
        soundManager.stopBackgroundMusic();
    }

    public void spielFortsetzen() {
        if (!isGameOver) {
            gameTime.start();
            spawnTimer.start();
            spawnTimer.start();
            countdownTimer.start();
            soundManager.playBackgroundMusic();
        }
    }

    public void spielStoppen() {
        gameTime.stop();
        spawnTimer.stop();
        gameTime.stop();
        spawnTimer.stop();
        countdownTimer.stop();
        soundManager.stopBackgroundMusic();
        isGameOver = true;
        model.resetGame();
    }

    private void decrementTime() {
        if (model.getZeit() > 0) {
            model.setZeit(model.getZeit() - 1);
            model.setChanged();
            model.notifyObservers();
        }
    }

    private void gameLoop() {
        // Move objects
        for (int i = objektList.size() - 1; i >= 0; i--) {
            SpielObjekt obj = objektList.get(i);
            obj.move();

            // Remove objects that are off-screen
            if (obj.isOffScreen(screenHeight)) {
                objektList.remove(i);
                // Lose a life if it was a virus that escaped
                if (obj instanceof Virus || obj instanceof UltraVirus) {
                    updateLeben(-1);
                }
            }
        }
        checkSpielEnde();
        model.setChanged();
        model.notifyObservers();
    }

    private void spawnObjekt() {
        float startX = random.nextFloat() * (screenWidth - 50);
        float startY = -50;
        float speed = (float) model.getLevel().getObjectSpeed();
        SpielObjekt neuesObjekt;
        int typ = random.nextInt(100); // Changed to 100 for percentage-based spawning

        if (typ < 5) {
            // 5% chance for Life (rare)
            neuesObjekt = new Life(startX, startY, speed);
        } else {
            // Remaining 95% distributed among other objects
            int objectType = random.nextInt(7);
            switch (objectType) {
                case 0:
                    neuesObjekt = new Virus(startX, startY, speed);
                    break;
                case 1:
                    neuesObjekt = new Antivirus(startX, startY, speed);
                    break;
                case 2:
                    neuesObjekt = new Datei(startX, startY, speed);
                    break;
                case 3:
                    neuesObjekt = new UltraVirus(startX, startY, speed);
                    break;
                case 4:
                    neuesObjekt = new UltraAntivirus(startX, startY, speed);
                    break;
                case 5:
                    if (model.getZeit() > 60) {
                        neuesObjekt = new Virus(startX, startY, speed);
                    } else {
                        neuesObjekt = new Uhr(startX, startY, speed);
                    }
                    break;
                default:
                    neuesObjekt = new USBStick(startX, startY, speed);
                    break;
            }
        }
        objektList.add(neuesObjekt);
    }

    public void checkKollission(int x, int y) {
        if (objektList == null)
            return;
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
            if (!isGameOver) {
                spielPausieren();
                isGameOver = true;

                // Submit score to leaderboard
                String playerName = model.getPlayerName();
                int score = model.getPunkte();
                String level = model.getLevel().name();

                de.hsh.persistence.LeaderboardEntry entry = new de.hsh.persistence.LeaderboardEntry(playerName, score,
                        level);
                persistence.addScore(entry);

                soundManager.playGameOver();

                System.out.println("Spiel beendet! Punkte: " + score);
            }
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
        if (model.isPowerMode())
            return;
        model.setPowerMode(true);
        Timer powerModeTimer = new Timer(10000, e -> {
            model.setPowerMode(false);
            ((Timer) e.getSource()).stop();
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

    public void setScreenDimensions(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void playVirusChoppedSound() {
        soundManager.playVirusChopped();
    }

    public void playAntivirusChoppedSound() {
        soundManager.playAntivirusChopped();
    }

    public void playSliceSound() {
        soundManager.playSliceSound();
    }
}
