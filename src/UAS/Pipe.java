package UAS;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Pipe extends GameElement implements Movable {
    private boolean passed;

    public Pipe(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
        this.passed = false;
    }

    @Override
    public void move() {
        x -= 4; 
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(img, x, y, width, height);
    }
}
