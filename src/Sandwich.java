import java.util.ArrayList;
import java.util.Collections;

/**
 * Sandwich is sub class of StaticEntity.
 * Have method to sort sandwich ArrayList.
 */
public class Sandwich extends StaticEntity {

    // constant variable for entity type
    public static final String TYPE = "Sandwich";

    /**
     * Constructor of Sandwich
     * @param x: x coordinate of sandwich
     * @param y: y coordinate of sandwich
     */
    public Sandwich(double x, double y) {
        super("res/images/sandwich.png", TYPE, x, y);
    }

    /**
     * Sort Sandwich ArrayList according to CompareTo method
     * @param sandwiches: an arraylist of sandwiches
     * @param player: a moving entity player
     */
    public static void sortSandwich(ArrayList<Sandwich> sandwiches, Player player) {
        for (Sandwich sandwich: sandwiches) {
            sandwich.setDistancePlayer(player);
        }
        Collections.sort(sandwiches);
    }
}
