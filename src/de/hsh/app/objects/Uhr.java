package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;
import java.awt.Color;

public class Uhr extends SpielObjekt {
    public Uhr(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updateZeit(10);
    }

    public static Color getColor() {
        return new Color(0, 191, 255);
    }

    public static String getDisplayName() {
        return "Clock";
    }
}
