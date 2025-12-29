package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;



/**
 * Represents a specific type of game object called "Datei" (file) in the game,
 * which has a unique color, display name, and behavior when interacted with.
 * This class extends SpielObjekt, inheriting its position, size, and movement logic.
 *
 * When this object is "sliced" by the player during gameplay, it decreases the player's
 * score by a fixed value of 5 points.
 */
public class Datei extends SpielObjekt {
    public Datei(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updatePunkte(-5);
    }

    public static String getDisplayName() {
        return "File";
    }
}