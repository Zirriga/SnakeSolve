package SnakeGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane(); //корневой элемент интерфейса
        Scene scene = new Scene(root,800,600);
        scene.setFill(Color.valueOf("#333333"));

        primaryStage.setTitle("SnakeGame.Snake");
        primaryStage.setScene(scene);
        Game game = new Game(30,40);
        Canvas canvas = new Canvas(800,600);
        GraphicsContext gc = canvas.getGraphicsContext2D(); //граф контекст в контроллер

        Controller.game = game;
        Controller.gc = gc;
        root.getChildren().add(canvas); //полотно в корневой элемент интерфейса
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    game.upDirection();
                    break;
                case DOWN:
                    game.downDirection();
                    break;
                case LEFT:
                    game.leftDirection();
                    break;
                case RIGHT:
                    game.rightDirection();
                    break;
                case SPACE:
                    game.restart();
                    break;
            }
        });
        primaryStage.setOnCloseRequest(event -> System.exit(0)); //при закрытии окна закрытие приложения
        primaryStage.setResizable(false); //чтоб не изменялся в размерах (не тянуть окно)
        primaryStage.sizeToScene();
        primaryStage.show();

        game.start();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}