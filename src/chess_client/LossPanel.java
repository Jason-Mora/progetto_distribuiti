package chess_client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LossPanel extends JPanel {
    private ArrayList<JButton> pedine;
    public LossPanel() {
        pedine = new ArrayList<>();
        this.setLayout(new FlowLayout());
    }

    public void addPedina(JButton b) {
        this.add(b);
        pedine.add(b);
    }
}
