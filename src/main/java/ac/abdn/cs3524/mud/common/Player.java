package ac.abdn.cs3524.mud.common;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player implements PlayerInterface {
    public final UUID id;
    private String name;
    private String location;
    private final PlayerManager manager;
    private final List<String> inventory;

    public Player(String name, String startLocation, PlayerManager playerManager){
        this.id = UUID.randomUUID();
        this.name = name;
        this.location = startLocation;
        this.manager = playerManager;
        this.inventory = new ArrayList<>();
        manager.drop(this,this.name);
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
        if (manager.pickup(this,item)) {
            inventory.add(item);
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean drop(String item) throws RemoteException {
        return  (inventory.remove(item) && manager.drop(this, item));
    }

    @Override
    public List<String> getInventory() throws RemoteException {
        return inventory;
    }
}
