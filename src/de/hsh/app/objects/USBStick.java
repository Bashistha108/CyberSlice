package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;
import java.awt.Color;

public class USBStick extends SpielObjekt {
    public USBStick(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.aktivierePowerModus();
    }

    public static Color getColor() {
        return new Color(255, 215, 0);
    }

    public static String getDisplayName() {
        return "USB Stick";
    }
}