package ac.abdn.cs3524.mud.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Basic remote interface which any client implementation must make available
 */
public interface ClientInterface extends Remote {
    /**
     * Ping the server, useful for connection verification
     * @return true is okay, false otherwise.
     * @throws RemoteException throw if there is a communications breakdown
     */
    abstract boolean ping() throws RemoteException;

    /**
     * gets an identifier for this client, which should be unique
     * @return identifier as a string
     */
    abstract String id() throws RemoteException;

    /**
     * Instruct the client to refresh it's status,
     * usually called when the game world or some other state has changed
     */
    abstract void refresh() throws RemoteException;

    /**
     * Send a message to the client
     * @param senderName name of the entity from which the message originated
     * @param message message body
     */
    abstract void receiveMessage(String senderName, String message) throws RemoteException;
}
