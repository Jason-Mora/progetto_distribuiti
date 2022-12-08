package chess_client;
import chess_server.MatchMakingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args)
    {
        //FINESTRA
        JFrame frame = new MainFrame("SCACCHI");
    }
}
