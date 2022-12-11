package client;

import model.Giocatore;
import server.MatchMakingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainFrame extends JFrame implements ActionListener {

    private MatchMakingService mms;
    private Giocatore giocatore, avversario;
    private boolean matchMacking;
    public MainFrame(String title) throws HeadlessException {
        super(title);
        initializeFrame();
        //PANEL
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        //BOTTONE
        JButton button = new JButton("Nuova partita");
        button.addActionListener(this);
        button.setBounds(130,100,100, 40);

        panel.add(button);
        this.add(panel);
        this.setVisible(true);
    }

    public void initializeFrame() {
        this.giocatore = new Giocatore();
        this.matchMacking = true;
        setAvversario(null);
        this.setSize(400,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try
        {
            mms = (MatchMakingService) Naming.lookup("rmi://localhost:5099/chess");
            giocatore.setIp(InetAddress.getLocalHost().getHostAddress());
            giocatore.setCasualPort();
        }
        catch (NotBoundException | IOException e)
        {
            mms = null;
            giocatore.setIp(null);
            throw new RuntimeException(e);
        }
        System.out.println("Inizia partita");
        if(!giocatore.isGiocatoreNull() && mms!=null) {
            try {
                if (mms.enterMatchMaking(giocatore)) {
                    Giocatore avversario = mms.getOpponent(giocatore);
                    long startTime = System.currentTimeMillis();
                    while(avversario==null && System.currentTimeMillis()-startTime<=6000)
                    {
                        avversario = mms.getOpponent(giocatore);
                    }
                    if (avversario==null)
                    {
                        mms.leaveMatchmaking(giocatore);
                        System.out.println("Dopo un minuto non è stato possibile trovare un giocatore, riprovare più tardi");
                        this.setAvversario(null);
                        this.matchMacking = false;
                    }
                    else
                    {
                        System.out.println("Giocatore accoppiato -> " + giocatore.getFullIp() + " - " + avversario.getFullIp());
                        String colore = mms.getColore(giocatore);
                        giocatore.setColore(colore);
                        this.setAvversario(avversario);
                        this.matchMacking = false;
                    }
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("Impossibile avviare la partita. problema di connessione con il server");
        }
    }

    public void setAvversario(Giocatore avversario)
    {
        this.avversario = avversario;
    }

    public Giocatore getGiocatore() {
        return this.giocatore;
    }
    public Giocatore getAvversario() {
        return this.avversario;
    }

    public boolean isMatchMacking() {
        return this.matchMacking;
    }


}
