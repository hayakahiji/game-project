import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    Image background;
    Image cloundImage;
    Image groundImage;

    public GamePanel() {

        Image bg = new ImageIcon("src/backgrounds_frame/background_1/1.png").getImage();
        Image clound = new ImageIcon("src/backgrounds_frame/background_1/6.png").getImage();
        Image ground = new ImageIcon("src/ground/PNG/Green_ground.png").getImage();
        background = bg.getScaledInstance(500, 400, Image.SCALE_SMOOTH);
        cloundImage = clound.getScaledInstance(  1000, 600, Image.SCALE_SMOOTH);
        groundImage = ground.getScaledInstance(1000, 200, Image.SCALE_SMOOTH);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // g.drawString("Hello Game!", 100, 100);
        // วาดเต็ม panel
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(groundImage, 0, 380, this);
        g.drawImage(cloundImage, 0, -150, this);

    }
}
