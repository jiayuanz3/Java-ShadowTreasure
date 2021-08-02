/**
 * Treasure is a child class of Entity.
 */
public class Treasure extends Entity {

    // constant variable for entity type
    public static final String TYPE = "Treasure";

    /**
     * Constructor of Treasure
     * @param x: x coordinate of treasure
     * @param y: y coordinate of treasure
     */
    public Treasure(double x, double y) {
        super("res/images/treasure.png", TYPE, x, y);
    }

}
