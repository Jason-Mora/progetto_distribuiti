package chess_client;

import chess_server.MatchMakingService;

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
    static MatchMakingService mms;
    static String IP;
    static Integer PORT;
    public MainFrame(String title) throws HeadlessException {
        super(title);
        this.setSize(400,500);//400 width and 500 height
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //PANEL
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //LABEL
        JLabel label = new JLabel(IP+":"+PORT);
        label.setBounds(130,60,100, 40);
        //BOTTONE
        JButton button = new JButton("Nuova partita");
        button.addActionListener(this);
        button.setBounds(130,100,100, 40);

        panel.add(label);
        panel.add(button);
        this.add(panel);
        this.setVisible(true);//making the frame visible
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            mms = (MatchMakingService) Naming.lookup("rmi://localhost:5099/chess");
            IP = InetAddress.getLocalHost().getHostAddress();
            PORT = (new ServerSocket(0)).getLocalPort();
        }
        catch (NotBoundException | IOException e) {
            mms = null;
            IP = null;
            PORT = null;
            throw new RuntimeException(e);
        }
        System.out.println("Inizia partita");
        if(IP != null && mms != null && PORT != null) {
            try {
                if (mms.enterMatchMaking(IP+":"+PORT)) {
                    String[] opponent = mms.getOpponentIP(IP+":"+PORT);
                    long startTime = System.currentTimeMillis();
                    while(opponent[0] == null && System.currentTimeMillis()-startTime<=6000)
                    {
                        opponent = mms.getOpponentIP(IP+":"+PORT);
                    }
                    if (opponent[0] == null) {
                        mms.leaveMatchmaking(IP+":"+PORT);
                        System.out.println("Dopo un minuto non è stato possibile trovare un giocatore, riprovare più tardi");
                    } else {
                        System.out.println("Giocatore con IP " + opponent[0] + " accoppiato");
                        JFrame matchFrame = new MatchFrame("gioco", opponent);
                        this.dispose();
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
}
