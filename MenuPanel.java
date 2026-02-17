import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private Image background, cloundImage, groundImage;

    public MenuPanel(Gameframe frame) {
        JButton startBtn = new JButton("Start Game");
        startBtn.addActionListener(e -> {
            frame.startGame();
        });
        add(startBtn);
        try {
            background = new ImageIcon("src/backgrounds_frame/background_1/1.png").getImage();
            cloundImage = new ImageIcon("src/backgrounds_frame/background_1/6.png").getImage();
            groundImage = new ImageIcon("src/ground/PNG/Green_ground.png").getImage();
        } catch (Exception e) {
            System.out.println("Error: หาไฟล์ภาพไม่เจอ!");
        }
    }
@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // วาดภาพโดยกำหนดขนาดให้เท่ากับขนาดของ Panel ปัจจุบัน (getWidth, getHeight)
        if (background != null) {
            g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
        
        if (cloundImage != null) {
            g2d.drawImage(cloundImage, 0, -150, getWidth(), getHeight(), this);
        }

        if (groundImage != null) {
            // วาดพื้นไว้ด้านล่าง (getHeight - 150)
            g2d.drawImage(groundImage, 0, getHeight() - 150, getWidth(), 150, this);
        }
    }
    
}
