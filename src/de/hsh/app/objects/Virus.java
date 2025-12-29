package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;


/**
 * The Virus class represents a game object that inherits behavior and attributes
 * from the SpielObjekt class. It is a specific type of object in the game that
 * interacts with the game controller when it is "sliced".
 */
public class Virus extends SpielObjekt {
    public Virus(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updatePunkte(10);
        controller.playVirusChoppedSound();
    }

    public static String getDisplayName() {
        return "Virus";
    }
}
