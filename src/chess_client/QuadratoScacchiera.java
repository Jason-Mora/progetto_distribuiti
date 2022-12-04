package chess_client;

import javax.swing.*;
import java.awt.*;

public class QuadratoScacchiera extends JButton {
    public QuadratoScacchiera(Color backgColor, String colorePezzo, String tipoPezzo) {
        super();
        this.setBackground(backgColor);
        if(tipoPezzo != null) {
            ImageIcon icon;
            if (colorePezzo.equals("nero")) {
                icon = new ImageIcon("./resources/pezzi_neri/" + tipoPezzo + ".png");
            } else {
                icon = new ImageIcon("./resources/pezzi_bianchi/" + tipoPezzo + ".png");
            }
            this.setIcon(new ImageIcon((icon.getImage()).getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH)));
        }
    }
}
