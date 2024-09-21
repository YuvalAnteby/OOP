package game;

import geometry.Line;
import geometry.Point;
import physics.CollisionInfo;
import physics.Collidable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to calculate collisions of the collidable objects and hold constant variables related to the game.
 * @author Yuval Anteby
 */
public class GameEnvironment {
    //Screen size constant variables.
    private static final int WIDTH = 800, HEIGHT = 600;
    //Boundaries size constant variables.
    private static final int BOUNDS_WIDTH = 10, BOUNDS_HEIGHT = 10;
    //Blocks size constant variables.
    private static final int BLOCK_WIDTH = 50, BLOCK_HEIGHT = 20, NUM_OF_ROWS = 6;
    //Paddle size constant variables
    private static final int PADDLE_WIDTH = 80, PADDLE_HEIGHT = 7;

    private List<Collidable> collidables;

    /**
     * Constructs a {@code GameEnvironment} with the specified list of collidable objects.
     *
     * @param collidables the list of collidable objects
     */
    public GameEnvironment(List<Collidable> collidables) {
        this.collidables = collidables;
    }

    /**
     * Default constructor for the class, will create an empty array list.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Function to add collidable objects to the game environment.
     * @param c - new collidable object to be added.
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Getter for collidable list.
     * @return - list of collidable objects.
     */
    public List<Collidable> getCollidables() {
        return collidables;
    }

    /**
     * Getter for the total gui width constant.
     * @return - integer of gui width.
     */
    public int getGuiWidth() {
        return WIDTH;
    }

    /**
     * Getter for the total gui height constant.
     * @return - integer of gui height.
     */
    public int getGuiHeight() {
        return HEIGHT;
    }

    /**
     * Getter for the boundaries' width constant.
     * @return - integer of boundaries' width.
     */
    public int getBoundsWidth() {
        return BOUNDS_WIDTH;
    }

    /**
     * Getter for the boundaries' height constant.
     * @return - integer of boundaries' height.
     */
    public int getBoundsHeight() {
        return BOUNDS_HEIGHT;
    }

    /**
     * Getter for the blocks' width constant.
     * @return - integer of blocks' width.
     */
    public int getBlockWidth() {
        return BLOCK_WIDTH;
    }

    /**
     * Getter for the blocks' height constant.
     * @return - integer of blocks' height.
     */
    public int getBlockHeight() {
        return BLOCK_HEIGHT;
    }

    /**
     * Getter for the amount of rows of blocks.
     * @return - number of rows of blocks.
     */
    public int getNumOfRows() {
        return NUM_OF_ROWS;
    }

    /**
     * Getter for the paddle's width constant.
     * @return - integer of paddle's width.
     */
    public int getPaddleWidth() {
        return PADDLE_WIDTH;
    }

    /**
     * Getter for the paddle's height constant.
     * @return - integer of paddle's height.
     */
    public int getPaddleHeight() {
        return PADDLE_HEIGHT;
    }

    /**
     * Assuming the object is moving from the start point to the end point, check if there are any collision in its
     * path. Add every collision to the list.
     * @param trajectory - a line of the movement from start to end.
     * @return - the closest collision point to the start point of the trajectory. If there are no collisions - null.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closestCollision = null;
        double closestDistance = Double.MAX_VALUE;

        for (Collidable c : collidables) {
            Point intersection = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (intersection != null) {
                double distance = trajectory.start().distance(intersection);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestCollision = new CollisionInfo(intersection, c);
                }
            }
        }

        return closestCollision;
    }
}
