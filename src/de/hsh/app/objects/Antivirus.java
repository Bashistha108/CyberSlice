package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;
import java.awt.Color;

public class Antivirus extends SpielObjekt {
    public Antivirus(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updatePunkte(-10);
        controller.updateLeben(-1);
        controller.playAntivirusChoppedSound();
    }

    public static Color getColor() {
        return new Color(50, 205, 50); // Lime Green
    }

    public static String getDisplayName() {
        return "Antivirus";
    }
}
