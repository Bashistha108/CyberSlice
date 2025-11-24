package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;
import java.awt.Color;

public class UltraAntivirus extends SpielObjekt {
    public UltraAntivirus(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updatePunkte(-20);
        controller.updateLeben(-2);
    }

    public static Color getColor() {
        return new Color(0, 128, 0);
    }

    public static String getDisplayName() {
        return "Ultra Antivirus";
    }
}