package ac.abdn.cs3524.mud.client;

import ac.abdn.cs3524.mud.common.Config;
import ac.abdn.cs3524.mud.server.MudServerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MudClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MudClient.class);

    private static  final boolean RUN = true;

    public static void main(String[] args) {
        try{
            Config clientConfig = new Config("client.properties");    // Client config

            String serverHostName = clientConfig.getProperty("server.hostname").orElse("localhost");
            int serverPort = Integer.parseInt(clientConfig.getProperty("server.port").orElse("8080"));

            Registry registry = LocateRegistry.getRegistry(serverHostName,serverPort);

            MudServerInterface mudServerStub = (MudServerInterface) registry.lookup("MudServerInterface");

            LOGGER.info(mudServerStub.ping());

            // Main Game Loop
            while (RUN){
                // wait for input
                // update
                // display
            }
        }
        catch (Exception e){
            LOGGER.error("Fatal error: {}", e.getMessage());
        }
    }
}
