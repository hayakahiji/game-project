import java.awt.FlowLayout;

import javax.swing.*;

public class Gameframe extends JFrame{
        public Gameframe() {
            add(new GamePanel());
            setTitle("Game");
            setSize(1000,600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            setResizable(false);
    }
}
