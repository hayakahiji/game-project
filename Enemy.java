import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Enemy {
    int x, y, width = 80, height = 80;
    double dy = 0;
    boolean onGround = false;
    int direction = -1;
    int speed = 5;

    BufferedImage[] frames = new BufferedImage[6];
    int currentFrame = 0, animationTick = 0;

    public Enemy(int startX, int startY, int distance) {
        this.x = startX;
        this.y = startY;
        loadSprites();
    }

    private void loadSprites() {
        try {
            BufferedImage spriteSheet = ImageIO.read(new File("src/player/Cat/Walk.png"));
            int w = spriteSheet.getWidth() / 6;
            for (int i = 0; i < 6; i++) frames[i] = spriteSheet.getSubimage(i * w, 0, w, spriteSheet.getHeight());
        } catch (Exception e) { System.out.println("Enemy Image Load Error"); }
    }

    public void update(Ball ball) {
        // AI: เดินหาลูกบอล
        if (ball.x > x + width/2) { x += speed; direction = 1; }
        else { x -= speed; direction = -1; }

        // AI: กระโดดโหม่งถ้าบอลลอยมาใกล้หัว
        if (onGround && ball.y < y && Math.abs(ball.x - x) < 50) {
            dy = -15;
            onGround = false;
        }

        dy += 0.8;
        y += dy;

        if (y + height >= GamePanel.GROUND_Y) {
            y = GamePanel.GROUND_Y - height;
            dy = 0;
            onGround = true;
        }

        // ป้องกันไม่ให้ Bot วิ่งเข้าประตูตัวเองเกินไป
        if (x < 280) x = 280; 
        if (x > GamePanel.WIDTH - width) x = GamePanel.WIDTH - width;

        animationTick++;
        if (animationTick >= 10) { currentFrame = (currentFrame + 1) % 6; animationTick = 0; }
    }

    public void draw(Graphics2D g) {
        if (frames[currentFrame] != null) {
            if (direction == 1) g.drawImage(frames[currentFrame], x, y, width, height, null);
            else g.drawImage(frames[currentFrame], x + width, y, -width, height, null);
        }
    }

    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }
}