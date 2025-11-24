package de.hsh.app.objects;


import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;

public class USBStick extends SpielObjekt {
    public USBStick(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.aktivierePowerModus();
    }
}