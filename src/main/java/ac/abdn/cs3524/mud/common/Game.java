package ac.abdn.cs3524.mud.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A MUD game with one world and multiple unique players.
 */
public class Game implements GameInterface {

    private static final Config CONFIG = Config.getConfig();
    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    private final UUID id;
    private final MUD world;
    private final List<PlayerInterface> players;
    private final int playerLimit;

    public Game(MUD world, List<PlayerInterface> players) {
        this.id = UUID.randomUUID();
        this.world = world;
        this.players = players;
        this.playerLimit = Integer.parseInt(CONFIG.getProperty("game.default-player-limit").orElse("10"));
    }

    public static Game fromID(String mapID) throws IllegalArgumentException{
        try {
            String mapDir = CONFIG.getProperty("game.content-directory").orElseThrow() + "/maps/" + mapID;
            return new Game(new MUD(mapDir + "/map.edg", mapDir + "/map.msg", mapDir + "/map.thg"), new ArrayList<>());
        }
        catch (Exception e){
            throw new IllegalArgumentException("Could not load game: " + e.getMessage());
        }
    }

    @Override
    public UUID getID() {
        return id;
    }

    @Override
    public boolean joinGame(PlayerInterface player) throws RemoteException, IllegalArgumentException {

        if (players.size() >= playerLimit) throw new IllegalArgumentException("Can't join game: full");

        players.add(player);                        // Add player to game world
        player.setLocation(world.startLocation());  // Set player defaults

        return true;
    }

    @Override
    public int playerCount() throws RemoteException {
        return players.size();
    }
}
