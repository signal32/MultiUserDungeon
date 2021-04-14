package ac.abdn.cs3524.mud.common;

import java.util.List;

/**
 * A MUD game with one world and multiple unique players.
 */
public class Game {

    private final MUD _world;
    private final List<Player> _players;

    public Game(MUD world, List<Player> players) {
        this._world = world;
        this._players = players;
    }
}
