import javax.swing.*;

public class MenuPanel extends JPanel {

    public MenuPanel(Gameframe frame) {
        JButton startBtn = new JButton("Start Game");
        startBtn.addActionListener(e -> {
            frame.startGame();
        });
        add(startBtn);
    }
    
}
