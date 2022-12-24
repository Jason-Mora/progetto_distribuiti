package client;

import model.Giocatore;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(0);

        //FINESTRA
        MainFrame mainFrame = new MainFrame("SCACCHI", serverSocket.getInetAddress().getHostAddress(), serverSocket.getLocalPort());

        Giocatore avversario;
        Giocatore giocatore;
        //attendo di essere assegnato ad un giocatore avversario
        while(true)
        {
            avversario = mainFrame.getAvversario();
            Thread.sleep(100);
            if(avversario != null) {
                break;
            }
        }

        //sono stato assegnato
        if(avversario!=null)
        {
            giocatore = mainFrame.getGiocatore();
            mainFrame.setVisible(false);
            MatchFrame matchFrame = new MatchFrame("gioco", mainFrame.getGiocatore(), avversario);

            //SOCKET SERVER
            Socket avversarioSocket = avversario.getSocket();
            DataInputStream readSocket = new DataInputStream(avversarioSocket.getInputStream());
            DataOutputStream writeSocket = new DataOutputStream(serverSocket.accept().getOutputStream());

            //GAME
            boolean turno = giocatore.isWhite();
            boolean connectionError = false;
            PlayPanel scacchiera = matchFrame.getScacchiera();
            scacchiera.setTurno(turno);
            try {
                while (!scacchiera.giocoFinito()) {
                    if (turno) {
                        String move = null;
                        while (move == null) {
                            move = scacchiera.getMove();
                            Thread.sleep(100);
                            if (move != null) {
                                break;
                            }
                        }
                        scacchiera.resetMove();
                        writeSocket.writeUTF(move);
                        writeSocket.flush();
                        turno = false;
                        scacchiera.setTurno(false);
                    } else {
                        String str = readSocket.readUTF();
                        String[] moveString = str.split(" ");
                        scacchiera.muoviPezzo(Integer.parseInt(moveString[0]), Integer.parseInt(moveString[1]), Integer.parseInt(moveString[2]), Integer.parseInt(moveString[3]));
                        turno = true;
                        scacchiera.setTurno(true);
                    }
                }
            }
            catch (SocketException e)
            {
                connectionError = true;
            }
            finally {
                readSocket.close();
                writeSocket.close();
                avversarioSocket.close();
                serverSocket.close();
            }

            if(!connectionError) {
                EndGame endGameFrame = new EndGame(giocatore, scacchiera.getVincitore());
                matchFrame.dispose();
                System.out.println("FINE");
                endGameFrame.setVisible(true);
            }
            else {
                mainFrame.setVisible(true);
            }
        }
    }
}
