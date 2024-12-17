package UAS;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Bird extends GameElement implements Movable {
    private int velocityY;
    private int velocityX;  
    private static final int GRAVITY = 1;

    public Bird(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
        this.velocityY = -10;
        this.velocityX = 5;  
    }

    @Override
    public void move() {
        velocityY += GRAVITY;
        y += velocityY;
        y = Math.max(y, 0);

        if (velocityX > 0) {
            velocityX -= 1;  
        }
        x += velocityX;

        if (x + width > 360) {
            x = 360 - width; 
        }
    }

    public void fly() {
        velocityY = -9;  
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(img, x, y, width, height);  
    }
}
