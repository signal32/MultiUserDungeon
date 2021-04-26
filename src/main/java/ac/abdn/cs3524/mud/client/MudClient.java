package ac.abdn.cs3524.mud.client;

import ac.abdn.cs3524.mud.common.Config;
import ac.abdn.cs3524.mud.common.GameInterface;
import ac.abdn.cs3524.mud.common.PlayerInterface;
import ac.abdn.cs3524.mud.server.MudServerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.UUID;

public class MudClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MudClient.class);

    private PlayerInterface player;
    private GameInterface game;
    private MudServerInterface server;
    private boolean run = true;

    public static void main(String[] args) {
        try {
            // Setup and get config
            Config clientConfig = new Config("content/client.properties");    // Client config
            String serverHostName = clientConfig.getProperty("server.hostname").orElse("localhost");
            int serverPort = Integer.parseInt(clientConfig.getProperty("server.port").orElse("8080"));

            MudClient app = new MudClient();

            // Connect to remote server
            Registry registry = LocateRegistry.getRegistry(serverHostName, serverPort);
            app.server = (MudServerInterface) registry.lookup("MudServerInterface");

            // Run the game
            while (app.run) {
                app.menu();
                app.game();
            }

            // Close down game
            app.exit();

        } catch(Exception e){
                LOGGER.error("Fatal error: {}", e.getMessage());
        }
    }


    private void menu() throws RemoteException {
        String playerName;
        if (player == null) {
            System.out.println("Enter a name and press ENTER to continue: ");
            Scanner scanner = new Scanner(System.in);
            playerName = scanner.next();
        }
        else
            playerName = player.getName();

        boolean menu = true;
        while (menu) {
            System.out.println("Choose one of the options (by inputting its number then pressing ENTER):");
            System.out.println("1. Create a new game");
            System.out.println("2. Join existing game");
            System.out.println("3. View existing games");
            System.out.println("4. Exit the server");
            Scanner in1 = new Scanner(System.in);
            String menuInputOne;
            menuInputOne = in1.next();

            if (menuInputOne.equalsIgnoreCase("1")) {
                System.out.println("Creating New Game");
                game = server.newGame("myMud");
                player = server.joinGame(game.getID(), playerName);
                return;

            } else if (menuInputOne.equalsIgnoreCase("2")) {
                while (true) {
                    System.out.println("Input game's ID you want to join or press ENTER to go back");
                    Scanner in2 = new Scanner(System.in);
                    String menuInputTwo;
                    menuInputTwo = in2.nextLine();
                    if (menuInputTwo.isEmpty()) {
                        break;
                    } else {
                        if (isUUID(menuInputTwo)) {
                            if (server.listGames().contains(UUID.fromString(menuInputTwo))) {
                                System.out.println("Joining: " + menuInputTwo);
                                game = server.getGame(UUID.fromString(menuInputTwo));
                                player = server.joinGame(UUID.fromString(menuInputTwo), playerName);
                                return;
                            }
                        } else {
                            System.out.println("Invalid input, please try again");
                        }
                    }
                }
            } else if (menuInputOne.equalsIgnoreCase("3")) {
                if (server.listGames().isEmpty()) {
                    System.out.println("Currently, there are no available games");
                } else {
                    System.out.println("List of available games:");
                    for (int i = 0; i < server.listGames().size(); i++) {
                        System.out.println((i + 1) + ". " + server.listGames().get(i).toString());
                    }
                }
            } else if (menuInputOne.equalsIgnoreCase("4")) {
                System.out.println("Are you sure about this? (y/n)");
                Scanner in3 = new Scanner(System.in);
                String menuInputThree;
                menuInputThree = in3.next();
                if (menuInputThree.equalsIgnoreCase("y")) {run = false; return;}
            } else {
                System.out.println("Invalid input, please try again");
            }
        }
    }

    private void game() throws RemoteException{
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello " + player.getName() +", You have entered the game at: " + player.getLocation() + player.getLocationInfo());
        boolean play = true;
        while (play && run) {

            // Get input and update
            player.getLocationInfo();
            System.out.println("Type command:");
            String input =scanner.next();
            if (input.equals("north") || input.equals("n")) {
                player.move("north");
            }
            else if (input.equals("south") || input.equals("s")) {
                player.move("south");
            }
            else if (input.equals("east") || input.equals("e")) {
                player.move("east");
            }
            else if (input.equals("west") || input.equals("w")) {
                player.move("west");
            }
            else if (input.equals("exit")) {
                play = false;
            }
            else if (input.contains("pick")) {
                player.pickUp(scanner.next());
                System.out.println("Inventory: " + player.getInventory().toString());
            }
            else if (input.contains("drop")) {
                player.drop(scanner.next());
                System.out.println("Inventory: " + player.getInventory().toString());
            }
            else if (input.equals("help")) {
                displayHelp();
            }
            else if (input.equals("inventory") || input.equals("i")){
                System.out.println(player.getInventory().toString());
            }

            System.out.println(player.getLocationInfo());
        }

        // Remove player from the game
        exit();
    }

    private void exit() throws RemoteException {
        server.leaveGame(game,player);
    }

    private static void displayHelp() {
        System.out.println();
        System.out.println("You can choose from one of these commands:");
        System.out.println();
        System.out.println("(move) <direction> - move in the selected direction (north, east, south, west)");
        System.out.println("pick <item> - pick up the item to your inventory");
        System.out.println("drop <item> - drop an item from your inventory");
        System.out.println("inventory - see the items you are carrying");
        System.out.println("location - show your current surroundings");
        System.out.println("help - display the available commands");
        System.out.println("menu - display all options regarding MUDS");
        System.out.println("exit - exit the game");
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
