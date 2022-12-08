package chess_client;

import javax.swing.*;
import java.awt.*;

public class MatchFrame extends JFrame {
    private String color;
    private String opponent;
    public MatchFrame(String title, String[] gameInfo) throws HeadlessException {
        super(title + " - " + gameInfo[1]);
        this.setSize(800, 900);
        this.setResizable(false);
        this.color = gameInfo[1];
        this.opponent = gameInfo[0];
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        //LOSS BIANCHI
        LossPanel lossBianchi = new LossPanel();
        //LOSS NERI
        LossPanel lossNeri = new LossPanel();
        //SCHACCHIERA
        PlayPanel scacchiera = new PlayPanel(color, opponent, lossNeri, lossBianchi);

        mainPanel.add(lossNeri, BorderLayout.NORTH);
        mainPanel.add(scacchiera, BorderLayout.CENTER);
        mainPanel.add(lossBianchi, BorderLayout.SOUTH);

        this.setContentPane(mainPanel);
        this.setVisible(true);
    }
}
