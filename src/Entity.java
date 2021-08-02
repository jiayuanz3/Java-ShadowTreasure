import bagel.Image;
import bagel.util.Point;

/**
 * Entity is a abstract parent class for StaticEntity, MovingEntity, Treasure.
 * Entity stores x, y coordinates, type for different characters.
 * Contains methods to render and calculate distance between two entities.
 */
public abstract class Entity {

    // constant variable for entity type and image
    public final String TYPE;
    private final Image IMAGE;
    // instance variable for x, y coordinates
    private double x;
    private double y;
    // instance variable to represent status (disappear if not there)
    private boolean isAppear = true;

    /**
     * Constructor of Entity
     * @param filename: a string represents where the image stored
     * @param type: a string represents which entity it belongs to
     * @param x: x coordinate of the moving entity
     * @param y: y coordinate of the moving entity
     */
    public Entity(String filename, String type, double x, double y) {
        this.IMAGE = new Image(filename);
        this.TYPE = type;
        this.x = x;
        this.y = y;
    }

    /**
     * Getter and Setter for x and y coordinates and for isAppear
     */
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean getAppear() {
        return this.isAppear;
    }

    public void setAppear(boolean appear) {
        this.isAppear = appear;
    }

    /**
     * Render function for updating position
     */
    public void render() {
        if (isAppear) {
            IMAGE.draw(x, y);
        }
    }

    /**
     * Take an entity to calculate distance between another entity
     * @param entity: the distance to which entity
     * @return distance between two entities
     */
    protected double calDistance(Entity entity) {
        Point otherPoint = new Point(entity.getX(), entity.getY());
        return new Point(this.getX(), this.getY()).distanceTo(otherPoint);
    }
}



