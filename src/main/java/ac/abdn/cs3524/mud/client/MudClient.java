package ac.abdn.cs3524.mud.client;

import ac.abdn.cs3524.mud.common.Config;
import ac.abdn.cs3524.mud.common.GameInterface;
import ac.abdn.cs3524.mud.common.PlayerInterface;
import ac.abdn.cs3524.mud.server.MudServerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MudClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MudClient.class);

    public static void main(String[] args) {
        try{
            // Setup and get config
            Config clientConfig = new Config("client.properties");    // Client config
            String serverHostName = clientConfig.getProperty("server.hostname").orElse("localhost");
            int serverPort = Integer.parseInt(clientConfig.getProperty("server.port").orElse("8080"));

            // Connect to remote server
            Registry registry = LocateRegistry.getRegistry(serverHostName,serverPort);
            MudServerInterface server = (MudServerInterface) registry.lookup("MudServerInterface");

            // Start game
            GameInterface game = server.newGame("myMud");
            LOGGER.info("Player count: {}",game.playerCount());

            // Join Game
            PlayerInterface player = server.joinGame(game.getID(), "test player");
            LOGGER.info("Added '{}' to game '{}' at '{}'", player.getName(), game.getID(), player.getLocation());

            LOGGER.info("Player count: {}",game.playerCount());

            player.move("east");
            LOGGER.info("Location {}",player.getLocation());

            // Main Game Loop
            boolean run = true;
            while (run){
                // TODO wait for input
                // TODO update
                // TODO display
                run = false;
            }

            // TODO leave game
            // TODO close connections
        }
        catch (Exception e){
            LOGGER.error("Fatal error: {}", e.getMessage());
        }
    }
}
