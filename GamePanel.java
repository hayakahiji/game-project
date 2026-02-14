import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    // ขนาดหน้าจอและค่าคงที่
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    public static final int GROUND_Y = 530;

    Image background, cloundImage, groundImage;
    Player player = new Player();
    Enemy bot = new Enemy(800, 430, 300); 
    Ball ball = new Ball(500, 200);
    
    // สถานะเกม
    int playerScore = 0, enemyScore = 0;
    int gameTime = 60;
    long lastTimeCheck = System.currentTimeMillis();
    boolean isGameOver = false;
    String winnerText = "";

    boolean left, right, jump;
    Timer timer;

    public GamePanel() {
        // Load Assets
        background = new ImageIcon("src/backgrounds_frame/background_1/1.png").getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        cloundImage = new ImageIcon("src/backgrounds_frame/background_1/6.png").getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        groundImage = new ImageIcon("src/ground/PNG/Green_ground.png").getImage().getScaledInstance(WIDTH, 150, Image.SCALE_SMOOTH);
        
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(this);

        timer = new Timer(16, this);
        timer.start();
    }

    private void resetRound() {
        ball = new Ball(WIDTH / 2, 200);
        player.x = 150;
        player.y = GROUND_Y - player.height;
        bot.x = 800;
        bot.y = GROUND_Y - bot.height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // วาดฉาก
        g2d.drawImage(background, 0, 0, null);
        g2d.drawImage(cloundImage, 0, -150, null);
        g2d.drawImage(groundImage, 0, 430, null);
        
        // วาดประตู (Goals)
        g2d.setColor(new Color(255, 255, 255, 150));
        g2d.fillRect(0, GROUND_Y - 150, 50, 150); // ประตูซ้าย
        g2d.fillRect(WIDTH - 50, GROUND_Y - 150, 50, 150); // ประตูขวา
        
        // วาดตัวละคร
        player.draw(g2d);
        bot.draw(g2d);
        ball.draw(g2d);

        // UI: Score & Time
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString(playerScore + " - " + enemyScore, WIDTH/2 - 50, 50);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.drawString("Time: " + gameTime, WIDTH/2 - 35, 80);

        if (isGameOver) {
            drawGameOver(g2d);
        }
    }

    private void drawGameOver(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 60));
        g2d.drawString("GAME OVER", WIDTH/2 - 180, HEIGHT/2 - 50);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        g2d.drawString(winnerText, WIDTH/2 - 100, HEIGHT/2 + 20);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.drawString("Press 'R' to Restart", WIDTH/2 - 80, HEIGHT/2 + 70);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            // อัปเดตเวลา
            long now = System.currentTimeMillis();
            if (now - lastTimeCheck >= 1000) {
                gameTime--;
                lastTimeCheck = now;
                if (gameTime <= 0) {
                    isGameOver = true;
                    winnerText = (playerScore > enemyScore) ? "YOU WIN!" : (playerScore < enemyScore) ? "YOU LOSE!" : "DRAW!";
                }
            }

            player.update(left, right, jump);
            bot.update(ball);
            ball.update();
            checkCollisions();
        }
        repaint();
    }

    private void checkCollisions() {

        // Ball Collision with Characters
        if (player.getBounds().intersects(ball.getBounds())) {
            ball.vx = (ball.x > player.x) ? 7 : -7;
            ball.vy = -8;
        }
        if (bot.getBounds().intersects(ball.getBounds())) {
            ball.vx = (ball.x > bot.x) ? 7 : -7;
            ball.vy = -8;
        }

        // Goal Detection
        if (ball.x < 50 && ball.y > GROUND_Y - 150) {
            enemyScore++;
            resetRound();
        } else if (ball.x > WIDTH - 50 - ball.size && ball.y > GROUND_Y - 150) {
            playerScore++;
            resetRound();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) left = true;
        if (key == KeyEvent.VK_D) right = true;
        if (key == KeyEvent.VK_W) jump = true;
        if (key == KeyEvent.VK_R && isGameOver) {
            playerScore = 0; enemyScore = 0; gameTime = 60;
            isGameOver = false; resetRound();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) left = false;
        if (key == KeyEvent.VK_D) right = false;
        if (key == KeyEvent.VK_W) jump = false;
        
    }
    @Override public void keyTyped(KeyEvent e) {}
}