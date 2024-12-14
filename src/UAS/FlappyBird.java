package UAS;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import static javafx.application.Application.launch;

public class FlappyBird extends Application {

    private static final int BOARD_WIDTH = 360;
    private static final int BOARD_HEIGHT = 640;
    private static final int PIPE_WIDTH = 64;
    private static final int PIPE_HEIGHT = 512;
    private static final int PIPE_OPENING = BOARD_HEIGHT / 4;

    private Bird bird;
    private ArrayList<Pipe> pipes;
    private double score = 0;
    private boolean gameOver = false;

    private Image backgroundImg, birdImg, topPipeImg, bottomPipeImg;
    private Canvas canvas;
    private GraphicsContext gc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas(BOARD_WIDTH, BOARD_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        try {
            backgroundImg = new Image(getClass().getResourceAsStream("/uploads/flappybirdbg.png"));
            birdImg = new Image(getClass().getResourceAsStream("/uploads/flappybird.png"));
            topPipeImg = new Image(getClass().getResourceAsStream("/uploads/toppipe.png"));
            bottomPipeImg = new Image(getClass().getResourceAsStream("/uploads/bottompipe.png"));
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }

        bird = new Bird(BOARD_WIDTH / 8, BOARD_HEIGHT / 2, 34, 24, birdImg);
        pipes = new ArrayList<>();

        canvas.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                bird.fly();
                if (gameOver) {
                    restartGame();
                }
            }
        });
        canvas.setFocusTraversable(true);

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) {
                    move();
                    render();
                }
            }
        };
        gameLoop.start();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Flappy Bird");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void placePipes() {
        int randomY = -PIPE_HEIGHT / 4 - (int) (Math.random() * (PIPE_HEIGHT / 2));
        Pipe topPipe = new Pipe(BOARD_WIDTH, randomY, PIPE_WIDTH, 
                PIPE_HEIGHT, topPipeImg);
        Pipe bottomPipe = new Pipe(BOARD_WIDTH, randomY + PIPE_HEIGHT + PIPE_OPENING, 
                PIPE_WIDTH, PIPE_HEIGHT, bottomPipeImg);
        pipes.add(topPipe);
        pipes.add(bottomPipe);
    }

    private void move() {
        bird.move();

        for (Pipe pipe : pipes) {
            pipe.move();

            if (!pipe.isPassed() && bird.x > pipe.x + pipe.width) {
                score += 0.5;
                pipe.setPassed(true);
            }

            if (bird.intersects(pipe)) {
                gameOver = true;
            }
        }

        pipes.removeIf(pipe -> pipe.x + pipe.width < 0);

        if (bird.y > BOARD_HEIGHT) {
            gameOver = true;
        }

        if (System.currentTimeMillis() % 1500 < 10) {
            placePipes();
        }
    }

    private void render() {
        gc.clearRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        gc.drawImage(backgroundImg, 0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        bird.draw(gc);
        for (Pipe pipe : pipes) {
            pipe.draw(gc);
        }

        gc.setFill(javafx.scene.paint.Color.WHITE);
        gc.fillText(gameOver ? "Game Over: " + (int) score : String.valueOf((int) score), 10, 35);
    }
    private void restartGame() {
        bird = new Bird(BOARD_WIDTH / 8, BOARD_HEIGHT / 2, 34, 24, birdImg);
        pipes.clear();
        score = 0;
        gameOver = false;
    }
}
