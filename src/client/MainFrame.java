package client;

import model.Giocatore;
import server.MatchMackingService;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainFrame extends JFrame implements ActionListener {

    private MatchMackingService mms;
    private Giocatore giocatore, avversario;
    private boolean matchMacking;
    private String ip;
    private int port;
    public MainFrame(String title, String ip, int port) throws HeadlessException, IOException {
        super(title);
        this.ip = ip;
        this.port = port;
        initializeFrame();
        //PANEL
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(new Insets(100, 100, 100, 100)));

        //BOTTONE NUOVA PARTITA
        JButton nuovaPartitaBtn = new JButton("Nuova partita");
        nuovaPartitaBtn.addActionListener(this);
        nuovaPartitaBtn.setAlignmentX(CENTER_ALIGNMENT);
        //BOTTONE CHIUDI
        JButton chiudiBtn = new JButton("Chiudi");
        chiudiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                Container frame = b.getParent();
                do
                    frame = frame.getParent();
                while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });
        chiudiBtn.setAlignmentX(CENTER_ALIGNMENT);

        JLabel titolo = new JLabel("SCACCHI");
        titolo.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(titolo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(nuovaPartitaBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(chiudiBtn);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

    public void initializeFrame() throws IOException {
        this.giocatore = new Giocatore();
        this.matchMacking = true;
        setAvversario(null);
        this.setSize(400,500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try
        {
            mms = (MatchMackingService) Naming.lookup("rmi://localhost:5099/chess");
            giocatore.setIp(ip);
            giocatore.setPort(port);
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
