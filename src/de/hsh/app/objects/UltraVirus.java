package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;
import java.awt.Color;

public class UltraVirus extends SpielObjekt {
    public UltraVirus(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
        this.width = 70;
        this.height = 70;
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updatePunkte(20);
        controller.updateLeben(1);
    }

    public static Color getColor() {
        return new Color(220, 20, 60); // Crimson
    }

    public static String getDisplayName() {
        return "Ultra Virus";
    }
}
