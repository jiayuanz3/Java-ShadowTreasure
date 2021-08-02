/**
 * StaticEntity is a sub class of Entity and parent class of Sandwich and Zombie.
 * StaticEntity implements Comparable interface in order to sort by distancePlayer.
 */
public abstract class StaticEntity extends Entity implements Comparable<StaticEntity> {

    // distance to player
    private double distancePlayer;

    /**
     * Constructor of Static Entity
     * @param image: a string represents where the image stored
     * @param type: a string represents which entity it belongs to
     * @param x: x coordinate of the entity
     * @param y: y coordinate of entity
     */
    public StaticEntity(String image, String type, double x, double y) {
        super(image, type, x, y);
    }

    /**
     * Setter for distancePlayer
     * @param movingentity: distance to which entity
     */
    protected void setDistancePlayer(MovingEntity movingentity) {
        this.distancePlayer = this.calDistance(movingentity);
    }

    /**
     * Getter for DistancePlayer
     * @return distancePlayer: distance to the player
     */
    private double getDistancePlayer() {
        return distancePlayer;
    }

    /**
     * Override CompareTo method to sort static entities by distancePlayer of ascending order
     */
    @Override
    public int compareTo(StaticEntity staticentity) {
        return Double.compare(this.getDistancePlayer(), staticentity.getDistancePlayer());
    }
}
