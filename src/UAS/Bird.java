package UAS;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Bird extends GameElement implements Movable {
    private int velocityY;
    private int velocityX;  // Kecepatan horizontal burung
    private static final int GRAVITY = 1;

    // Konstruktor untuk inisialisasi burung
    public Bird(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
        this.velocityY = -10;
        this.velocityX = 5;  // Kecepatan awal burung ke kanan
    }

    @Override
    public void move() {
        // Perhitungan gravitasi vertikal
        velocityY += GRAVITY;
        y += velocityY;

        // Batasi agar burung tidak keluar dari atas
        y = Math.max(y, 0);

        // Pergerakan horizontal burung ke kanan dengan perlambatan
        if (velocityX > 0) {
            velocityX -= 1;  // Perlambat burung dengan mengurangi velocityX
        }
        x += velocityX;  // Gerakan horizontal

        // Batasi agar burung tidak keluar dari batas kanan layar
        if (x + width > 360) {
            x = 360 - width;  // Batasi posisi horizontal burung
        }
    }

    // Fungsi untuk membuat burung terbang ke atas
    public void fly() {
        velocityY = -9;  // Gerakkan burung ke atas
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(img, x, y, width, height);  // Gambar burung pada posisi x, y
    }
}
