package chess_client;

import javax.swing.*;
import java.awt.*;

public class MatchFrame extends JFrame {
    public MatchFrame(String title) throws HeadlessException {
        super(title);
        this.setLayout(new BorderLayout());
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //LOSS BIANCHI
        LossPanel lossBianchi = new LossPanel();
        //LOSS NERI
        LossPanel lossNeri = new LossPanel();
        //SCHACCHIERA
        PlayPanel scacchiera = new PlayPanel();

        this.add(lossNeri, BorderLayout.PAGE_START);
        this.add(scacchiera, BorderLayout.CENTER);
        this.add(lossBianchi, BorderLayout.PAGE_END);
        this.setVisible(true);
    }
}
