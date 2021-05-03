package ac.abdn.cs3524.mud.server;

import ac.abdn.cs3524.mud.client.ClientInterface;
import ac.abdn.cs3524.mud.common.GameInterface;
import ac.abdn.cs3524.mud.common.PlayerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface MudServerInterface extends Remote {

    /**
     * Allow a registered client to join a game
     * @param gameID game identifier (must be active)
     * @param playerName name to join game as
     * @param clientID id of the client (must be registered with client)
     * @return A new player for the given client for the game and with the name specified
     */
    abstract PlayerInterface joinGame(UUID gameID, String playerName, String clientID) throws RemoteException;

    /**
     * Removes a player safely from a game
     * @param game the game
     * @param player the player
     */
    abstract void leaveGame(GameInterface game, PlayerInterface player) throws RemoteException;

    /**
     * Create a new game using MUD loaded from file system
     * @param mapID the folder in which map files exist - contains map.edg, map.msg, map.thg
     * @return Interface for generated game
     */
    abstract GameInterface newGame(String mapID) throws RemoteException;

    /**
     * Create a new game using MUD generated from supplied map parameters
     * Interface for generated game
     * @param name friendly name for the generated map
     * @param edges
     * @param messages
     * @param things
     * @return
     */
    abstract GameInterface newGame(String name, String edges, String messages, String things) throws RemoteException;

    /**
     * Get an interface to an existing active game
     * @param gameID
     * @return Interface to the game
     * @throws RemoteException
     */
    abstract GameInterface getGame(UUID gameID) throws RemoteException;

    /**
     * Get list of all games that are active on server
     * @return list of game UUIDs
     * @throws RemoteException
     */
    abstract List<UUID> listGames() throws RemoteException;

    /**
     * Get a list of all maps/game worlds which the server can load
     * @return list of map names
     */
    abstract List<String> listMaps() throws RemoteException;

    /**
     * Make the server aware of a client. Allows server to make callbacks and handle further authentication
     * @param client client to register, must have ID
     */
    abstract void registerClient(ClientInterface client) throws RemoteException;

    /**
     * Remove a client from the servers registration list
     * @param clientID client, must be registered
     */
    abstract void deregisterClient(String clientID) throws RemoteException;

    abstract String ping() throws RemoteException;
}
