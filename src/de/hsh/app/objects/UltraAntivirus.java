package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;


public class UltraAntivirus extends SpielObjekt {
    public UltraAntivirus(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
        this.width = 110;
        this.height = 110;
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updatePunkte(-20);
        controller.updateLeben(-2);
        controller.playAntivirusChoppedSound();
    }

    public static String getDisplayName() {
        return "Ultra Antivirus";
    }
}