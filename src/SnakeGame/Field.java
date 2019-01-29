package SnakeGame;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Field {
    private int[][] gamePlace;
    private int width;
    private int height;
    private Snake snake;
    private Point food;

    private static final int minAmtOfBarriers = 3;
    private static final int maxAmtOfBarriers = 6;


    public Field(int height, int width) {//конструктор поля
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
        createBarriers();
        snake = new Snake();
        spawnFood();
    }

    private void createBarriers() {
        for (int y = 0; y < height; y++) {
            set(y, 0, 4);
            set(y, width - 1, 4);
        }

        for (int x = 0; x < width; x++) {
            set(0, x, 4);
            set(height - 1, x, 4);
        }

        int amtOfBarriers = ThreadLocalRandom.current().nextInt(minAmtOfBarriers, maxAmtOfBarriers);
        for (int i = 0; i < amtOfBarriers; i++) {
            createBarrier();
        }
    }

    private void createBarrier() {
        int amtOfWallLeft;
        do {
            int yWallStart = ThreadLocalRandom.current().nextInt(4, height - 2);
            int xWallStart = ThreadLocalRandom.current().nextInt(4, width - 2);
            amtOfWallLeft = ThreadLocalRandom.current().nextInt(4, 8);

            if (!isEmptyArea(yWallStart, xWallStart)) continue;

            do {
                List<Point> wall = getWall(yWallStart, xWallStart);
                if (wall == null) break;

                for (Point p : wall) {
                    set(p.getY(), p.getX(), 4);
                }
                yWallStart = wall.get(3).getY();
                xWallStart = wall.get(3).getX();

                amtOfWallLeft--;
            } while (amtOfWallLeft > 0);
        } while (amtOfWallLeft > 0);
    }

    private List<Point> getWall(int yWallStart, int xWallStart) {
        List<Direction> dirs = Arrays.asList(Direction.values());
        Collections.shuffle(dirs);

        for (Direction dir : dirs) {
            List<Point> wall = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                wall.add(new Point(yWallStart + i * dir.getDy(), xWallStart + i * dir.getDx()));
            }
            if (isCorrectWall(wall)) {
                return wall;
            }
        }
        return null;
    }

    private boolean isCorrectWall(List<Point> pointList) {
        for (int i = 1; i <= pointList.size() - 2; i++) {
            Point p = pointList.get(i);
            if (!isEmptyPoint(p.getY(), p.getX())) return false;
        }
        Point end = pointList.get(3);
        if (!isEmptyArea(end.getY(), end.getX())) return false;

        return true;
    }

    private boolean isEmptyArea(int yWallStart, int xWallStart) {
        for (int dy = -2; dy <= 2; dy++) {
            for (int dx = -2; dx < 2; dx++) {
                if (!isEmptyPoint(yWallStart + dy, xWallStart + dx)) return false;
            }
        }
        return true;
    }



    private boolean isEmptyPoint(int y, int x) {
        return isCorrectPoint(y, x) && isEmpty(y, x);
    }

    public boolean isCorrectPoint(int y, int x) {
        return 0 <= y && y <= height - 1 &&
                0 <= x && x <= width - 1;
    }

    public void spawnFood() {
        int y, x;
        do {
            y = ThreadLocalRandom.current().nextInt(0, height);
            x = ThreadLocalRandom.current().nextInt(0, width);
        } while (!isEmpty(y, x)); //если занято змейкой
        food = new Point(y, x);
        refreshField();
    }

    private boolean isEmpty(int y, int x) {
        int state = get(y, x);
        return state != 4 && state != 1 && state != 2;
    }

    public boolean isEmpty(Point p) {
        return isEmpty(p.getY(), p.getX());
    }

    public int get(int y, int x) {
        return gamePlace[y][x];
    }

    private void set(int y, int x, int value) {
        gamePlace[y][x] = value;
    }

    public boolean checkMove() {
        refreshField();//для точного местоположения
        Point head = snake.getHead();
        Point velocity = snake.getVelocity();//текущее направление
        return isEmptyPoint(head.getY() + velocity.getY(), head.getX() + velocity.getX());
    }

    public void refreshField() {
        for (int y = 0; y < gamePlace.length; y++) {
            for (int x = 0; x < gamePlace[0].length; x++) {
                if (gamePlace[y][x] == 4) continue; //стена
                gamePlace[y][x] = 0; //очистка
            }
        }
        set(food.getY(), food.getX(), 3); //кидаем еду (3)
        for (Point point : snake.getBody()) {
            set(point.getY(), point.getX(), 1); //змейка (1)
        }
        set(snake.getBody().get(0).getY(), snake.getBody().get(0).getX(), 2);
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
