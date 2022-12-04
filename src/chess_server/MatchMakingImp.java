package chess_server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class MatchMakingImp extends UnicastRemoteObject implements MatchMakingService {
    private LinkedList<String> waitingPlayer;
    private Map<String, String[]> matchedPlayer;

    public MatchMakingImp() throws RemoteException {
        super();
        waitingPlayer = new LinkedList<>();
        matchedPlayer = new HashMap<>();
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
    public String[] getOpponentIP(String IP) throws RemoteException {
        String[] res = new String[2];
        res[0]=null;
        res[1]=null;
        waitingPlayer.remove(IP);
        for (String key : matchedPlayer.keySet()) {
            if (matchedPlayer.get(key)[0] != null && matchedPlayer.get(key)[0].equals(IP)) {
                res[0] = key;
                res[1] = matchedPlayer.get(key)[1].equals("nero")?"bianco":"nero";
                return res;
            }
        }
        String opponent = waitingPlayer.pollFirst();
        res[0] = opponent;
        res[1] = (new Random()).nextInt(100)%2==0?"nero":"bianco";
        matchedPlayer.put(IP, res);
        return res;
    }
}
