package de.hsh.gui;


import de.hsh.app.CyberSliceFassade;
import de.hsh.app.Level;
import de.hsh.app.MainFrame;

public class GUIController {
    private CyberSliceFassade appFassade;
    private MainFrame mainFrame;

    public GUIController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.appFassade = CyberSliceFassade.getInstance();
    }

    public void onSpielStartenClicked(Level level) {
        System.out.println("GUIController: Spiel wird gestartet (Level: " + level + ")");
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
    }

    public void onAnleitungClicked() {
        System.out.println("GUIController: Anleitung anzeigen");
    }

    public void onMausBewegung(int x, int y) {
        appFassade.objektSchneiden(x, y);
    }

    public CyberSliceFassade getAppFassade() {
        return appFassade;
    }
}