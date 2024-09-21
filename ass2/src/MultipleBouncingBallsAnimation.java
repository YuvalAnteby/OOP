
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;
/**
 * Animation of several balls of different sizes and different speeds.
 */
public class MultipleBouncingBallsAnimation {
    //Screen size constant variables.
    private static final int WIDTH = 500, HEIGHT = 500;
    //Screen update interval in milliseconds - default by BIU is 50.
    private static final int UPDATE_INTERVAL = 50;

    /**
     * Main function.
     * @param args - command line arguments, containing the user's input for the balls sizes.
     */
    public static void main(String[] args) {
        MultipleBouncingBallsAnimation main = new MultipleBouncingBallsAnimation();
        //Make sure the user's input is valid.
        if (!main.isValidInput(args)) {
            System.out.println("Invalid input");
        } else {
            //Move the sizes from string array to int array, then sort the int array.
            int[] ballsSizes = new int[args.length];
            for (int i = 0; i < args.length; i++) {
                ballsSizes[i] = Integer.parseInt(args[i]);
            }
            //Generate balls using the sizes the user provided.
            Ball[] ballsArray = main.generateBalls(ballsSizes);
            //Start the GUI
            GUI gui = new GUI("Multiple Bouncing Balls", WIDTH, HEIGHT);
            Sleeper sleeper = new Sleeper();
            //Run the animation.
            while (true) {
                DrawSurface ds = gui.getDrawSurface();
                //Update each ball.
                for (Ball ball: ballsArray) {
                    ball.drawAnimation(ds, 0, WIDTH, 0, HEIGHT);
                }
                gui.show(ds);
                sleeper.sleepFor(UPDATE_INTERVAL); // wait for 50 milliseconds.
            }
        }
    }

    /**
     * Check if the command line arguments are valid.
     * @param args - the command line arguments.
     * @return - true if there are only digits and valid sizes. false if not.
     */
    private boolean isValidInput(String[] args) {
        //Make sure the input isn't empty.
        if (args.length == 0) {
            return false;
        }
        //Check if includes only positive digits and check that the ball is able to be inside the GUI window.
        for (String arg : args) {
            if (!arg.matches("[0-9]+") || (Double.parseDouble(arg) <= 0)
                    || (Double.parseDouble(arg) * 2 >= HEIGHT) || (Double.parseDouble(arg) * 2 >= WIDTH)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Function to generate balls using random coordinates and random velocities.
     * Will generate random coordinates for each ball and random velocities in a way that the bigger the ball the
     * slower it is.
     * @param ballsSizes - array balls sizes.
     * @return - an array of balls containing random coordinates and velocities.
     */
    private Ball[] generateBalls(int[] ballsSizes) {
        Ball[] ballsArray = new Ball[ballsSizes.length];
        for (int i = 0; i < ballsSizes.length; i++) {
            //Generate random coordinates for the ball's center.
            double x = randomDouble(ballsSizes[i], WIDTH - ballsSizes[i]);
            double y = randomDouble(ballsSizes[i], HEIGHT - ballsSizes[i]);
            //As a default use black balls and generate random velocity for the ball based on it's size.
            ballsArray[i] = new Ball(new Point(x, y), ballsSizes[i], Color.BLACK,
                    Velocity.generateRandomVelocity(ballsSizes[i]));
        }
        return ballsArray;
    }

    /**
     * Generates a random double in a specified range.
     * @param min - min value of the double.
     * @param max - max value of the double.
     * @return - the generated double.
     */
    private double randomDouble(double min, double max) {
        Random rand = new Random();
        return min + (max - min) * rand.nextDouble();
    }
}