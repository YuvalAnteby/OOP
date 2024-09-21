/**
 * Class to represent a point.
 * Includes the x and y values of the point.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Constructor for the Point class.
     * @param x - x value in double of the point.
     * @param y - y value in double of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Function to calculate the distance between a point to another.
     * @param other - the other pointer reference.
     * @return - distance (in double) between our point and another.
     */
    public double distance(Point other) {
        if (other == null) {
            return -1;
        }
        //Calculate the distance for points x1,x2 and y1,y2 formula: ((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2))
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }

    /**
     * Checks if our point has the same x and y values as the other point.
     * @param other - another point to be checked with.
     * @return - true if the points has the same values, false if one or two of the values is different.
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        double epsilon = 0.00001;
        //There can be a small difference between two variables of the same values in double, use epsilon for checking.
        return (Math.abs(this.getX() - other.getX()) <= epsilon) && (Math.abs(this.getY() - other.getY()) <= epsilon);
    }

    /**
     * Getter for the y value variable.
     * @return - the value of the x variable of our point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Getter for the y value variable.
     * @return - the value of the y variable of our point.
     */
    public double getY() {
        return this.y;
    }

}
