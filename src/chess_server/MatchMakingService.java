package chess_server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatchMakingService extends Remote {
    public boolean enterMatchMaking(String IP) throws RemoteException;
    public boolean leaveMatchmaking(String IP) throws RemoteException;
    public String[] getOpponentIP(String IP) throws RemoteException;
}
