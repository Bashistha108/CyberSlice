package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;
import java.awt.Color;

public class Virus extends SpielObjekt {
    public Virus(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updatePunkte(10);
        controller.playVirusChoppedSound();
    }

    public static Color getColor() {
        return new Color(255, 99, 71); // Tomato Red
    }

    public static String getDisplayName() {
        return "Virus";
    }
}
