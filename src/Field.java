import java.util.concurrent.ThreadLocalRandom;

public class Field {
    private int[][] gamePlace;
    private int width;
    private int height;
    private Snake snake;
    private Point food;

    public Field(int height, int width) {
        gamePlace = new int[height][width];
        this.height = height;
        this.width = width;
        clean();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void clean() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gamePlace[i][j] = 0;
            }
        }
        snake = new Snake();
        spawnFood();
    }

    public void spawnFood() {
        int y;
        int x;
        do {
            y = ThreadLocalRandom.current().nextInt(0, height);
            x = ThreadLocalRandom.current().nextInt(0, width);
        } while (get(y, x) == 1);
        food = new Point(y, x);
        refreshField();
    }

    public int get(int y, int x) {
        return gamePlace[y][x];
    }

    public void set(int y, int x, int value) {
        gamePlace[y][x] = value;
    }

    public boolean checkMove() {
        refreshField();
        Point head = snake.getHead();
        Point velocity = snake.getVelocity();
        Point nextHeadLocation = new Point(head.getY() + velocity.getY(), head.getX() + velocity.getX());
        return !(nextHeadLocation.getY() < 0 || nextHeadLocation.getY() > height - 1 ||
                nextHeadLocation.getX() < 0 || nextHeadLocation.getX() > width - 1 ||
                get(nextHeadLocation.getY(), nextHeadLocation.getX()) == 1);
    }

    public void refreshField() {
        for (int i = 0; i < gamePlace.length; i++) {
            for (int j = 0; j < gamePlace[0].length; j++) {
                gamePlace[i][j] = 0;
            }
        }
        set(food.getY(), food.getX(), 3);
        for (Point point : snake.getBody()) {
            set(point.getY(), point.getX(), 1);
        }
    }

    public Point getFood() {
        return food;
    }

    public int area() {
        return height * width;
    }

    public Snake getSnake() {
        return snake;
    }
}
