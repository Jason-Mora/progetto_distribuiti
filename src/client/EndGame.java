package client;

import model.Giocatore;

import javax.swing.*;
import java.awt.*;

public class EndGame extends JFrame {

    public EndGame(Giocatore g, String vincitore)
    {
        super();
        this.setTitle(g.getColore().equals(vincitore)?"Hai vinto":"Hai perso");
        this.setSize(300, 300);
        //PANNELLO
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        //LABEL
        JLabel label = new JLabel(g.getColore().equals(vincitore)?"Hai vinto":"Hai perso");
        panel.add(label);

        this.setContentPane(panel);
    }
}
