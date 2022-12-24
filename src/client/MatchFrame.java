package client;

import model.Giocatore;
import server.MatchMackingImp;
import server.MatchMackingService;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MatchFrame extends JFrame {
    private Giocatore giocatore, avversario;
    PlayPanel scacchiera;
    public MatchFrame(String title, Giocatore giocatore, Giocatore avversario) throws HeadlessException, RemoteException {
        super(title + " - " + giocatore.getColore());
        this.giocatore = giocatore;
        this.avversario = avversario;
        this.initializeFrame();
        JPanel contentPane = new JPanel(new BorderLayout());
        this.scacchiera = new PlayPanel(this);
        contentPane.add(this.scacchiera, BorderLayout.CENTER);
        this.setContentPane(contentPane);
        this.setVisible(true);
        MatchMackingService mms = null;
        try {
            mms = (MatchMackingService) Naming.lookup("rmi://localhost:5099/chess");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        mms.leaveMatchmaking(giocatore);
    }

    public void setSize() {
        this.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)screenSize.getHeight()-this.getInsets().top-100;
        this.setSize(height, height);
    }

    public void initializeFrame() {
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
