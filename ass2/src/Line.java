/**
 * Class to represent a line made be two points.
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * Constructor for the line class made by two points.
     * @param start - First point. (where the line starts).
     * @param end - Second point. (where the line ends).
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructor for the line class made by 2 pairs of x,y.
     * @param x1 - x value of where the line starts.
     * @param y1 - y value of where the line starts.
     * @param x2 - x value of where the line ends.
     * @param y2 - y value of where the line ends.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Function to calculate the length of the line. (the distance between the start and end points).
     * @return - the length of the line in double.
     */
    public double length() {
        return this.start.distance(end);
    }

    /**
     * Function to get the middle point of our line.
     * @return - point in the middle of the line (between start and end points)
     */
    public Point middle() {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    /**
     * Function to get the start point of our line.
     * @return - start point of our line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Function to get the end point of our line.
     * @return - end point of our line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Function to calculate the incline of our line.
     * (meant for calculation purposes).
     * Using the incline calculation from math classes: m = (y1 - y2) / (x1 - x2)
     * @return - value of the incline in double.
     */
    public double getIncline() {
        if (this.end.getX() - this.start.getX() != 0) {
            return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
        }
        return Double.POSITIVE_INFINITY;
    }

    /**
     * Function to find the y value once x value is 0 regardless of if it's between the start and end point or not.
     * (meant for calculation purposes).
     * Using the equation: m = (y1 - yB) / (x1 - 0) we get to this calculation
     * @return - value of y in double.
     */
    public double x0Point() {
        if (getIncline() != Double.POSITIVE_INFINITY) {
            return this.start.getY() - this.getIncline() * this.start.getX();
        }
        return Double.POSITIVE_INFINITY;
    }

    /**
     * Function to check if our line intersecting with one another.
     * @param other - another line to be checked with ours.
     * @return - true if they intersect, false if they aren't.
     */
    public boolean isIntersecting(Line other) {
        return (intersectionWith(other) != null) || (this.intersectionWith(other) == null
                && (this.equals(other) || this.isSubLine(other)));
    }

    /**
     * Function to check if 3 lines intersect.
     * @param other1 - first line to be checked if it's intersecting with ours
     * @param other2 - second line to be checked if it's intersecting with ours
     * @return - true if the 2 lines intersect with our line, false if one of them (or both) aren't.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        if (other1 == null || other2 == null) {
            return false;
        }
        return this.isIntersecting(other1) && this.isIntersecting(other2);
    }

    /**
     * Function to check intersection point of our line with another line.
     * @param other - another line to be checked with ours.
     * @return - a point containing the x,y values of the intersection point between our line and another, if there is
     * no intersection point returns null.
     */
    public Point intersectionWith(Line other) {
        //If the lines are equal or sub lines, then there are infinite intersection points.
        if (other == null || this.equals(other) || this.isSubLine(other)) {
            return null;
        }
        double xInter, yInter;
        if ((this.getIncline() == Double.POSITIVE_INFINITY) && (other.getIncline() == Double.POSITIVE_INFINITY)) {
            //In case both lines are of the kind x = number then they're either sub lines, equal or not intersecting.
            return null;
        } else if (this.getIncline() == Double.POSITIVE_INFINITY) {
            //In case our line is of the kind x = number then the intersection has to be of it's x value.
            xInter = this.start.getX();
            yInter = other.getIncline() * xInter + other.x0Point();
        } else if (other.getIncline() == Double.POSITIVE_INFINITY) {
            //In case the other line is of the kind x = number then the intersection has to be of it's x value.
            xInter = this.start.getX();
            yInter = other.getIncline() * xInter + other.x0Point();
        } else {
            //Using the method from school: mx + b = Mx + c and then finding the x value of the intersection.
            xInter = (other.x0Point() - this.x0Point()) / (this.getIncline() - other.getIncline());
            yInter = this.getIncline() * xInter + this.x0Point();
        }
        //Check if the intersection X and y values if in the scope of our lines.
        if (!this.isInXScope(xInter) || !this.isInYScope(yInter) || !other.isInXScope(xInter)
                || !other.isInYScope(yInter)) {
            return null;
        }
        return new Point(xInter, yInter);
    }

    /**
     * Function to check if two line are identical. (checks if each pair of points are equal).
     * @param other - another line to be checked with.
     * @return - true if our start and ends points is the same as the other line's, false if one of them is different.
     */
    public boolean equals(Line other) {
        if (other == null) {
            return false;
        }
        return (this.start.equals(other.start) && this.end.equals(other.end)) || (this.start.equals(other.end)
                && this.end.equals(other.start));
    }

    /**
     * Checks if the lines are a part of one another.
     * @param other - another line to be checked with ours.
     * @return - true if one of the lines is a part of another.
     */
    public boolean isSubLine(Line other) {
        double epsilon = 0.00001;
        if ((this.getIncline() == Double.POSITIVE_INFINITY) && (other.getIncline() == Double.POSITIVE_INFINITY)) {
            //In case both lines are of the kind x = number then they're sub lines only if they have the same x value.
            return this.start.getX() == other.start.getX();
        } else if ((this.getIncline() != Double.POSITIVE_INFINITY)
                && (other.getIncline() != Double.POSITIVE_INFINITY)) {
            //In case both lines are of y=mx+b then they need to have the same incline and the y value on the y-axis.
            return Math.abs(this.getIncline() - other.getIncline()) <= epsilon
                    && Math.abs(this.x0Point() - other.x0Point()) <= epsilon;
        }
        //If one of them is of y=mx+b and the other x=number they can't be sub lines.
        return false;
    }

    /**
     * Checks if the x value is in between the x value of start and end points.
     * @param xValue - double value representing x value of a point.
     * @return - true if the value is in between start and end, false if not.
     */
    private boolean isInXScope(double xValue) {
        return (this.start.getX() <= xValue && this.end.getX() >= xValue)
                || (this.start.getX() >= xValue && this.end.getX() <= xValue);
    }

    /**
     * Checks if the y value is in between the y value of start and end points.
     * @param yValue - double value representing y value of a point.
     * @return - true if the value is in between start and end, false if not.
     */
    private boolean isInYScope(double yValue) {
        return (this.start.getY() <= yValue && this.end.getY() >= yValue)
                || (this.start.getY() >= yValue && this.end.getY() <= yValue);
    }
}