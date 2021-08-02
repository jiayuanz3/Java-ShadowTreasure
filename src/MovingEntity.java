/**
 * Child class of Entity and parent class of Bullet and Player.
 * Have method to set direction of the moving entity towards the target static entity.
 * Have method to check if two entities can meet.
 */
public abstract class MovingEntity extends Entity {

    /**
     * Constructor of MovingEntity
     * @param image: a string represents where the image stored
     * @param type: a string represents which entity it belongs to
     * @param x: x coordinate of the moving entity
     * @param y: y coordinate of the moving entity
     */
    public MovingEntity(String image, String type, double x, double y) {
        super(image, type, x, y);
    }

    /**
     * Update coordinates according to moving entity's step size
     * @param entity: identity which entity will move
     * @param stepSize: step size relates to that entity
     */
    public void setDirectionTo(Entity entity, int stepSize){
        double distance;
        // Calculate distance between moving entity and the other static entity
        distance = calDistance(entity);

        // Update moving entity's coordinates with normalized distance and step size
        this.setX(this.getX() + ((entity.getX() - this.getX())/distance) * stepSize);
        this.setY(this.getY() + ((entity.getY() - this.getY())/distance) * stepSize);
    }

    /**
     * Check if player can meet with the other entity
     * @param entity: the entity to check with
     * @param standardDistance: meeting distance according to definition
     * @return a boolean value: true if two entities meet, false it they don't meet
     */
    public boolean meetWith(Entity entity, double standardDistance) {
        // Calculate distance and check meeting condition
        return calDistance(entity) < standardDistance;
    }
}
