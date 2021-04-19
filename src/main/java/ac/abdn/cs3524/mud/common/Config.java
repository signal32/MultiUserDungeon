package ac.abdn.cs3524.mud.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Optional;
import java.util.Properties;

public class Config {

    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);

    private static Config config;

    private final Properties properties;
    private final File file;

    public Config(String path) throws IOException{

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();// read as input stream
        file = new File(classloader.getResource(path).getFile());
        properties = new Properties();

        try(FileReader reader = new FileReader(file)){
            properties.load(reader);
        }

        config = this;
    }

    public Optional<String> getProperty(String key){
        return Optional.ofNullable(properties.getProperty(key));
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        try (FileWriter writer = new FileWriter(file)) {
            properties.store(writer, "");
        } catch (Exception e) {
            LOGGER.warn("Properties file not updated: {}", e.getMessage());
        }
    }

    public static Config getConfig(){
        return Config.config;
    }
}
