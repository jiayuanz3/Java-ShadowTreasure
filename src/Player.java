/**
 * Player is a subclass of MovingEntity.
 */
public class Player extends MovingEntity {

    // constant variable for entity type and step size
    public static final String TYPE = "Player";
    // instance variable for energy level
    private int energy;

    /**
     * Constructor of Player
     * @param x: x coordinator of player
     * @param y: y coordinator of player
     * @param energy: energy level of the player
     */
    public Player(double x, double y, int energy) {
        super("res/images/player.png", TYPE, x, y);
        this.energy = energy;
    }

    /**
     * Getter and Setter for energy
     */
    public int getEnergy() {
        return this.energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

}

