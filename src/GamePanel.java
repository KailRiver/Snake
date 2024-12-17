import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int WIDTH = 20;
    private final int HEIGHT = 20;
    private final int CELL_SIZE = 20;

    private LinkedList<Point> snake;
    private Point food;
    private int direction; // 0 - вверх, 1 - вправо, 2 - вниз, 3 - влево
    private boolean gameOver;
    private Timer timer;
    private int score; // Счетчик очков

    private JButton restartButton; // Кнопка для перезапуска игры

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        // Создаем кнопку перезапуска
        restartButton = new JButton("Restart");
        restartButton.setFocusable(false); // Убираем фокус с кнопки
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame(); // Перезапускаем игру при нажатии на кнопку
            }
        });
        restartButton.setVisible(false); // Скрываем кнопку в начале игры
        add(restartButton); // Добавляем кнопку на панель

        initGame();
    }

    private void initGame() {
        snake = new LinkedList<>();
        snake.add(new Point(5, 5)); // Начальная позиция змейки
        direction = 1; // Начальное направление (вправо)
        spawnFood();
        gameOver = false;
        score = 0; // Инициализация счета
        timer = new Timer(150, this); // Таймер для обновления игры
    }

    private void spawnFood() {
        Random random = new Random();
        food = new Point(random.nextInt(WIDTH), random.nextInt(HEIGHT));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBorder(g); // Отрисовка рамки вокруг игрового поля
        drawSnake(g);
        drawFood(g);
        drawScore(g); // Отрисовка счета
        if (gameOver) {
            drawGameOver(g);
            restartButton.setVisible(true); // Показываем кнопку при завершении игры
        } else {
            restartButton.setVisible(false); // Скрываем кнопку во время игры
        }
    }

    // Метод для отрисовки рамки вокруг игрового поля
    private void drawBorder(Graphics g) {
        g.setColor(Color.GRAY);
        g.drawRect(0, 0, WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE); // Верхняя и нижняя границы
        g.drawRect(1, 1, (WIDTH * CELL_SIZE) - 2, (HEIGHT * CELL_SIZE) - 2); // Левая и правая границы
    }

    private void drawSnake(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x * CELL_SIZE, p.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    private void drawFood(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20); // Отображаем счет в левом верхнем углу
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Game Over!", 150, 150);
        g.drawString("Final Score: " + score, 150, 170); // Отображаем итоговый счет
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            moveSnake();
            checkCollisions();
            repaint();
        }
    }

    private void moveSnake() {
        Point head = snake.getFirst();
        Point newHead = new Point(head);

        // Обновляем позицию головы в зависимости от направления
        if (direction == 0) newHead.y--; // Вверх
        else if (direction == 1) newHead.x++; // Вправо
        else if (direction == 2) newHead.y++; // Вниз
        else if (direction == 3) newHead.x--; // Влево

        // Проверяем границы
        if (newHead.x < 0 || newHead.x >= WIDTH || newHead.y < 0 || newHead.y >= HEIGHT) {
            gameOver = true;
            return;
        }

        // Проверяем столкновение с самой собой
        for (Point p : snake) {
            if (p.equals(newHead)) {
                gameOver = true;
                return;
            }
        }

        // Добавляем новую голову
        snake.addFirst(newHead);

        // Удаляем хвост, если не съели еду
        if (!newHead.equals(food)) {
            snake.removeLast();
        } else {
            spawnFood();
            score++; // Увеличиваем счет при съедании еды
        }
    }

    private void checkCollisions() {
        Point head = snake.getFirst();
        if (head.equals(food)) {
            spawnFood();
        }
    }

    public void startGame() {
        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP && direction != 2) direction = 0;
        else if (key == KeyEvent.VK_RIGHT && direction != 3) direction = 1;
        else if (key == KeyEvent.VK_DOWN && direction != 0) direction = 2;
        else if (key == KeyEvent.VK_LEFT && direction != 1) direction = 3;
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    // Метод для перезапуска игры
    private void restartGame() {
        timer.stop(); // Останавливаем текущий таймер
        initGame(); // Сбрасываем состояние игры
        gameOver = false;
        timer = new Timer(150, this); // Создаем новый таймер
        timer.start(); // Перезапускаем таймер
        repaint();
    }
}