package ac.abdn.cs3524.mud.server;

import ac.abdn.cs3524.mud.common.GameInterface;
import ac.abdn.cs3524.mud.common.PlayerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface MudServerInterface extends Remote {

    /**
     * Creates a new player
     * @param name the players display name
     * @return
     * @throws RemoteException
     */
    abstract PlayerInterface newPlayer(String name) throws RemoteException;

    abstract PlayerInterface joinGame(UUID gameID, String playerName) throws RemoteException;

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

    abstract String ping() throws RemoteException;
}
