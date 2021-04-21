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

public class MudClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MudClient.class);

    public static void main(String[] args) {
        try{
            // Setup and get config
            Config clientConfig = new Config("content/client.properties");    // Client config
            String serverHostName = clientConfig.getProperty("server.hostname").orElse("localhost");
            int serverPort = Integer.parseInt(clientConfig.getProperty("server.port").orElse("8080"));
            
            // Main Menu for the Client
            MainMenu: {
                while (true) {
                    System.out.println("Choose one of the options (by inputting its number then pressing ENTER):");
                    System.out.println("1. Create a new game");
                    System.out.println("2. Join existing game");
                    System.out.println("3. View existing games");

                    Scanner in1 = new Scanner(System.in);
                    //in.useDelimiter("\n"); // not sure if needed
                    String menuInputOne;
                    menuInputOne = in1.next();

                    if (menuInputOne.equalsIgnoreCase("1")) {
                        System.out.println("Creating New Game");
                        // WIP
                        // Will use the code in the following lines
                        // Now just breaks and continues
                        // Technical functional
                        break;
                    } else if (menuInputOne.equalsIgnoreCase("2")) {
                        while (true) {

                            System.out.println("Input game's IP you want to join or press ENTER to go back");
                            Scanner in2 = new Scanner(System.in);
                            //in2.useDelimiter("\n"); // not sure needed
                            String menuInputTwo;
                            menuInputTwo = in2.nextLine();

                            if (menuInputTwo.isEmpty()) {
                                break;
                            } else if (menuInputTwo.equalsIgnoreCase("1111")) { // "1111" being example ip
                                System.out.println("Joining: " + menuInputTwo);
                                // WIP
                                // Will join a game from available IPs
                                // Now just breaks MainMenu and continues
                                // Not functional
                                break MainMenu;
                            } else {
                                System.out.println("Invalid input, please try again");
                            }
                        }
                    } else if (menuInputOne.equalsIgnoreCase("3")) {
                        System.out.println("List of available games:");
                        // WIP
                        // Will print out a list of available games by IP
                        // Now does nothing
                        // Not functional
                    } else {
                        System.out.println("Invalid input, please try again");
                    }
                }
            }

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
