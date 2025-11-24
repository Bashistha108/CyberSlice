package de.hsh.app;

import de.hsh.persistence.PersistenceFassade;
import de.hsh.persistence.SettingsBroker;

public class CyberSliceFassade {

    private static CyberSliceFassade instance;
    private GameController gameController;
    private GameModel gameModel;

    private CyberSliceFassade() {

        this.gameModel = new GameModel();

        PersistenceFassade persistence = new SettingsBroker();

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
}