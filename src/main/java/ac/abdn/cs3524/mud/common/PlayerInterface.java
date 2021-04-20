package ac.abdn.cs3524.mud.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerInterface extends Remote {

    abstract void setName(String name) throws RemoteException;
    abstract String getName() throws RemoteException;

    abstract void setLocation(String location) throws RemoteException;
    abstract String getLocation() throws RemoteException;
    abstract String getLocationInfo() throws RemoteException;

    abstract boolean move(String direction) throws RemoteException;

    abstract boolean pickUp(String item) throws RemoteException;

    abstract boolean drop(String item) throws RemoteException;
}
