package ac.abdn.cs3524.mud.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    abstract boolean ping() throws RemoteException;
    abstract String id() throws RemoteException;
    abstract void refresh() throws RemoteException;
}
