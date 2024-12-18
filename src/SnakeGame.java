import javax.swing.*;

public class SnakeGame {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Змейка");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450); // Увеличиваем размер окна для кнопки
        frame.setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.setVisible(true);

        gamePanel.startGame();
    }
}