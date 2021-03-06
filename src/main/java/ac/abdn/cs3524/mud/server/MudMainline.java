package ac.abdn.cs3524.mud.server;

import ac.abdn.cs3524.mud.common.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

import java.lang.SecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class MudMainline {

    private static final Logger LOGGER = LoggerFactory.getLogger(MudMainline.class);

    public static void main(String[] args) {

        try {
            // Load configuration
            Config config = new Config("content/server.properties");
            int registryPort = Integer.parseInt(config.getProperty("registry.port").orElseThrow());
            int serverPort = Integer.parseInt(config.getProperty("server.port").orElseThrow());
            String hostname = config.getProperty("server.hostname").orElse((InetAddress.getLocalHost()).getHostAddress());

            System.setProperty( "java.security.policy", "content/rmi.policy" ) ;
            System.setSecurityManager( new SecurityManager() ) ;

            // Setup Mud Server and create stub
            MudServer server = new MudServer();
            MudServerInterface serverStub = (MudServerInterface) UnicastRemoteObject.exportObject(server, serverPort);
            LOGGER.info("Server initialised on {}:{}", hostname,serverPort);

            // Setup and export RMI registry on localhost
            Registry registry = LocateRegistry.createRegistry(registryPort);
            registry.bind("MudServerInterface",serverStub);
            LOGGER.info("Remote registry initialised on {}:{}", hostname,registryPort);

            LOGGER.info("Startup complete!");

            server.start();

        }
        catch (Exception e){
            LOGGER.warn("Initialisation failed: {}", e.getMessage());
        }


    }
}
