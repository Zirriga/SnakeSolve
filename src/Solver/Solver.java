package Solver;

import SnakeGame.*;

import java.util.*;

public class Solver {
    ArrayDeque<Move> way;
    Game game;

    public Solver(Game game) {
        this.game = game;
    }

    public void run() {
        Field field = game.getField();
        Direction dir = findMove(field.getSnake(), field.getFood());
        if (dir != null) {
            makeMove(dir);
        }
    }

    private Direction findMove(Snake snake, Point target) {
        Queue<Point> queue = new LinkedList<>(); // очередь в поиске в ширину
        Set<Point> visited = new HashSet<>();// Сюда записываются посещенные вершины
        Map<Point, Point> pathOfVisited = new HashMap<>(); //запись конкретных путей до каждой посещенной вершины
        Stack<Point> snakeToEatPath = new Stack<>();

        queue.add(snake.getHead());
        visited.add(snake.getHead());
        while (!queue.isEmpty()) {
            Point node = queue.remove(); // узелб который достали из очереди для проверки его соседей
            if (node.getX() == target.getX() && node.getY() == target.getY()) {
                //Если путь к еде найден - ищем первый шажочек, а так как это стек шагов,
                //то мы потихоньку вытаскиваем из него элементы, пока в нем не встретим голову змеи
                Point step = target;
                while (step != null && step != snake.getHead()) {
                    snakeToEatPath.push(step);
                    step = pathOfVisited.get(step);
                }

                if (!snakeToEatPath.isEmpty()) {
                    step = snakeToEatPath.peek();
                }

                //первый шаг из стека доставли, теперь надо его сделать
                int x = step.getX();
                int y = step.getY();
                if (x > snake.getHead().getX() && y == snake.getHead().getY()) {
                    return Direction.RIGHT;
                }
                if (x < snake.getHead().getX() && y == snake.getHead().getY()) {
                    return Direction.LEFT;
                }
                if (x == snake.getHead().getX() && y > snake.getHead().getY()) {
                    return Direction.DOWN;
                }
                if (x == snake.getHead().getX() && y < snake.getHead().getY()) {
                    return Direction.UP;
                }
                if (x == snake.getHead().getX() && y == snake.getHead().getY()) {
                    return null;
                }
            }

            //всевозможные варианты хода из узла, в котором мы находимся
            Point up = new Point(node.getY() - 1, node.getX());
            Point right = new Point(node.getY(), node.getX() + 1);
            Point down = new Point(node.getY() + 1, node.getX());
            Point left = new Point(node.getY(), node.getX() - 1);

            Field field = game.getField();

            //проверяем возможность полученных выше шагов, если шаг валидный, то
            // мы добавляем его в очередь, для рассмотрения уже его соседей, в надежде найти
            // среди них клетку с едой, заодно добавляем нынешний узел в список уже
            // уже посещенных, что бы не мотать лишние круги
            if (field.isEmpty(up) && !visited.contains(up)) {
                queue.add(up);
                visited.add(up);
                pathOfVisited.put(up, node);
            }
            if (field.isEmpty(right) && !visited.contains(right)) {
                queue.add(right);
                visited.add(right);
                pathOfVisited.put(right, node);
            }
            if (field.isEmpty(down) && !visited.contains(down)) {
                queue.add(down);
                visited.add(down);
                pathOfVisited.put(down, node);
            }
            if (field.isEmpty(left) && !visited.contains(left)) {
                queue.add(left);
                visited.add(left);
                pathOfVisited.put(left, node);
            }
        }
        return null;
    }

    private void makeMove(Direction direction) {
        switch (direction) {
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
        }
    }


}
