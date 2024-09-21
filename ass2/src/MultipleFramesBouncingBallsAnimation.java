
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;

/**
 * Task 3.4 - animate half the balls in the gray rectangle while the other half are outside the rectangle.
 */
public class MultipleFramesBouncingBallsAnimation {
    //Screen size constant variables.
    private static final int TOTAL_WIDTH = 800, TOTAL_HEIGHT = 800;
    //Constant gray rectangle coordinates and size.
    private static final int X_GRAY = 50, Y_GRAY = 50, WIDTH_GRAY = 450, HEIGHT_GRAY = 450;
    //Constant yellow rectangle coordinates and size.
    private static final int X_YELLOW = 450, Y_YELLOW = 450, WIDTH_YELLOW = 150, HEIGHT_YELLOW = 150;
    //Max attempts for generating random center point.
    private static final int MAX_ATTEMPTS = 500;
    //Screen update interval in milliseconds - default by BIU is 50.
    private static final int UPDATE_INTERVAL = 50;

    /**
     * Main function.
     * @param args - command line arguments, containing the user's input for the balls sizes.
     */
    public static void main(String[] args) {
        MultipleFramesBouncingBallsAnimation main = new MultipleFramesBouncingBallsAnimation();
        //Make sure the user's input is valid.
        if (!main.isValidInput(args)) {
            System.out.println("Invalid input");
            return;
        }
        //Move the sizes from string array to int array, then sort the int array.
        int[] ballsSizes = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            ballsSizes[i] = Integer.parseInt(args[i]);
        }
        //Generate balls using the sizes the user provided.
        Ball[] ballsArray = main.generateBalls(ballsSizes);
        if (ballsArray == null) {
            System.out.print("Invalid input - Failed to generate balls");
            return;
        }
        //Start the GUI.
        GUI gui = new GUI("Multiple Frames Bouncing Balls", TOTAL_WIDTH, TOTAL_HEIGHT);
        //Start the animation
        main.startAnimation(gui, ballsArray);
    }

    /**
     * Check if the command line arguments are valid.
     * Will check ball's size, amount, digits etc.
     * @param args - the command line arguments.
     * @return - true if the input is valid (including edge cases). false if one or more are invalid.
     */
    private boolean isValidInput(String[] args) {
        //Make sure the input isn't empty.
        if (args.length == 0) {
            return false;
        }
        for (String arg : args) {
            //Check if includes only positive digits and check that the ball is able to be inside the GUI window.
            if ((!arg.matches("[0-9]+")) || (Double.parseDouble(arg) <= 0)
                    || (Double.parseDouble(arg) * 2 >= TOTAL_HEIGHT) || (Double.parseDouble(arg) * 2 >= TOTAL_WIDTH)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Function to generate random coordinates and velocities.
     * Will generate random coordinates for each ball and random velocities in a way that the bigger the ball the
     * slower it is.
     * above the size of 50 all balls will have velocity of random angle and min speed.
     * @param ballsSizes - array of sorted balls sizes.
     * @return - an array of balls containing random coordinates and velocities, null if failed to generate a ball.
     */
    private Ball[] generateBalls(int[] ballsSizes) {
        Ball[] ballsArray = new Ball[ballsSizes.length];
        //First half are in the gray rectangle.
        int halfBallsIndex = ballsArray.length / 2;
        //In case we have an odd numbers of balls add one and add the extra ball to be inside the gray rectangle.
        if (ballsArray.length % 2 != 0) {
            halfBallsIndex++;
        }
        for (int i = 0; i < halfBallsIndex; i++) {
            //Generate all the balls in the gray rectangle to be in its center.
            double xCenter = new Line(X_GRAY, Y_GRAY, X_GRAY + WIDTH_GRAY, Y_GRAY).middle().getX();
            double yCenter = new Line(X_GRAY, Y_GRAY, X_GRAY, Y_GRAY + HEIGHT_GRAY).middle().getY();
            Point p = new Point(xCenter, yCenter);
            ballsArray[i] = new Ball(p, ballsSizes[i], generateRandomColor(),
                    Velocity.generateRandomVelocity(ballsSizes[i]));
        }
        //Second half are outside both rectangles.
        for (int i = halfBallsIndex; i < ballsSizes.length; i++) {
            Point p = generateRandomCenter(ballsSizes[i]);
            if (p == null) {
                return null;
            }
            ballsArray[i] = new Ball(p, ballsSizes[i], generateRandomColor(),
                    Velocity.generateRandomVelocity(ballsSizes[i]));
        }
        return ballsArray;
    }

    /**
     * Generate a random center point for a ball.
     * Will create the point inside or outside the rectangles as needed.
     * @param size - size of the ball.
     * @return - the random generated center point.
     */
    private Point generateRandomCenter(int size) {
        int counter = 0;
        double x = randomDouble(size, TOTAL_WIDTH - size), y = randomDouble(size, TOTAL_HEIGHT - size);
        //If a ball got the first coordinates in the rectangles keep randomize until the ball is outside.
        while (isInvalidCoordinates((int) x, (int) y, size)) {
            //In case we didn't manage to generate a valid point return null - the size is invalid.
            if (counter >= MAX_ATTEMPTS) {
                return null;
            }
            x = randomDouble(size, TOTAL_WIDTH - size);
            y = randomDouble(size, TOTAL_HEIGHT - size);
            counter++;
        }
        return new Point(x, y);
    }

    /**
     * Create a random color for the balls.
     * Will generate 3 random numbers for RGB of the color.
     * @return - random color.
     */
    private Color generateRandomColor() {
        Random random = new Random();
        // Generates a value between 0 and 255 for red, green and blue.
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new Color(red, green, blue);
    }

    /**
     * Check if center coordinates are invalid for balls outside the rectangles.
     * Will check if the ball is inside the entire screen or one of the rectangles.
     * @param x - x value of the center.
     * @param y - y value of the center.
     * @param size - radius of the ball.
     * @return - true if the ball isn't in the entire screen or in one of the rectangles.
     */
    private boolean isInvalidCoordinates(int x, int y, int size) {
        return (isPointInRectangle(x, y, size, X_GRAY, Y_GRAY, X_GRAY + WIDTH_GRAY, Y_GRAY + HEIGHT_GRAY))
                || (isPointInRectangle(x, y, size, X_YELLOW, Y_YELLOW, X_YELLOW + WIDTH_YELLOW,
                Y_YELLOW + HEIGHT_YELLOW));
    }

    /**
     * Function to check if a point is in a rectangle.
     * @param x - x value of the point.
     * @param y - y value of the point.
     * @param size - radius of the ball.
     * @param xLeft - lower x value of the rectangle.
     * @param yLeft - lower y value of the rectangle.
     * @param xRight - higher x value of the rectangle.
     * @param yRight - higher y value of the rectangle.
     * @return - true if the point is in the rectangle, false if isn't.
     */
    private boolean isPointInRectangle(int x, int y, int size, int xLeft, int yLeft, int xRight, int yRight) {
        return ((x - size <= xRight) && (x + size >= xLeft)) && ((y - size <= yRight) && (y + size >= yLeft));
    }

    /**
     * Generates a random double in a specified range.
     * @param min - min value of the double.
     * @param max - max value of the double.
     * @return - the generated double.
     */
    private static double randomDouble(double min, double max) {
        Random rand = new Random();
        return min + (max - min) * rand.nextDouble();
    }

    /**
     * Start the animation.
     * Draw the rectangles using the constant variables and animating the balls.
     * @param gui - the GUI made by BIU for the animations.
     * @param ballsArray - array of balls we would like to animate.
     */
    private void startAnimation(GUI gui, Ball[] ballsArray) {
        Sleeper sleeper = new Sleeper();
        int halfBallsIndex = ballsArray.length / 2;
        //In case we have an odd numbers of balls add one and add the extra ball to be inside the gray rectangle.
        if (ballsArray.length % 2 != 0) {
            halfBallsIndex++;
        }
        while (true) {
            DrawSurface ds = gui.getDrawSurface();
            drawRectangle(ds, Color.GRAY, X_GRAY, Y_GRAY, WIDTH_GRAY, HEIGHT_GRAY);
            //Animate the first of the balls in the grey rectangle.
            for (int i = 0; i < halfBallsIndex; i++) {
                ballsArray[i].drawAnimation(ds, X_GRAY, X_GRAY + WIDTH_GRAY, Y_GRAY,
                        Y_GRAY + HEIGHT_GRAY);
                //bounce back the ball upon reaching the yellow rectangle.
                if (ballsArray[i].isReachedRectangle(X_YELLOW, WIDTH_YELLOW, Y_YELLOW, HEIGHT_YELLOW)) {
                    ballsArray[i].getVelocity().performUTurn();
                }
            }
            //This half of the balls will be outside the rectangles and inside the GUI window.
            for (int i = halfBallsIndex; i < ballsArray.length; i++) {
                //bounce back the ball upon reaching the gray rectangle.
                ballsArray[i].drawAnimation(ds, 0, TOTAL_WIDTH, 0, TOTAL_HEIGHT);
                if (ballsArray[i].isReachedRectangle(X_GRAY, WIDTH_GRAY, Y_GRAY, HEIGHT_GRAY)) {
                    ballsArray[i].getVelocity().performUTurn();
                }
                //bounce back the ball upon reaching the yellow rectangle.
                if (ballsArray[i].isReachedRectangle(X_YELLOW, WIDTH_YELLOW, Y_YELLOW, HEIGHT_YELLOW)) {
                    ballsArray[i].getVelocity().performUTurn();
                }
            }
            drawRectangle(ds, Color.YELLOW, X_YELLOW, Y_YELLOW, WIDTH_YELLOW, HEIGHT_YELLOW);
            gui.show(ds);
            sleeper.sleepFor(UPDATE_INTERVAL);
        }
    }

    /**
     * Function to draw a filled rectangle.
     * @param ds - surface for animations made by BIU.
     * @param color - color for the rectangle to be filled by.
     * @param x - left upper x coordinate.
     * @param y - left upper y coordinate.
     * @param width - width of the rectangle.
     * @param height - height of the rectangle.
     */
    private void drawRectangle(DrawSurface ds, Color color, int x, int y, int width, int height) {
        ds.setColor(color);
        ds.fillRectangle(x, y, width, height);
    }
}
