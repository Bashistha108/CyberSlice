package de.hsh.gui;

import de.hsh.app.CyberSliceFassade;
import de.hsh.app.Level;
import de.hsh.app.MainFrame;


/**
 * The GUIController is responsible for managing user interaction within the graphical user interface.
 * It serves as a bridge between the view layer and the application logic encapsulated in the CyberSliceFassade.
 * The controller handles button clicks, navigation between panels, and user-driven events such as mouse movements.
 *
 * @author bashistha
 * @version 1.0
 */
public class GUIController {
    private CyberSliceFassade appFassade;
    private MainFrame mainFrame;
    private Level currentLevel;

    public GUIController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.appFassade = CyberSliceFassade.getInstance();
    }

    public void onSpielStartenClicked(Level level) {
        System.out.println("GUIController: Spiel wird gestartet (Level: " + level + ")");
        this.currentLevel = level;
        appFassade.spielStarten(level);
        mainFrame.showPanel("Spiel");
    }

    public void onPauseClicked() {
        appFassade.spielPausieren();
    }

    public void onResumeClicked() {
        appFassade.spielFortsetzen();
        mainFrame.showPanel("Spiel");
    }

    public void onBackToMenuClicked() {
        appFassade.spielStoppen();
        mainFrame.showPanel("Hauptmenü");
    }

    public void onRestartGameClicked() {
        System.out.println("GUIController: Restarting game with level: " + currentLevel);
        appFassade.spielStarten(currentLevel);
        mainFrame.showPanel("Spiel");
    }

    public void onLeaderboardClicked() {
        System.out.println("GUIController: Showing leaderboard");
        mainFrame.showPanel("Leaderboard");
    }

    public void onExitClicked() {
        System.exit(0);
    }

    public void onSaveSettingsClicked(String playerName, float volume) {
        System.out.println("GUIController: Speichere Einstellungen (" + playerName + ", " + volume + ")");
        appFassade.saveUserSetting(playerName, volume);
        mainFrame.showPanel("Hauptmenü");
    }

    public void onLevelAuswahlClicked() {
        System.out.println("GUIController: Level-Auswahl anzeigen");
        mainFrame.showPanel("LevelAuswahl");
    }

    public void onAnleitungClicked() {
        System.out.println("GUIController: Anleitung anzeigen");
        mainFrame.showPanel("Anleitung");
    }

    public void onMausBewegung(int x, int y) {
        appFassade.objektSchneiden(x, y);
    }

    public CyberSliceFassade getAppFassade() {
        return appFassade;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}