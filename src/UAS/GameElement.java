package UAS;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameElement {
    protected int x, y, width, height;
    protected Image img;

    public GameElement(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    public abstract void draw(GraphicsContext gc);

    public boolean intersects(GameElement other) {
        return this.x < other.x + other.width &&
               this.x + this.width > other.x &&
               this.y < other.y + other.height &&
               this.y + this.height > other.y;
    }
}
