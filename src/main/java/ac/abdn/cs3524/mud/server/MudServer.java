package ac.abdn.cs3524.mud.server;

import ac.abdn.cs3524.mud.common.Game;
import ac.abdn.cs3524.mud.common.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MudServer implements MudServerInterface {

    private List<Game> games;   // Active games

    public MudServer() {
        this.games = new ArrayList<>();
    }

    @Override
    public boolean init() throws RemoteException {
        this.games = new ArrayList<>();
        return true;
    }

    @Override
    public Player newPlayer(String name) throws RemoteException {
        return null;
    }

    @Override
    public Game newGame(String name, String edges) throws RemoteException {
        return null;
    }

    @Override
    public Game getGame(UUID gameID) throws RemoteException {
        return null;
    }
}
