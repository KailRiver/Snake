import javax.swing.*;

public class SnakeGame {
    public static void main(String[] args) {
        // Создаем окно игры
        JFrame frame = new JFrame("Змейка");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450); // Увеличиваем размер окна для кнопки
        frame.setLocationRelativeTo(null);

        // Добавляем панель игры
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        // Делаем окно видимым
        frame.setVisible(true);

        // Запускаем игру
        gamePanel.startGame();
    }
}