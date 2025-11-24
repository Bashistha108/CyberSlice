package de.hsh.app;

public abstract class SpielObjekt {
    protected float posX;
    protected float posY;
    protected float geschwindigkeit;

    protected int width = 50;
    protected int height = 50;

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

    public abstract void onSlice(GameController controller);

    public float getPosX() { return posX; }
    public float getPosY() { return posY; }
}