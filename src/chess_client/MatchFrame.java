package chess_client;

import javax.swing.*;
import java.awt.*;

public class MatchFrame extends JFrame {
    private String color;
    private String opponent;
    public MatchFrame(String title, String[] gameInfo) throws HeadlessException {
        super(title + " - " + gameInfo[1]);
        this.color = gameInfo[1];
        this.opponent = gameInfo[0];
        this.setLayout(new BorderLayout());
        this.setSize(1000, 1000);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //LOSS BIANCHI
        LossPanel lossBianchi = new LossPanel();
        //LOSS NERI
        LossPanel lossNeri = new LossPanel();
        //SCHACCHIERA
        PlayPanel scacchiera = new PlayPanel(color, opponent);

        this.add(lossNeri, BorderLayout.PAGE_START);
        this.add(scacchiera, BorderLayout.CENTER);
        this.add(lossBianchi, BorderLayout.PAGE_END);
        this.setVisible(true);
    }
}
