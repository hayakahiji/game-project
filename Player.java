import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Player {
    int x = 150, y = 430;
    int width = 80, height = 80;
    int speed = 6;
    double dy = 0;
    boolean onGround = false;
    int facing = 1; 

    BufferedImage[] frames = new BufferedImage[6];
    int currentFrame = 0, animationTick = 0;

    public Player() {
        loadSprites();
    }

    private void loadSprites() {
        try {
            BufferedImage spriteSheet = ImageIO.read(new File("src/player/Dog/Walk.png"));
            int w = spriteSheet.getWidth() / 6;
            for (int i = 0; i < 6; i++) frames[i] = spriteSheet.getSubimage(i * w, 0, w, spriteSheet.getHeight());
        } catch (Exception e) { System.out.println("Player Image Load Error"); }
    }

    public void update(boolean left, boolean right, boolean jump) {
        if (left) { x -= speed; facing = -1; }
        if (right) { x += speed; facing = 1; }
        if (jump && onGround) { dy = -16; onGround = false; }

        dy += 0.8;
        y += dy;

        if (y + height >= GamePanel.GROUND_Y) {
            y = GamePanel.GROUND_Y - height;
            dy = 0;
            onGround = true;
        }

        if (x < 0) x = 0;
        if (x > GamePanel.WIDTH - width) x = GamePanel.WIDTH - width;

        if (left || right) {
            animationTick++;
            if (animationTick >= 8) { currentFrame = (currentFrame + 1) % 6; animationTick = 0; }
        } else currentFrame = 0;
    }

    public void draw(Graphics2D g) {
        if (frames[currentFrame] != null) {
            if (facing == 1) g.drawImage(frames[currentFrame], x, y, width, height, null);
            else g.drawImage(frames[currentFrame], x + width, y, -width, height, null);
        }
    }

    public Rectangle getBounds() { return new Rectangle(x, y, width, height); }
}