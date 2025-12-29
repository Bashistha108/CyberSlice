package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;


/**
 * Represents a USB stick game object in a 2D environment, extending the generic
 * behavior provided by the SpielObjekt class. The USB stick can activate a
 * specific power-up mode in the game when sliced.
 */
public class USBStick extends SpielObjekt {
    public USBStick(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.aktivierePowerModus();
    }


    public static String getDisplayName() {
        return "USB Stick";
    }
}