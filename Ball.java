import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Ball {
    int x, y, size = 40;
    double vx = 0, vy = 0;
    double gravity = 0.6;
    double friction = 0.99;
    BufferedImage ballImage;

    public Ball(int x, int y) {
        this.x = x; this.y = y;
    }

    public void update() {
        vy += gravity;
        vx *= friction;
        x += vx;
        y += vy;

        // ชนพื้น
        if (y + size >= GamePanel.GROUND_Y) {
            y = GamePanel.GROUND_Y - size;
            vy = -vy * 0.75; // Bounciness
            if (Math.abs(vx) < 0.2) vx = 0;
        }
        
        // ชนเพดาน
        if (y < 0) { y = 0; vy = -vy * 0.8; }

        // ชนกำแพงซ้ายขวา
        if (x < 0 || x + size > GamePanel.WIDTH) {
            vx = -vx * 0.85;
            x = (x < 0) ? 0 : GamePanel.WIDTH - size;
        }
    }

    public void draw(Graphics2D g) {
        if (ballImage != null) g.drawImage(ballImage, x, y, size, size, null);
        else { g.setColor(Color.BLACK); g.fillOval(x, y, size, size); }
    }

    public Rectangle getBounds() { return new Rectangle(x, y, size, size); }
}