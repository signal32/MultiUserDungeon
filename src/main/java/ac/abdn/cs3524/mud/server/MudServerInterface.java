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


    abstract PlayerInterface joinGame(UUID gameID, String playerName) throws RemoteException;
    abstract void leaveGame(GameInterface game, PlayerInterface player) throws RemoteException;

    /**
     * Create a new game using MUD loaded from file system
     * @param mapID the folder in which map files exist - contains map.edg, map.msg, map.thg
     * @return Interface for generated game
     * @throws RemoteException
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
     * @throws RemoteException
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

    abstract List<String> listMaps() throws RemoteException;

    abstract String ping() throws RemoteException;

    abstract void registerClient(ClientInterface client) throws RemoteException;

    abstract void deregisterClient(String clientID) throws RemoteException;
}
