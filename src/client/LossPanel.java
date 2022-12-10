package client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LossPanel extends JPanel {
    private ArrayList<JButton> pedine;
    public LossPanel() {
        pedine = new ArrayList<>();
        this.setSize(new Dimension(700, 50));
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton b = new JButton("pedine mangiate:");
        b.setSize(50, 50);
        b.setMinimumSize(new Dimension(50, 50));
        b.setEnabled(false);
        b.setBorder(null);
        this.add(b);
        this.setBackground(Color.red);
    }

    public void addPedina(String colore, String tipoPezzo) {
        QuadratoScacchiera b = new QuadratoScacchiera(colore, tipoPezzo);
        this.add(b);
        pedine.add(b);
    }
}
