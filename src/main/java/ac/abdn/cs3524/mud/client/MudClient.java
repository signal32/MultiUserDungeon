package ac.abdn.cs3524.mud.client;

import ac.abdn.cs3524.mud.common.Config;
import ac.abdn.cs3524.mud.common.GameInterface;
import ac.abdn.cs3524.mud.common.PlayerInterface;
import ac.abdn.cs3524.mud.server.MudServerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.UUID;

public class MudClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MudClient.class);

    public static void main(String[] args) {
        try{
            // Setup and get config
            Config clientConfig = new Config("content/client.properties");    // Client config
            String serverHostName = clientConfig.getProperty("server.hostname").orElse("localhost");
            int serverPort = Integer.parseInt(clientConfig.getProperty("server.port").orElse("8080"));
            
            // Connect to remote server
            Registry registry = LocateRegistry.getRegistry(serverHostName,serverPort);
            MudServerInterface server = (MudServerInterface) registry.lookup("MudServerInterface");

            // Allocate a player
            PlayerInterface player = null;
            
            // Main Menu for the Client
            MainMenu: {
                while (true) {
                    System.out.println("Choose one of the options (by inputting its number then pressing ENTER):");
                    System.out.println("1. Create a new game");
                    System.out.println("2. Join existing game");
                    System.out.println("3. View existing games");
                    Scanner in1 = new Scanner(System.in);
                    String menuInputOne;
                    menuInputOne = in1.next();

                    if (menuInputOne.equalsIgnoreCase("1")) {
                        System.out.println("Creating New Game");
                        GameInterface game = server.newGame("myMud");
                        LOGGER.info("Player count: {}",game.playerCount());
                        player = server.joinGame(game.getID(), "test player");
                        break;
                    } else if (menuInputOne.equalsIgnoreCase("2")) {
                        while (true) {
                            System.out.println("Input game's IP you want to join or press ENTER to go back");
                            Scanner in2 = new Scanner(System.in);
                            String menuInputTwo;
                            menuInputTwo = in2.nextLine();
                            if (menuInputTwo.isEmpty()) {
                                break;
                            } else {
                                if (isUUID(menuInputTwo)) {
                                    if (server.listGames().contains(UUID.fromString(menuInputTwo))){
                                    System.out.println("Joining: " + menuInputTwo);
                                    GameInterface game = server.getGame(UUID.fromString(menuInputTwo));
                                    LOGGER.info("Player count: {}",game.playerCount());
                                    player = server.joinGame(UUID.fromString(menuInputTwo), "test player");
                                    break MainMenu;
                                    }
                                } else {
                                    System.out.println("Invalid input, please try again");
                                }
                            }
                        }
                    } else if (menuInputOne.equalsIgnoreCase("3")) {
                        if (server.listGames().size() == 0) {
                            System.out.println("Currently, there are no available games");
                        } else {
                            System.out.println("List of available games:");
                            for (int i = 0; i < server.listGames().size(); i++) {
                                System.out.println((i + 1) + ". " + server.listGames().get(i).toString());
                            }
                        }
                    } else {
                        System.out.println("Invalid input, please try again");
                    }
                }
            }

            if (player == null) {
                LOGGER.error("Player is null");
                System.exit(-1);
            }

            // Start game
            //GameInterface game = server.newGame("myMud");
            //LOGGER.info("Player count: {}",game.playerCount());

            // Join Game
            //PlayerInterface player = server.joinGame(game.getID(), "test player");
           /*LOGGER.info("Added '{}' to game '{}' at '{}'", player.getName(), game.getID(), player.getLocation());

            LOGGER.info("Player count: {}",game.playerCount());*/

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
    
    static boolean isUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
