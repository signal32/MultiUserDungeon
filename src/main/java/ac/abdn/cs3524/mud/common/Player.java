package ac.abdn.cs3524.mud.common;

import ac.abdn.cs3524.mud.client.ClientInterface;

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
    private final GameInterface game;
    private final ClientInterface client;

    public Player(String name, ClientInterface client, String startLocation, PlayerManager playerManager, GameInterface game){
        this.id = UUID.randomUUID();
        this.name = name;
        this.location = startLocation;
        this.manager = playerManager;
        this.inventory = new ArrayList<>();
        manager.drop(this,this.name);
        this.game = game;
        this.client = client;
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
        if (manager.move(this,direction)){
            for (var player : game.getList()){
                if (!player.getName().equals(this.name))
                    player.getClient().refresh();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean pickUp(String item) throws RemoteException {
        if (game.doesPlayerExist(item) == true) {
            return false;
        }

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

    @Override
    public ClientInterface getClient() throws RemoteException {
        return this.client;
    }
}
