import java.util.ArrayList;
import java.util.Collections;

/**
 * Zombie is a sub class of StaticEntity.
 * Have constructor, getter and setter.
 * Have method to sort zombie ArrayList.
 */
public class Zombie extends StaticEntity {

    // constant variable for entity type and static variable zombieCount
    public static final String TYPE = "Zombie";
    private static int zombieCount = 0;

    /**
     * Constructor of Zombie
     * Count static variable: zombieCount
     * @param x: x coordinate of zombie
     * @param y: y coordinate of zombie
     */
    public Zombie(double x, double y) {
        super("res/images/zombie.png", TYPE, x, y);
        zombieCount += 1;
    }

    /**
     * Getter for zombieCount
     * @return zombieCount: number of zombie
     */
    public static int getZombieCount() {
        return zombieCount;
    }
    /**
     * Setter for zombieCount
     * @param zombieCount: number of zombies
     */
    public static void setZombieCount(int zombieCount) {
        Zombie.zombieCount = zombieCount;
    }

    /**
     * Sort Zombie ArrayList according to CompareTo method
     * @param zombies: an arraylist of zombies
     * @param player: a moving entity player
     */
    public static void sortZombie(ArrayList<Zombie> zombies, Player player) {
        for (Zombie zombie: zombies) {
            zombie.setDistancePlayer(player);
        }
        Collections.sort(zombies);
    }
}
