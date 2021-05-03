package ac.abdn.cs3524.mud.common;

import ac.abdn.cs3524.mud.client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

/**
 * Models am abstract game and operations which can be performed on it.
 */
public interface GameInterface extends Remote {

    /**
     * Get a unique identifier to this game
     * @return unique id
     */
    UUID getID() throws RemoteException;

    /**
     * Allow a client to join the game
     * @param client client to join
     * @param playerName their name
     * @return an interface to this player
     */
    PlayerInterface joinGame(ClientInterface client, String playerName) throws RemoteException;

    /**
     * @return The number of players within the game
     */
    int playerCount() throws RemoteException;

    /**
     * Get a manager utility which implements interaction logic between player and underlying game world
     * @return an existing player manager
     */
    PlayerManager getPlayerManager() throws RemoteException;

    /**
     * Remove the given player from the game
     * @param player the player to remove
     * @return true if removal was successfully, false otherwise
     */
    boolean removePlayer(PlayerInterface player)throws RemoteException;

    /**
     * Get a list of players within the current game
     * @return list of players
     */
    List<PlayerInterface> getList() throws RemoteException;

    /**
     * Send a message to a player within a game
     * @param senderName origin of message
     * @param receiverName name of the receiver (player/entity) who will receive the message (must exist)
     * @param message the message body
     * @return true if message was delivered, false otherwise
     */
    boolean sendMessage(String senderName, String receiverName, String message) throws  RemoteException;

    /**
     * Check if a named player exists within the game
     * @param name of player
     * @return true if player exists, false otherwise
     */
    boolean doesPlayerExist(String name) throws RemoteException;
}
