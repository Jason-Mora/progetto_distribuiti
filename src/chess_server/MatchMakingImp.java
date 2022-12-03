package chess_server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MatchMakingImp extends UnicastRemoteObject implements MatchMakingService {
    private LinkedList<String> waitingPlayer;
    private Map<String, String> matchedPlayer;

    public MatchMakingImp() throws RemoteException {
        super();
        waitingPlayer = new LinkedList<>();
        matchedPlayer = new HashMap<>();
    }

    @Override
    public String echo() throws RemoteException {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "ciao dal server";
    }

    @Override
    public boolean enterMatchMaking(String IP) throws RemoteException {
        System.out.println("Player " + IP + " ready for matchmaking");
        waitingPlayer.addLast(IP);
        return true;
    }

    @Override
    public boolean leaveMatchmaking(String IP) throws RemoteException {
        System.out.println("Player " + IP + " leave matchmaking");
        if (waitingPlayer.contains(IP)) {
            waitingPlayer.remove(IP);
            matchedPlayer.remove(matchedPlayer.get(IP));
            matchedPlayer.remove(IP);
            return true;
        }
        return false;
    }

    @Override
    public String getOpponentIP(String IP) throws RemoteException {
        waitingPlayer.remove(IP);
        if (matchedPlayer.containsValue(IP)) {
            for (String key : matchedPlayer.keySet()) {
                System.out.println(key);
                if (matchedPlayer.get(key) != null && matchedPlayer.get(key).equals(IP))
                    return key;
            }
        }
        String opponent = waitingPlayer.pollFirst();
        matchedPlayer.put(IP, opponent);
        return opponent;
    }
}
