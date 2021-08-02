import bagel.*;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import bagel.util.Colour;

/**
 * The player enters a tomb to search for a treasure box where is filled with zombies and sandwiches.
 * If the player meets a sandwich, energy increases and sandwich disappears.
 * If the player is within shooting range, shoots the bullet and energy decreases.
 * If the bullet meets a zombie, both bullet and zombie disappears.
 * If the player has enough energy, he will move towards zombie, otherwise, move towards sandwich.
 * If all zombies are dead or no enough energy to fight with zombie when there is no sandwich, exits the game.
 */
public class ShadowTreasure extends AbstractGame {

    // constant variable for background, font information, shooting range, death range, step size
    private final Image BACKGROUND = new Image("res/images/background.png");
    private final Font FONT = new Font("res/font/DejaVusAns-Bold.ttf", 20);
    public static final int ENERGY_COMPARE = 3;
    public static final int MEET_DISTANCE = 50;
    public static final int SHOOTING_RANGE = 150;
    public static final int DEATH = 25;
    public static final int PLAYER_STEPSIZE = 10;
    public static final int BULLET_STEPSIZE = 25;

    // ArrayList for zombie, sandwich, outputString
    private static ArrayList<Zombie> zombies = new ArrayList<>();
    private static ArrayList<Sandwich> sandwiches = new ArrayList<>();
    private static ArrayList<String> outputStrings = new ArrayList<>();

    // instance variable for player, treasure, bullet, frameCount, output strings
    private Player player;
    private Treasure treasure;
    private Bullet bullet;
    private int frameCount;

    // for rounding double number; use this to print the location of the player
    private static DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Constructor of ShadowTreasure
     */
    public ShadowTreasure() throws IOException {
        // super(900, 600, "Treasure Hunt");
        this.loadEnvironment("res/IO/environment.csv");

        // initialize background and frameCount
        BACKGROUND.drawFromTopLeft(0, 0);
        this.frameCount = 0;
    }

    /**
     * Load from input file
     * @param filename: name of the csv file
     */
    private void loadEnvironment(String filename){
        String line;
        // read environment csv file
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while ((line = reader.readLine()) != null) {

                // replace invalid characters with empty string and store entity name with coordinates
                String[] character = line.split(",");
                String type = character[0].replaceAll("[^a-zA-Z0-9]", "");
                double x = Double.parseDouble(character[1]);
                double y = Double.parseDouble(character[2]);

                // add Static Entity zombie to zombies arraylist if entity name is "Zombie"
                if (type.equals(Zombie.TYPE)) {
                    zombies.add(new Zombie(x, y));
                }

                // add Static Entity sandwich to sandwiches arraylist if entity name is "Sandwich"
                else if (type.equals(Sandwich.TYPE)) {
                    sandwiches.add(new Sandwich(x, y));
                }

                // create treasure entity if entity name is "Treasure"
                else if (type.equals(Treasure.TYPE)) {
                    treasure = new Treasure(x, y);
                }

                // create player entity if entity name is "Player"
                else if (type.equals(Player.TYPE)) {
                    player = new Player(x, y, Integer.parseInt(character[3]));
                    bullet = new Bullet(x, y);
                    bullet.setAppear(false);
                }
            }
        }

        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * write output to the output csv file
     * @param outputStrings: string contains the output information
     */
    private void writeInfo(ArrayList<String> outputStrings) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("res/IO/output.csv"))) {
            for (String outputString: outputStrings) {
                pw.println(outputString);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Performs a state update.
     */
    @Override
    public void update(Input input) {
        final DrawOptions drawOptions = new DrawOptions();

        // draw background, zombies, sandwiches, player, treasure, bullet, and  energy string
        BACKGROUND.drawFromTopLeft(0, 0);
        for (Zombie zombie: zombies) {
            zombie.render();
        }
        for (Sandwich sandwich : sandwiches) {
            sandwich.render();
        }
        player.render();
        treasure.render();
        bullet.render();
        FONT.drawString("energy: " + player.getEnergy(), 20, 760,
                drawOptions.setBlendColour(Colour.BLACK));

        // simulation state change only every 10 frames
        if ((frameCount % 10)  == 0) {

            // find the closest sandwich and zombie by sorting the distance to player
            Sandwich.sortSandwich(sandwiches, player);
            Zombie.sortZombie(zombies, player);

            // terminate the game
            if ((player.meetWith(treasure, MEET_DISTANCE)) ||
                    (player.getEnergy() < ENERGY_COMPARE && sandwiches.size() == 0 &&
                            Zombie.getZombieCount() > 0 && !bullet.getAppear())) {

                // print energy level to console
                System.out.print(player.getEnergy());

                // treasure is met/reached by the player then print ",success!"
                if (player.meetWith(treasure, MEET_DISTANCE)) {
                    System.out.println(",success!");
                }

                // write csv file and terminate game
                writeInfo(outputStrings);
                System.exit(0);
            }

            else {
                // player meets a sandwich
                if ((sandwiches.size() > 0) && (player.meetWith(sandwiches.get(0), MEET_DISTANCE))) {

                    // player energy increase 5 and sandwich disappears
                    player.setEnergy(player.getEnergy() + 5);
                    sandwiches.remove(sandwiches.get(0));
                }

                // player shoot the bullet when within the shooting range if energy is greater than or equal to 3
                else if ((zombies.size() > 0) && (player.meetWith(zombies.get(0), SHOOTING_RANGE)) &&
                        (player.getEnergy() >= ENERGY_COMPARE) && (!bullet.getAppear())) {

                    // set bullet to appear at player's coordinates
                    bullet.setAppear(true);
                    bullet = new Bullet(player.getX(), player.getY());

                    // player's energy reduces by 3 and the valid number of zombies decreases by 1
                    player.setEnergy(player.getEnergy() - ENERGY_COMPARE);
                    Zombie.setZombieCount(Zombie.getZombieCount() - 1);
                }

                // bullet movement if bullet is set to appear
                if (bullet.getAppear()) {
                    bullet.setDirectionTo(zombies.get(0), BULLET_STEPSIZE);
                    outputStrings.add(df.format(bullet.getX()) + "," + df.format(bullet.getY()));
                }

                // if all zombies are killed then treasure is reachable
                if (zombies.size() == 0) {
                    player.setDirectionTo(treasure, PLAYER_STEPSIZE);
                }

                // player's energy is greater or equal to 3, then moves towards nearest zombie
                else if (player.getEnergy() >= ENERGY_COMPARE) {
                    player.setDirectionTo(zombies.get(0), PLAYER_STEPSIZE);
                }

                else {

                    // if exists sandwich, then player moves towards sandwich
                    if (sandwiches.size() > 0) {
                        player.setDirectionTo(sandwiches.get(0), PLAYER_STEPSIZE);
                    }

                    // if no sandwich exists, them player moves towards zombie
                    if (sandwiches.size() == 0) {
                        player.setDirectionTo(zombies.get(0), PLAYER_STEPSIZE);
                    }
                }

                // remove dead zombie if within the death distance and bullet disappear
                if ((zombies.size() > 0) && bullet.meetWith(zombies.get(0), DEATH)) {
                    zombies.remove(zombies.get(0));
                    bullet.setAppear(false);
                }
            }
        }
        frameCount += 1;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasure game = new ShadowTreasure();
        game.run();
    }
}
