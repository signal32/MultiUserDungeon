package ac.abdn.cs3524.mud.common;

import ac.abdn.cs3524.mud.server.MudServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MUDPlayerManager implements PlayerManager{

    private static final Logger LOGGER = LoggerFactory.getLogger(MudServer.class);

    private MUD mud;

    public MUDPlayerManager(MUD mud){
        this.mud = mud;
    }

    @Override
    public boolean move(PlayerInterface player, String direction) throws IllegalArgumentException{
        try {
            String newLocation = mud.moveThing(player.getLocation(), direction, player.getName());
            if (newLocation.matches(player.getLocation())) return false;
            else {
                player.setLocation(newLocation);
                return true;
            }
        }
        catch (Exception e){
            LOGGER.error("Could not move player: {}", e.getMessage());
            throw new IllegalArgumentException("Could not move player");
        }
    }

    @Override
    public boolean pickup(PlayerInterface player, String objectName) throws IllegalArgumentException {
        // TODO implement
        return false;
    }

    @Override
    public boolean drop(PlayerInterface player, String objectName) throws IllegalArgumentException {
        // TODO implement
        return false;
    }
}
