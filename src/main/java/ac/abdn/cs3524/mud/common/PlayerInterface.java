package ac.abdn.cs3524.mud.common;

import ac.abdn.cs3524.mud.client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PlayerInterface extends Remote {

    /**
     * Give the player a name
     */
    abstract void setName(String name) throws RemoteException;

    /**
     * Get the players name
     */
    abstract String getName() throws RemoteException;

    /**
     * Set the players location
     */
    abstract void setLocation(String location) throws RemoteException;

    /**
     * Get the players location
     */
    abstract String getLocation() throws RemoteException;

    /**
     * Get information about the players location
     */
    abstract String getLocationInfo() throws RemoteException;

    /**
     * Move the players in a names direction
     */
    abstract boolean move(String direction) throws RemoteException;

    /**
     * Pickup an item at the players location
     */
    abstract boolean pickUp(String item) throws RemoteException;

    /**
     * Drop an item at the players location
     */
    abstract boolean drop(String item) throws RemoteException;

    /**
     * A list of things the player has picked up and stored in their inventory
     */
    abstract List<String> getInventory() throws RemoteException;

    /**
     * Get the client responsible for this player
     */
    abstract ClientInterface getClient() throws RemoteException;
}
