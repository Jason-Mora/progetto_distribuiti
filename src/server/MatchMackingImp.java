package server;

import model.Giocatore;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class MatchMackingImp extends UnicastRemoteObject implements MatchMackingService {
    private LinkedList<Giocatore> waitingPlayer;
    private Map<Giocatore, Giocatore> matchedPlayer;

    public MatchMackingImp() throws RemoteException {
        super();
        waitingPlayer = new LinkedList<>();
        matchedPlayer = new HashMap<>();
    }

    @Override
    public boolean enterMatchMaking(Giocatore giocatore) throws RemoteException {
        System.out.println("Player " + giocatore.getFullIp() + " ready for matchmaking");
        waitingPlayer.addLast(giocatore);
        return true;
    }

    @Override
    public boolean leaveMatchmaking(Giocatore giocatore) throws RemoteException {
        System.out.println("Player " + giocatore.getFullIp() + " leave matchmaking");
        if (waitingPlayer.contains(giocatore)) {
            waitingPlayer.remove(giocatore);
            matchedPlayer.remove(matchedPlayer.get(giocatore));
            matchedPlayer.remove(giocatore);
            return true;
        }
        return false;
    }

    @Override
    public Giocatore getOpponent(Giocatore giocatore) throws RemoteException {
        waitingPlayer.remove(giocatore);
        Giocatore avversario = matchedPlayer.get(giocatore);
        if(avversario!=null)
            return avversario;
        avversario = waitingPlayer.pollFirst();
        if(avversario==null)
            return null;
        boolean isWhite = (new Random()).nextInt(100)%2==0;
        avversario.setColore(isWhite);
        giocatore.setColore(!isWhite);
        matchedPlayer.put(giocatore, avversario);
        matchedPlayer.put(avversario, giocatore);
        return avversario;
    }

    public String getColore(Giocatore giocatore) throws RemoteException {
        for (Giocatore key : matchedPlayer.keySet()) {
            if (key.equals(giocatore)) {
                return key.getColore();
            }
        }
        return null;
    }
}
