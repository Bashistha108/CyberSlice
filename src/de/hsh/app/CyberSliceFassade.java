package de.hsh.app;

import de.hsh.persistence.PersistenceFassade;
import de.hsh.persistence.SettingsBroker;

/**
 * CyberSliceFassade dient als singleton fassade zur Verbindung zwischen GameModel,
 * GameController und der Persistence Schicht
 *
 * @author bashistha joshi
 * @version 1.0
 *
 */
public class CyberSliceFassade {

    private static CyberSliceFassade instance;
    private GameController gameController;
    private GameModel gameModel;
    private PersistenceFassade persistence;

    private CyberSliceFassade() {

        this.gameModel = new GameModel();

        this.persistence = new SettingsBroker();

        this.gameController = new GameController(this.gameModel, persistence);
    }

    public static CyberSliceFassade getInstance() {
        if (instance == null) {
            instance = new CyberSliceFassade();
        }
        return instance;
    }

    public void spielStarten(Level level) {
        gameController.spielStarten(level);
    }

    public void spielPausieren() {
        gameController.spielPausieren();
    }

    public void spielFortsetzen() {
        gameController.spielFortsetzen();
    }

    public void spielStoppen() {
        gameController.spielStoppen();
    }

    public void objektSchneiden(int x, int y) {
        gameController.checkKollission(x, y);
    }

    public void saveUserSetting(String playerName, float volume) {
        gameController.saveUserSettings(playerName, volume);
    }

    public void loadInitialSetting() {
        gameController.loadInitialSettings();
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public GameController getGameController() {
        return gameController;
    }

    public PersistenceFassade getPersistence() {
        return persistence;
    }
}