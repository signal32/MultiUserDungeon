package ac.abdn.cs3524.mud.common;

public interface PlayerManager {
    boolean move(PlayerInterface player, String direction) throws IllegalArgumentException;
    boolean pickup(PlayerInterface player, String objectName) throws IllegalArgumentException;
    boolean drop (PlayerInterface player, String objectName) throws IllegalArgumentException;
    String getLocationInfo(String location) throws IllegalArgumentException;
}
