package ac.abdn.cs3524.mud.common;

public interface PlayerInterface {

    Vertex getLocation();
    boolean move(Edge edge);

    boolean pickUp(String item);
    boolean drop(String item);
}
