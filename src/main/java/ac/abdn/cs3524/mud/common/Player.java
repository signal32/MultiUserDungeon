package ac.abdn.cs3524.mud.common;

import java.rmi.RemoteException;
import java.util.UUID;

public class Player implements PlayerInterface {
    private final UUID id;
    private String name;
    private String location;

    public Player(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.location = "";
    }

    @Override
    public void setName(String name) throws RemoteException {
        this.name = name;
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public void setLocation(String location) throws RemoteException {
        this.location = location;
    }

    @Override
    public String getLocation() throws RemoteException {
        return this.location;
    }

    @Override
    public boolean move(String direction) throws RemoteException {
        return false;
    }

    @Override
    public boolean pickUp(String item) throws RemoteException {
        return false;
    }

    @Override
    public boolean drop(String item) throws RemoteException {
        return false;
    }
}
