package de.hsh.app;

/**
 * The SpielObjekt class represents a general game object that can move and be
 * interacted with.
 * Specific game objects (e.g., Antivirus, Datei) are expected to extend this
 * abstract class and
 * define their own specific behavior.
 */
public abstract class SpielObjekt {
    protected float posX;
    protected float posY;
    protected float geschwindigkeit;

    protected int width = 80;
    protected int height = 80;

    public SpielObjekt(float posX, float posY, float geschwindigkeit) {
        this.posX = posX;
        this.posY = posY;
        this.geschwindigkeit = geschwindigkeit;
    }

    public void move() {
        this.posY += this.geschwindigkeit;
    }

    public boolean isClicked(int x, int y) {
        return x >= posX && x <= posX + width &&
                y >= posY && y <= posY + height;
    }

    public boolean isOffScreen(int screenHeight) {
        return posY > screenHeight + height;
    }

    public abstract void onSlice(GameController controller);

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}