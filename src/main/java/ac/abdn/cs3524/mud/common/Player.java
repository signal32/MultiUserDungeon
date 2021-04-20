package ac.abdn.cs3524.mud.common;

import java.rmi.RemoteException;
import java.util.UUID;

public class Player implements PlayerInterface {
    public final UUID id;
    private String name;
    private String location;
    private final PlayerManager manager;

    public Player(String name, String startLocation, PlayerManager playerManager){
        this.id = UUID.randomUUID();
        this.name = name;
        this.location = startLocation;
        this.manager = playerManager;
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
    public String getLocationInfo() throws RemoteException {
        return this.manager.getLocationInfo(this.location);
    }

    @Override
    public boolean move(String direction) throws RemoteException {
        return manager.move(this,direction);
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
