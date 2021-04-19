package ac.abdn.cs3524.mud.server;

import ac.abdn.cs3524.mud.common.Game;
import ac.abdn.cs3524.mud.common.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface MudServerInterface extends Remote {

    abstract boolean init() throws RemoteException;

    abstract Player newPlayer(String name) throws RemoteException;

    abstract Game newGame(String name, String edges) throws RemoteException;
    abstract Game getGame(UUID gameID) throws RemoteException;

    abstract String ping() throws RemoteException;
}
