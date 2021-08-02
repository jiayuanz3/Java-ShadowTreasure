/**
 * Bullet is a subclass of Moving Entity.
 */
public class Bullet extends MovingEntity {

    // constant variable for entity type
    public static final String TYPE = "Bullet";

    /**
     * Constructor of Bullet
     * @param x: x coordinator of bullet
     * @param y: y coordinator of bullet
     */
    public Bullet(double x, double y) {
        super("res/images/shot.png", TYPE, x, y);
    }

}
