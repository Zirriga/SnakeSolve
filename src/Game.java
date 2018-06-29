import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private Field field;
    private Timer timer;
    private boolean gameOver = false;
    private boolean win = false;
    private boolean run = false;
    private int score = 0;
    public Game(int height, int width) {
        field = new Field(height, width);
    }

    public void start() {
        run = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (field.checkMove()) { //может ли двигаться дальше
                    if (field.getFood().equals(field.getSnake().getHead())) {
                        field.getSnake().moveAndIncrease();
                        score++;//съела
                        if (field.area() == field.getSnake().getBody().size()){
                            win = true;
                        } else {
                            field.spawnFood();
                        }
                    } else {
                        field.getSnake().move(); //если голова != еда - двигаемся
                    }
                } else {
                    gameOver = true;
                }
                field.refreshField();
                Controller.refresh();
                if (win || gameOver) stop();
            }
        },0,300);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        gameOver = false;
        win = false;
        score = 0;
        field.clean();
        run = false;
    }
    public void restart() {
        stop();
        start();
    }

    public Field getField() {
        return field;
    }

    public Integer getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWin() {
        return win;
    }

    public void upDirection() {
        if (!run) return;
        field.getSnake().upDirection();
    }

    public void rightDirection() {
        if (!run) return;
        field.getSnake().rightDirection();
    }

    public void downDirection() {
        if (!run) return;
        field.getSnake().downDirection();
    }

    public void leftDirection() {
        if (!run) return;
        field.getSnake().leftDirection();
    }
}
