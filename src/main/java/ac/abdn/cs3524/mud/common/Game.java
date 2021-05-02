package ac.abdn.cs3524.mud.common;

import ac.abdn.cs3524.mud.client.ClientInterface;
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
    private final PlayerManager playerManager;      // Interface for players to interact with game
    private final List<PlayerInterface> players;    // Players in the game
    private final int playerLimit;

    public Game(MUD world, List<PlayerInterface> players) {
        this.id = UUID.randomUUID();
        this.world = world;
        this.players = players;
        this.playerLimit = Integer.parseInt(CONFIG.getProperty("game.default-player-limit").orElse("10"));
        this.playerManager = new MUDPlayerManager(world);
    }

    public static Game fromID(String mapID) throws IllegalArgumentException{
        try {
            String mapDir = CONFIG.getProperty("game.content-directory").orElseThrow() + "/maps/" + mapID;
            return new Game(new MUD(mapDir + "/map.edg", mapDir + "/map.msg", mapDir + "/map.thg"), new ArrayList<>());
        }
        catch (Exception e){
            LOGGER.error("Could not load game: {}", e.getMessage());
            throw new IllegalArgumentException("Could not load game");
        }
    }

    @Override
    public UUID getID() {
        return id;
    }

    @Override
    public PlayerInterface joinGame(ClientInterface client, String playerName) throws RemoteException {
        try {
            if (players.size() >= playerLimit) throw new RemoteException("Can't join game: full");

            // Create a new player for this game and add it to the world
            PlayerInterface player = new Player(playerName, client,world.startLocation(), playerManager, this);
            players.add(player);

            return player;
        }
        catch (Exception e){
            LOGGER.error("Player {} could not join game {} : {}",playerName, this.id, e.getMessage());
            throw new RemoteException("Could not join game");
        }
    }

    @Override
    public int playerCount() throws RemoteException {
        return players.size();
    }

    @Override
    public PlayerManager getPlayerManager() throws RemoteException {
        return this.playerManager;
    }

    @Override
    public boolean removePlayer(PlayerInterface player) throws RemoteException {
        world.delThing(player.getLocation(),player.getName());
        return true;
    }

    @Override
    public List<PlayerInterface> getList() throws RemoteException {
        return this.players;
    }
}
