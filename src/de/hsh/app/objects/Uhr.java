package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;


/**
 * The Uhr class represents a specific type of SpielObjekt in the game.
 * It functions as a "clock" object, providing a time-related benefit
 * when interacted with by the player.
 *
 * When sliced, the Uhr object updates the game controller to add extra time
 * to the game only when time < 60s
 */
public class Uhr extends SpielObjekt {
    public Uhr(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updateZeit(10);
    }

    public static String getDisplayName() {
        return "Clock";
    }
}
