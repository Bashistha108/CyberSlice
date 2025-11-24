package de.hsh.app.objects;


import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;

public class Datei extends SpielObjekt {
    public Datei(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updatePunkte(-5);
    }
}