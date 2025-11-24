package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;
import java.awt.Color;

public class Datei extends SpielObjekt {
    public Datei(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updatePunkte(-5);
    }

    public static Color getColor() {
        return new Color(169, 169, 169);
    }

    public static String getDisplayName() {
        return "File";
    }
}