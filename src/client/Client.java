package client;

import model.Giocatore;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        //FINESTRA
        MainFrame frame = new MainFrame("SCACCHI");

        Giocatore avversario;
        Giocatore giocatore;
        //attendo di essere assegnato ad un giocatore avversario
        while(true)
        {
            avversario = frame.getAvversario();
            Thread.sleep(100);
            if(avversario != null) {
                break;
            }
        }

        //sono stato assegnato
        if(avversario!=null)
        {
            giocatore = frame.getGiocatore();
            frame.dispose();
            MatchFrame matchFrame = new MatchFrame("gioco", frame.getGiocatore(), avversario);

            //SOCKET SERVER
            ServerSocket serverSocket = new ServerSocket(giocatore.getPort());
            Socket avversarioSocket = avversario.getSocket();
            DataInputStream readSocket = new DataInputStream(avversarioSocket.getInputStream());
            DataOutputStream writeSocket = new DataOutputStream(serverSocket.accept().getOutputStream());

            //GAME
            boolean turno = giocatore.isWhite();
            PlayPanel scacchiera = matchFrame.getScacchiera();
            scacchiera.setTurno(turno);
            while(!scacchiera.giocoFinito())
            {
                if(turno)
                {
                    String move = null;
                    while(move==null)
                    {
                        move = scacchiera.getMove();
                        Thread.sleep(100);
                        if(move != null) {
                            break;
                        }
                    }
                    scacchiera.resetMove();
                    writeSocket.writeUTF(move);
                    writeSocket.flush();
                    turno = false;
                    scacchiera.setTurno(false);
                }
                else
                {
                    String str = readSocket.readUTF();
                    String[] moveString = str.split(" ");
                    scacchiera.muoviPezzo(Integer.parseInt(moveString[0]), Integer.parseInt(moveString[1]), Integer.parseInt(moveString[2]), Integer.parseInt(moveString[3]));
                    turno = true;
                    scacchiera.setTurno(true);
                }
            }
            EndGame endGameFrame = new EndGame(giocatore, scacchiera.getVincitore());
            matchFrame.dispose();
            System.out.println("FINE");
            endGameFrame.setVisible(true);
        }
    }
}
