package ac.abdn.cs3524.mud.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote {

    boolean joinGame(Player player) throws RemoteException;
    int playerCount() throws RemoteException;
}
