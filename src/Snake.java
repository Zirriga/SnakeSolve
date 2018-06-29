import java.util.LinkedList;
import java.util.List;

public class Snake {
    private List<Point> snake = new LinkedList<>();
    private int velocityX; //скорости по х и у
    private int velocityY;
    private int length;

    public Snake() {
        snake.add(new Point(0, 0));//добавим голову
        velocityX = 1;//направление вправо
        velocityY = 0;
        length = 1;
    }

    public void upDirection() {
        if (length > 1 && snake.get(0).getY() - snake.get(1).getY() == 1) return; //коорд головы - коорд второго звена
        velocityX = 0;
        velocityY = -1; //движ вверх
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
        snake.remove(snake.size() - 1); //коорд головы + движение и удаление хвоста
    }

    public void moveAndIncrease() { //если съела
        snake.add(0,new Point(snake.get(0).getY() + velocityY, snake.get(0).getX() + velocityX));
        length++;
    }
    public Point getVelocity() {
        return new Point(velocityY, velocityX);
    }//получение скорости
    public Point getHead() {
        return snake.get(0);
    }
    public List<Point> getBody() {
        return snake;
    }
}
