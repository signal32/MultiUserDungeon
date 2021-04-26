package ac.abdn.cs3524.mud.server;

import ac.abdn.cs3524.mud.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
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
    public PlayerInterface joinGame(UUID gameID, String playerName) throws RemoteException{
        try{
            GameInterface game = games.get(gameID);
            PlayerInterface player = game.joinGame(playerName);
            players.add(player);
            LOGGER.info("Player '{}' joined game '{}'", player.getName(), gameID);
            return (PlayerInterface) UnicastRemoteObject.exportObject(player, Integer.parseInt(CONFIG.getProperty("server.port").orElseThrow()));
        }
        catch (Exception e){
            LOGGER.error("Could not add player '{}' to game '{}': {}", playerName, gameID, e.getMessage());
            throw new RemoteException("Could not join game");
        }
    }

    @Override
    public void leaveGame(GameInterface game, PlayerInterface player) throws RemoteException {
        try {
            game.removePlayer(player);
            player.setLocation("No game");
            LOGGER.info("Player '{}' left game '{}'", player.getName(), game.getID());
        }
        catch (Exception e){
            LOGGER.error("Could not add remove '{}' from game '{}': {}", player.getName(), game.getID(), e.getMessage());
            throw new RemoteException("Could leave game: " + e.getMessage());
        }

    }

    @Override
    public GameInterface newGame(String mapID) throws RemoteException {
        try {
            GameInterface game = Game.fromID(mapID);
            this.games.put(game.getID(),game);
            LOGGER.info("New game created: gameID= {} mapID = {} ", game.getID(), mapID);
            return (GameInterface) UnicastRemoteObject.exportObject(game, Integer.parseInt(CONFIG.getProperty("server.port").orElseThrow()));
        }
        catch (Exception e) {
            LOGGER.error("Server could not create new game using mapID '{}' : {}", mapID, e.getMessage());
            throw new RemoteException("Server could not create new game");
        }
    }

    @Override
    public GameInterface newGame(String name, String edges, String messages, String things) throws RemoteException {
        LOGGER.error("Not implemented");
        throw new RemoteException("Server could not create new game: not implemented");
    }

    @Override
    public GameInterface getGame(UUID gameID) throws RemoteException {
        return games.get(gameID);
    }

    @Override
    public List<UUID> listGames() throws RemoteException{
        return new ArrayList<>(games.keySet());
    }

    @Override
    public List<String> listMaps() throws RemoteException {
        String mapDir = CONFIG.getProperty("game.content-directory").orElseThrow() + "/maps/";
        List<String> maps = new ArrayList<>();
        for (var map : new File(mapDir).listFiles(File::isDirectory)){
            maps.add(map.getName());
        }
        return maps;
    }

    @Override
    public String ping() throws RemoteException {
        return "pong";
    }
}
