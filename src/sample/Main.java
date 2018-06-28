package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;

public class Main extends Application {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static final int BLOCK_SIZE = 30;
    public static final int WIDTH = 20 * BLOCK_SIZE;
    public static final int HEIGHT = 15 * BLOCK_SIZE;

    private Direction direction = Direction.RIGHT;
    private boolean moved = false;
    private boolean running = false;

    private Timeline timeline = new Timeline();

    private ObservableList<Node> snake;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        Group snakeBody = new Group();
        snake = snakeBody.getChildren();

        Rectangle food = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        food.setFill(Color.PINK);
        food.setTranslateX((int) (Math.random() * WIDTH - BLOCK_SIZE) / BLOCK_SIZE * BLOCK_SIZE);
        food.setTranslateY((int) (Math.random() * HEIGHT - BLOCK_SIZE) / BLOCK_SIZE * BLOCK_SIZE);

        KeyFrame frame = new KeyFrame(Duration.seconds(0.2), event -> {
            if (!running)
                return;

            boolean toRemove = snake.size() > 1;

            Node tail = toRemove ? snake.remove(snake.size() - 1) : snake.get(0);

            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();

            switch (direction) {
                case UP:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() - BLOCK_SIZE);
                    break;
                case DOWN:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() + BLOCK_SIZE);
                    break;
                case LEFT:
                    tail.setTranslateX(snake.get(0).getTranslateX() - BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snake.get(0).getTranslateX() + BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }
            moved = true;

            if (toRemove)
                snake.add(0, tail);

            for (Node rect : snake) {
                if (rect != tail && tail.getTranslateX() == rect.getTranslateX()
                        && tail.getTranslateY() == rect.getTranslateY()) {
                    restartGame();
                    break;
                }
            }
            if (tail.getTranslateX() < 0 || tail.getTranslateX() >= WIDTH
                    || tail.getTranslateY() < 0 || tail.getTranslateY() >= HEIGHT) {
                restartGame();
            }
            if (tail.getTranslateX() == food.getTranslateX()
                    && tail.getTranslateY() == food.getTranslateY()) {
                food.setTranslateX((int) (Math.random() * (WIDTH - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
                food.setTranslateY((int) (Math.random() * (HEIGHT - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

                Rectangle rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                rect.setTranslateX(tailX);
                rect.setTranslateY(tailY);

                snake.add(rect);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(food, snakeBody);
        return root;
    }

    private void restartGame() {
        stopGame();
        startGame();
    }

    private void startGame() {
        direction = Direction.RIGHT;
        Rectangle head = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        snake.add(head);
        timeline.play();
        running = true;
    }

    private void stopGame() {
        running = false;
        timeline.stop();
        snake.clear();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(event -> {
            if (moved) {
                switch (event.getCode()) {
                    case W:
                        if (direction != Direction.DOWN)
                            direction = Direction.UP;
                        break;
                    case A:
                        if (direction != Direction.RIGHT)
                            direction = Direction.LEFT;
                        break;
                    case D:
                        if (direction != Direction.LEFT)
                            direction = Direction.RIGHT;
                        break;
                    case S:
                        if (direction != Direction.UP)
                            direction = Direction.DOWN;
                        break;
                }
            }
            moved = false;
        });
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();
        startGame();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

//как лучше поделить на классы? как добавть окно очков?
