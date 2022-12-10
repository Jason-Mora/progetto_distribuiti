package server;

import model.Giocatore;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatchMakingService extends Remote {
    public boolean enterMatchMaking(Giocatore giocatore) throws RemoteException;
    public boolean leaveMatchmaking(Giocatore giocatore) throws RemoteException;
    public Giocatore getOpponent(Giocatore giocatore) throws RemoteException;
    public String getColore(Giocatore giocatore) throws RemoteException;
}
