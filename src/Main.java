import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root,800,600);
        scene.setFill(Color.valueOf("#333333"));
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        Game game = new Game(30,40);
        Canvas canvas = new Canvas(800,600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Controller.game = game;
        Controller.gc = gc;
        root.getChildren().add(canvas);
        scene.setOnKeyPressed(event -> {
            switch(event.getCode().toString()) {
                case "UP":
                    game.upDirection();
                    break;
                case "DOWN":
                    game.downDirection();
                    break;
                case "LEFT":
                    game.leftDirection();
                    break;
                case "RIGHT":
                    game.rightDirection();
                    break;
                case "SPACE":
                    game.restart();
                    break;
            }
        });
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setResizable(false);
        primaryStage.show();
        game.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}