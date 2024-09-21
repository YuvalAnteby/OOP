
import java.util.Random;

/**
 * Class to represent velocity.
 * Specifies the change in position on the `x` and the `y` axis.
 */
public class Velocity {
    //Speeds Constant variables.
    private static final Double MIN_SPEED = 0.25, MAX_SPEED = 10.0;

    private double dx;
    private double dy;

    /**
     * Constructor for the velocity class using speed for x and y-axis.
     * @param dx - x-axis speed.
     * @param dy - y-axis speed.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Default constructor for creating a velocity of 0.
     */
    public Velocity() {
        this.dx = 0;
        this.dy = 0;
    }

    /**
     * Get the velocity of the x-axis.
     * @return - double value of the velocity for x-axis.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Get the velocity of the y-axis.
     * @return - double value of the velocity for y-axis.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Change the velocity of the x-axis.
     * @param dx - the new double value for the velocity of the x-axis.
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Change the velocity of the y-axis.
     * @param dy - the new double value for the velocity of the y-axis.
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Set the velocity using angle and speed.
     * using vectors and sin, cos definitions we set values to the dx,dy variables (assuming up is 0 degrees).
     * @param angle - the angle in degrees.
     * @param speed - speed's vector length.
     * @return - the new velocity.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Generate random angle and give speed to the ball according to it's size.
     * The bigger the ball the slower it is. Max and min speed are constant and can be changed, all balls above size of
     * 50 will get the min speed.
     * @param size - size of the ball.
     * @return - new velocity created by angle and speed.
     */
    public static Velocity generateRandomVelocity(int size) {
        Random rnd = new Random();
        double angle = 360 * rnd.nextDouble();
        double speed;
        if (size >= 50) {
            //For bigger than radius 50 balls give the min speed.
            speed = MIN_SPEED;
        } else {
            //For smaller balls generate random speed that is faster based on their size (the bigger, the slower).
            speed =  MAX_SPEED / size;
        }
        return Velocity.fromAngleAndSpeed(angle, speed);
    }

    /**
     * Change the direction of the y-axis velocity to be the other direction.
     */
    public void flipAxisY() {
        this.dy *= -1;
    }

    /**
     * Change the direction of the x-axis velocity to be the other direction.
     */
    public void flipAxisX() {
        this.dx *= -1;
    }

    /**
     * change the direction 180 degrees (make a U turn).
     */
    public void performUTurn() {
        this.flipAxisY();
        this.flipAxisX();
    }

    /**
     * Change a point's coordinates according to the velocity.
     * taking a point at (x,y) and changing the coordinates to (x+dx, y+dy).
     * @param p - the point we would like to change its coordinates.
     * @return - the new point after changes.
     */
    public Point applyToPoint(Point p) {
        if (p == null) {
            return null;
        }
        return new Point(p.getX() + dx, p.getY() + dy);
    }
}
