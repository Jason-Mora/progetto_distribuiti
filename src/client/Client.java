package client;

import model.Giocatore;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Client {

    public static void main(String[] args) throws IOException {
        //FINESTRA
        MainFrame frame = new MainFrame("SCACCHI");

        Giocatore avversario = frame.getAvversario();
        while(avversario==null)
        {
            avversario = frame.getAvversario();
            System.out.println("khgghgg");
        }

        System.out.println("suuuu");


        if(avversario!=null)
        {
            frame.dispose();
            MatchFrame matchFrame = new MatchFrame("gioco", frame.getGiocatore(), avversario);

            //SOCKET SERVER
            ServerSocket ss = new ServerSocket(3333);
            Socket s = ss.accept();
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            //GAME
            String str = "", str2 = "";
            while (!str.equals("stop")) {
                str = din.readUTF();
                System.out.println("client says: " + str);
                str2 = br.readLine();
                dout.writeUTF(str2);
                dout.flush();
            }
        }
        else {
        }
    }
}
