package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;
import java.awt.Color;

public class Life extends SpielObjekt {
    public Life(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updateLeben(1);
    }

    public static Color getColor() {
        return new Color(255, 105, 180); // Hot Pink
    }

    public static String getDisplayName() {
        return "Life";
    }
}
