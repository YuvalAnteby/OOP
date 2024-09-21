import biuoop.GUI;
import biuoop.DrawSurface;

import java.util.Random;
import java.awt.Color;

/**
 * Part 2 of the second assessment.
 * Generating random points and draw from them lines, dots and triangles.
 * use ant run2 command to run it.
 */
public class AbstractArtDrawing {
    //Screen size constant variables.
    private static final int WIDTH = 400, HEIGHT = 300;
    //Dots constant variables.
    private static final int DOTS_RADIUS = 3;
    private static final Color MIDDLE_DOT_COLOR = Color.BLUE, INTERSECTION_COLOR = Color.RED;
    //Lines constant variables.
    private static final int LINE_AMOUNT = 10;
    private static final Color NORMAL_LINE_COLOR = Color.BLACK, TRIANGLE_COLOR = Color.GREEN;

    /**
     * Main function.
     * @param args - command line arguments. (not in use currently).
     */
    public static void main(String[] args) {
        AbstractArtDrawing main = new AbstractArtDrawing();
        GUI gui = new GUI("AbstractArtDrawing", WIDTH, HEIGHT);
        DrawSurface ds = gui.getDrawSurface();
        Line[] lineArray = new Line[LINE_AMOUNT];
        //generate 10 random lines and draw them and their middle point.
        for (int i = 0; i < LINE_AMOUNT; i++) {
            lineArray[i] = main.generateRandomLine();
            main.drawDot(lineArray[i].middle(), MIDDLE_DOT_COLOR, ds);
            main.drawLine(lineArray[i], NORMAL_LINE_COLOR, ds);
        }
        // Draw intersection points and triangles.
        main.findDrawTriangles(lineArray, ds);
        main.findDrawIntersections(lineArray, ds);
        //Show the GUI after all the editing.
        gui.show(ds);
    }

    /**
     * Function to generate a new line randomly with boundaries.
     *
     * @return - an instance of a line made by 2 random generated points.
     */
    private Line generateRandomLine() {
        Random rand = new Random();
        //Generate 2 random pairs of x,y values for the points.
        double x1 = rand.nextDouble() * WIDTH, y1 = rand.nextDouble() * HEIGHT;
        Point startPoint = new Point(x1, y1);
        double x2 = rand.nextDouble() * WIDTH, y2 = rand.nextDouble() * HEIGHT;
        Point endPoint = new Point(x2, y2);
        //Make sure the points are different, so it would be a line.
        while (startPoint.equals(endPoint)) {
            x2 = rand.nextDouble() * WIDTH;
            y2 = rand.nextDouble() * HEIGHT;
            endPoint = new Point(x2, y2);
        }
        return new Line(startPoint, endPoint);
    }

    /**
     * Function to draw a new line in the GUI using a line.
     * @param line - instance of a line object. containing the start and end points for the DrawSurface to draw.
     * @param color - color of the line.
     * @param ds - interface instance made by BIU for the GUI.
     */
    private void drawLine(Line line, Color color, DrawSurface ds) {
        ds.setColor(color);
        ds.drawLine((int) line.start().getX(), (int) line.start().getY(), (int) line.end().getX(),
                (int) line.end().getY());

    }

    /**
     * Function to draw a new point of radius 3 in the GUI.
     * @param p - a point object containing the x,y values to draw the center of the circle.
     * @param color - color of the filling of the point.
     * @param ds - interface instance made by BIU for the GUI.
     */
    private void drawDot(Point p, Color color, DrawSurface ds) {
        ds.setColor(color);
        ds.fillCircle((int) p.getX(), (int) p.getY(), DOTS_RADIUS);
    }

    /**
     * Function to find intersections and draw a dot at each one.
     * @param lines - array of lines to be checked for intersections.
     * @param ds - - interface instance made by BIU for the GUI.
     */
    private void findDrawIntersections(Line[] lines, DrawSurface ds) {
        //Loop through all the lines and check each one against the others.
        for (int i = 0; i < lines.length; i++) {
            for (int j = i + 1; j < lines.length; j++) {
                Point intersectionPoint = lines[i].intersectionWith(lines[j]);
                //Draw a new dot if indeed there is intersection (and also none infinite intersections).
                if (intersectionPoint != null) {
                    drawDot(intersectionPoint, INTERSECTION_COLOR, ds);
                }
            }
        }
    }

    /**
     * Function to find triangles and draw their lines green.
     * @param lines - array of lines to be checked if they create triangles.
     * @param ds - - interface instance made by BIU for the GUI.
     */
    private void findDrawTriangles(Line[] lines, DrawSurface ds) {
        //Loop through all combinations of three lines
        for (int i = 0; i < lines.length; i++) {
            for (int j = i + 1; j < lines.length; j++) {
                for (int k = j + 1; k < lines.length; k++) {
                    // Check if the three lines form a triangle
                    if (formsTriangle(lines[i], lines[j], lines[k])) {
                        // Get the intersection points of the lines.
                        Point intersection1 = lines[i].intersectionWith(lines[j]);
                        Point intersection2 = lines[i].intersectionWith(lines[k]);
                        Point intersection3 = lines[j].intersectionWith(lines[k]);
                        // Draw the lines between intersection points in green.
                        drawLine(new Line(intersection1, intersection2), TRIANGLE_COLOR, ds);
                        drawLine(new Line(intersection1, intersection3), TRIANGLE_COLOR, ds);
                        drawLine(new Line(intersection2, intersection3), TRIANGLE_COLOR, ds);
                    }
                }
            }
        }
    }

    /**
     * Function to check if 3 lines form a triangle.
     * @param line1 - first line to be checked.
     * @param line2 - second line to be checked.
     * @param line3 - third line to be checked.
     * @return - true if the 3 lines indeed create a triangle, false if not.
     */
    private boolean formsTriangle(Line line1, Line line2, Line line3) {
        // Three lines form a triangle if each pair intersect (1-2, 1-3, 2-3).
        return line1.isIntersecting(line2, line3) && line2.isIntersecting(line3);
    }

}
