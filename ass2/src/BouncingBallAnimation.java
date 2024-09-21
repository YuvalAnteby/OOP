
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;

/**
 * Basic animation of a ball moving in space, the ball's movement is restricted to the windows boundaries.
 */
public class BouncingBallAnimation {
    //Screen size constant variables.
    private static final int WIDTH = 500, HEIGHT = 500;
    //Screen update interval in milliseconds - default by BIU is 50.
    private static final int UPDATE_INTERVAL = 50;
    //Ball's default radius size.
    private static final int DEFAULT_RADIUS = 30;
    //Ball's default color
    private static final Color DEFAULT_COLOR = Color.BLACK;

    /**
     * Main function.
     * @param args - command line arguments. In args the 1st index is x value for the center of the ball,
     *            2nd is the y value, the 3rd is the velocity of x-axis and 4th is the velocity of y-axis.
     */
    public static void main(String[] args) {
        BouncingBallAnimation main = new BouncingBallAnimation();
        if (!main.isValidInput(args)) {
            System.out.print("Invalid input\n");
        } else {
            main.startAnimation(new Point(Double.parseDouble(args[0]), Double.parseDouble(args[1])),
                    Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        }
    }

    /**
     * Check if the command line arguments are valid.
     * @param args - the command line arguments.
     * @return - true if there are only digits and exactly 4 numbers including valid coordinates. false if not.
     */
    private boolean isValidInput(String[] args) {
        //Make sure the user provided exactly 4 numbers (2 for coordinates and 2 for velocity).
        if (args.length != 4) {
            return false;
        }
        //Make sure each string has only digits.
        for (String arg : args) {
            if (!arg.matches("[0-9]+")) {
                return false;
            }
        }
        //Make sure the coordinates provided will create a ball entirely inside the GUI.
        return (Integer.parseInt(args[0]) + DEFAULT_RADIUS < WIDTH) && (Integer.parseInt(args[0]) - DEFAULT_RADIUS > 0)
                && (Integer.parseInt(args[1]) + DEFAULT_RADIUS < HEIGHT)
                && (Integer.parseInt(args[1]) - DEFAULT_RADIUS > 0);
    }

    /**
     * Function to draw and move the ball using the classes we created and BIU jar file.
     * @param start - the first point of the center of the ball.
     * @param dx - velocity's x-axis value by the user.
     * @param dy - velocity's y-axis value by the user.
     */
    private void startAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("Bouncing Ball", WIDTH, HEIGHT);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(new Point(start.getX(), start.getY()), DEFAULT_RADIUS, DEFAULT_COLOR);
        ball.setVelocity(new Velocity(dx, dy));
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            ball.drawAnimation(d, 0, WIDTH, 0, HEIGHT);
            gui.show(d);
            sleeper.sleepFor(UPDATE_INTERVAL); // wait for 50 milliseconds.
        }
    }
}


