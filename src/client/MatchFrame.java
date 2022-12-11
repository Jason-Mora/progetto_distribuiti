package client;

import model.Giocatore;
import javax.swing.*;
import java.awt.*;

public class MatchFrame extends JFrame {
    private Giocatore giocatore, avversario;
    PlayPanel scacchiera;
    public MatchFrame(String title, Giocatore giocatore, Giocatore avversario) throws HeadlessException {
        super(title + " - " + giocatore.getColore());
        this.giocatore = giocatore;
        this.avversario = avversario;
        this.initializeFrame();
        //SCHACCHIERA
        this.scacchiera = new PlayPanel(this);
        this.setContentPane(scacchiera);
        this.setVisible(true);
    }

    public void setSize() {
        this.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)screenSize.getHeight()-this.getInsets().top-100;
        this.setSize(height, height);
    }

    public void initializeFrame()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize();
        this.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2-this.getSize().width/2, 0);
    }

    public PlayPanel getScacchiera()
    {
        return this.scacchiera;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }
    public Giocatore getAvversario() {
        return avversario;
    }
}
