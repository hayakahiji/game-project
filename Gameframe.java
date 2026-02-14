import java.awt.FlowLayout;

import javax.swing.*;

public class Gameframe extends JFrame {


    public Gameframe() {
        add(new GamePanel());
        setTitle("Game");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // ถ้าไม่มี frame ไม่ขึ้น
        setLocationRelativeTo(null);
        setResizable(false);
        showMenu();
    }

    public void showMenu() { // จะเป็นหน้าจอมีปุ่ม Start
        setContentPane(new MenuPanel(this));
        revalidate();
    }

    public void startGame() {
        setContentPane(new GamePanel());
        revalidate();
    }

}
