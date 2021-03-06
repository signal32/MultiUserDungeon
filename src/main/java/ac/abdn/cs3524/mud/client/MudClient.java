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
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MudClient implements ClientInterface{

    private static final Logger LOGGER = LoggerFactory.getLogger(MudClient.class);

    private PlayerInterface player;
    private GameInterface game;
    private MudServerInterface server;
    private boolean run = true;
    private final UUID id = UUID.randomUUID();

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

            //--------
            System.setProperty( "java.security.policy", "content/rmi.policy" ) ;
            System.setSecurityManager(new SecurityManager());

            int clientPort = Integer.parseInt(clientConfig.getProperty("client.port").orElse("8082"));
            boolean isClientInit = false;
            ClientInterface clientInterface = null;
            do
            {
                try {
                    clientInterface =  (ClientInterface) UnicastRemoteObject.exportObject(app,clientPort);
                    isClientInit = true;
                }
                catch (Exception e) {
                    System.out.println("Cannot init client at port " + clientPort);
                    clientPort++;
                }
            }
            while(!isClientInit);

            app.server.registerClient(clientInterface);

            if (clientPort == Integer.parseInt(clientConfig.getProperty("client.port").orElse("8082"))) {
                System.out.println("Client initiated at port " + clientPort);
            }
            else {
                System.out.println("Client initiated at port " + clientPort + " instead");
            }

            // Run the game
            while (app.run) {
                app.menu();
                if (app.run) {app.game();}
            }

            app.server.deregisterClient(clientInterface.id());
            System.exit(0);

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

        while (true) {
            System.out.println("Choose one of the options (by inputting its number then pressing ENTER):");
            System.out.println("1. Create a new game");
            System.out.println("2. Join existing game");
            System.out.println("3. View existing games");
            System.out.println("4. Exit the server");
            Scanner in1 = new Scanner(System.in);
            String menuInputOne;
            menuInputOne = in1.next();

            if (menuInputOne.equalsIgnoreCase("1")) {
                while (true) {
                    System.out.println("Select Game Map by inputting its name or press ENTER to go back:");
                    for (int i = 0; i < server.listMaps().size(); i++) {
                        System.out.println((i + 1) + ". " + server.listMaps().get(i));
                    }
                    Scanner in0 = new Scanner(System.in);
                    String menuInputZero;
                    menuInputZero = in0.nextLine();
                    if (menuInputZero.isEmpty()) {
                        break;
                    } else if (server.listMaps().contains(menuInputZero)){
                        System.out.println("Creating New Game, playing on: " + menuInputZero);
                        game = server.newGame(menuInputZero);
                        player = server.joinGame(game.getID(), playerName, this.id.toString());
                        System.out.println("Your GameID is: " + game.getID());
                        return;
                    } else {
                        System.out.println("Invalid input, please try again");
                    }
                }
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
                                player = server.joinGame(UUID.fromString(menuInputTwo), playerName, this.id.toString());
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
        while (run && play) {

            // Get input and update
            player.getLocationInfo();
            System.out.println("Type command:");
            String input = scanner.next();
            if (input.equals("north") || input.equals("n")) {
                player.move("north");
                System.out.println(player.getLocationInfo());
            }
            else if (input.equals("south") || input.equals("s")) {
                player.move("south");
                System.out.println(player.getLocationInfo());
            }
            else if (input.equals("east") || input.equals("e")) {
                player.move("east");
                System.out.println(player.getLocationInfo());
            }
            else if (input.equals("west") || input.equals("w")) {
                player.move("west");
                System.out.println(player.getLocationInfo());
            }
            else if (input.equals("exit")) {
                play = false;
            }
            else if (input.contains("pick") || input.equals("p")) {
                String item = scanner.next();
                if(player.pickUp(item) == true)
                {
                    System.out.println("Inventory: " + player.getInventory().toString());
                    System.out.println(player.getLocationInfo());
                }
                else {
                    System.out.println("Cannot pick " + item);
                }

            }
            else if (input.contains("drop") || input.equals("d")) {
                player.drop(scanner.next());
                System.out.println("Inventory: " + player.getInventory().toString());
                System.out.println(player.getLocationInfo());
            }
            else if (input.contains("location") || input.equals("loc")) {
                System.out.println(player.getLocationInfo());
            }
            else if (input.equals("help") || input.equals("h")) {
                displayHelp();
            }
            else if (input.equals("inventory") || input.equals("i")){
                System.out.println("Inventory: " + player.getInventory().toString());
            }
            else if (input.equals("list") || input.equals("l")){
                List<PlayerInterface> players = game.getList();
                if (players.size() == 1) {
                    System.out.println("You are the only player in this game.");
                }
                else {
                    System.out.println("There are " + (players.size()) + " players in the game.");
                }
                for (int i = 0; i < players.size(); i++) {
                    if(i == 0 && players.size() == 1)
                    {
                        System.out.print("[ " + players.get(i).getName() + " ]");
                    }
                    else if (i == 0){
                        System.out.print("[ " + players.get(i).getName());
                    }
                    else if (i == (players.size() - 1)){
                        System.out.println(", " + players.get(i).getName() + " ]");
                    }
                    else {
                        System.out.print(", " + players.get(i).getName());
                    }
                }
                System.out.println();
            }
            else if (input.equals("message") || input.equals("m")){
                String receiver = scanner.next();
                String message = null;
                if (receiver.equals("all")) {
                    message = scanner.nextLine();
                    System.out.println("Message to All:" + message);

                    if(game.sendMessage(player.getName(), receiver, message) == true) {
                        System.out.println("Message sent successfully");
                    }
                    else
                    {
                        System.out.println("Sending message FAILED: Receiver not found");
                    }
                }
                else {
                    message = scanner.nextLine();
                    System.out.printf("Message to %s: %s\n", receiver, message);

                    if(game.sendMessage(player.getName(), receiver, message) == true) {
                        System.out.println("Message sent successfully");
                    }
                    else
                    {
                        System.out.println("Sending message FAILED: Receiver not found");
                    }
                }
            }
        }

        exit();
    }

    private void exit() throws RemoteException {
        server.leaveGame(game,player);
    }

    private static void displayHelp() {
        System.out.println();
        System.out.println("You can choose from one of these commands:");
        System.out.println();
        System.out.println("<direction> - move in the selected direction (north, east, south, west)");
        System.out.println("n/e/s/w - (shorthand) move in the selected direction (north, east, south, west)");
        System.out.println("pick <item> - pick up the item to your inventory");
        System.out.println("p <item> - (shorthand) pick up the item to your inventory");
        System.out.println("drop <item> - drop an item from your inventory");
        System.out.println("d <item> - (shorthand) drop an item from your inventory");
        System.out.println("inventory - see the items you are carrying");
        System.out.println("i - (shorthand) see the items you are carrying");
        System.out.println("location - show your current surroundings");
        System.out.println("loc - (shorthand) show your current surroundings");
        System.out.println("help - display the available commands");
        System.out.println("h - (shorthand) display the available commands");
        System.out.println("list - display a list of all online players");
        System.out.println("l - (shorthand) display a list of all online players");
        System.out.println("message <receiver's name> <message content> - send a message to a player in the current game");
        System.out.println("m <receiver's name> <message content> - (shorthand) send a message to a player in the current game");
        System.out.println("message all <message content> - send a message to all players in the current game");
        System.out.println("m all <message content> - (shorthand) send a message to all players in the current game");
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

    @Override
    public boolean ping() throws RemoteException {
        return true;
    }

    @Override
    public String id() throws RemoteException {
        return this.id.toString();
    }

    @Override
    public void refresh() throws RemoteException {
        System.out.println(this.player.getLocationInfo());
    }

    @Override
    public void receiveMessage(String senderName, String message) throws RemoteException {
        System.out.printf("Message received from %s: %s\n", senderName, message);
    }
}
