package ac.abdn.cs3524.mud.common;

import ac.abdn.cs3524.mud.client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface GameInterface extends Remote {

    UUID getID() throws RemoteException;
    PlayerInterface joinGame(ClientInterface client, String playerName) throws RemoteException;
    int playerCount() throws RemoteException;
    PlayerManager getPlayerManager() throws RemoteException;
    boolean removePlayer(PlayerInterface player)throws RemoteException;
    List<PlayerInterface> getList() throws RemoteException;
    boolean sendMessage(String senderName, String receiverName, String message) throws  RemoteException;
    boolean doesPlayerExist(String name) throws RemoteException;
}
