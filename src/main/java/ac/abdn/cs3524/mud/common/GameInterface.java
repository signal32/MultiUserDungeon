package ac.abdn.cs3524.mud.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface GameInterface extends Remote {

    UUID getID() throws RemoteException;
    boolean joinGame(PlayerInterface player) throws RemoteException;
    int playerCount() throws RemoteException;
}
