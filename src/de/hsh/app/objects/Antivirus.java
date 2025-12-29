package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;



/**
 * The Antivirus class represents a game object in a 2D environment
 * that penalizes the player when interacted with. It extends the
 * SpielObjekt class, inheriting the common behaviors and attributes
 * for game objects such as position and movement.
 *
 * This object is expected to represent an antivirus element in the game
 * that reduces the player's score and health when sliced.
 */
public class Antivirus extends SpielObjekt {
    public Antivirus(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updatePunkte(-10);
        controller.updateLeben(-1);
        controller.playAntivirusChoppedSound();
    }

    public static String getDisplayName() {
        return "Antivirus";
    }
}
