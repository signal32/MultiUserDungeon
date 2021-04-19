package ac.abdn.cs3524.mud.server;

import ac.abdn.cs3524.mud.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class MudServer implements MudServerInterface {

    private static final Config CONFIG = Config.getConfig();
    private static final Logger LOGGER = LoggerFactory.getLogger(MudServer.class);

    //private List<GameInterface> games;   // Active games
    private final Map<UUID,GameInterface> games;
    private final List<PlayerInterface> players; // Active players

    public MudServer() {
        this.games = new HashMap<>();
        this.players = new ArrayList<>();
    }

    @Override
    public PlayerInterface newPlayer(String name) throws RemoteException {
        try{
            PlayerInterface player = new Player(name);
            players.add(player);
            LOGGER.info("Player {} connected", player.getName());
            return (PlayerInterface) UnicastRemoteObject.exportObject(player, Integer.parseInt(CONFIG.getProperty("server.port").orElseThrow()));
        }
        catch (Exception e) {
            LOGGER.error("Server could not create new player: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public PlayerInterface joinGame(UUID gameID, String playerName) throws RemoteException{
        try{
            GameInterface game = games.get(gameID);
            PlayerInterface player = newPlayer(playerName);
            game.joinGame(player);
            return player;
        }
        catch (Exception e){
            LOGGER.error("Server could not join game: {}", e.getMessage());
            throw new RemoteException("Server could not join game:" + e.getMessage());
        }
    }

    @Override
    public GameInterface newGame(String mapID) throws RemoteException {
        try {
            GameInterface game = Game.fromID(mapID);
            this.games.put(game.getID(),game);
            LOGGER.info("New game created");
            return (GameInterface) UnicastRemoteObject.exportObject(game, Integer.parseInt(CONFIG.getProperty("server.port").orElseThrow()));
        }
        catch (Exception e) {
            LOGGER.error("Server could not create new game: {}", e.getMessage());
            throw new RemoteException("Server could not create new game: " + e.getMessage());
        }
    }

    @Override
    public GameInterface newGame(String name, String edges, String messages, String things) throws RemoteException {
        LOGGER.error("Not implemented");
        return null;
    }

    @Override
    public GameInterface getGame(UUID gameID) throws RemoteException {
        return null;
    }

    @Override
    public String ping() throws RemoteException {
        return "pong";
    }
}
