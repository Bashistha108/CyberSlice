package de.hsh.app.objects;

import de.hsh.app.GameController;
import de.hsh.app.SpielObjekt;


/**
 * Represents a "Life" object in the game, which is a subclass of the abstract
 * SpielObjekt class. When sliced by the player, this object increases the player's lives but maximum to 3.
 */
public class Life extends SpielObjekt {
    public Life(float posX, float posY, float geschwindigkeit) {
        super(posX, posY, geschwindigkeit);
    }

    @Override
    public void onSlice(GameController controller) {
        controller.updateLeben(1);
    }

    public static String getDisplayName() {
        return "Life";
    }
}
