import java.util.LinkedList;
import java.util.List;

public class Snake {
    private List<Point> snake = new LinkedList<>();
    private int velocityX;
    private int velocityY;
    private int length;

    public Snake() {
        snake.add(new Point(0, 0));
        velocityX = 1;
        velocityY = 0;
        length = 1;
    }

    public void upDirection() {
        if (length > 1 && snake.get(0).getY() - snake.get(1).getY() == 1) return;
        velocityX = 0;
        velocityY = -1;
    }

    public void downDirection() {
        if (length > 1 && snake.get(0).getY() - snake.get(1).getY() == -1) return;
        velocityX = 0;
        velocityY = 1;
    }

    public void leftDirection() {
        if (length > 1 && snake.get(0).getX() - snake.get(1).getX() == 1) return;
        velocityX = -1;
        velocityY = 0;

    }

    public void rightDirection() {
        if (length > 1 && snake.get(0).getX() - snake.get(1).getX() == -1) return;
        velocityX = 1;
        velocityY = 0;
    }

    public void move() {
        snake.add(0,new Point(snake.get(0).getY() + velocityY, snake.get(0).getX() + velocityX));
        snake.remove(snake.size() - 1);
    }

    public void moveAndIncrease() {
        snake.add(0,new Point(snake.get(0).getY() + velocityY, snake.get(0).getX() + velocityX));
        length++;
    }
    public Point getVelocity() {
        return new Point(velocityY, velocityX);
    }
    public Point getHead() {
        return snake.get(0);
    }
    public List<Point> getBody() {
        return snake;
    }
}
