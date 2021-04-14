package ac.abdn.cs3524.mud.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class MudMainline {

    private static final Logger LOGGER = LoggerFactory.getLogger(MudMainline.class);

    public static void main(String[] args) {

        try {
            String hostname = (InetAddress.getLocalHost()).getCanonicalHostName();
            int registryPort = Integer.parseInt(args[0]);
            int serverPort = Integer.parseInt(args[1]);

            MudServerInterface server = new MudServer();
            MudServerInterface serverStub = (MudServerInterface) UnicastRemoteObject.exportObject(server, serverPort);

            String serverURL = "rmi://" + hostname + ":" + registryPort + "/MUDServer";
            Naming.rebind(serverURL, serverStub);

            LOGGER.info("Server started on: {}", serverURL);

        }
        catch (Exception e){
            LOGGER.warn("Initialisation failed: {}", e.getMessage());
        }


    }
}
