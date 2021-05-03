package ac.abdn.cs3524.mud.common;

/**
 * Abstract player manager which manipulates a game world.
 * Designed to abstract implementation of the game world from it's users.
 */
public interface PlayerManager {

    /**
     * Move a player one unit in a direction
     * @param player the player
     * @param direction the direction
     * @return true if the player was moved, false otherwise
     * @throws IllegalArgumentException if the direction was invalid
     */
    boolean move(PlayerInterface player, String direction) throws IllegalArgumentException;

    /**
     * Pickup an object at the players location
     * (does not imply that the object is stored - i.e simply removes it from game world)
     */
    boolean pickup(PlayerInterface player, String objectName) throws IllegalArgumentException;

    /**
     * Drop an object at the players location
     */
    boolean drop (PlayerInterface player, String objectName) throws IllegalArgumentException;

    /**
     * Get a descriptor of the given location within the game world
     */
    String getLocationInfo(String location) throws IllegalArgumentException;
}
