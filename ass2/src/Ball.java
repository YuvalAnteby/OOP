import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Class to represent a ball in the GUI.
 */
public class Ball {
    private Point center;
    private int r;
    private Color color;
    private Velocity velocity;

    /**
     * Constructor for the ball class.
     * in this constructor the default velocity is 0.
     * @param center - the center point of the ball.
     * @param r - integer value of the radius of the ball.
     * @param color - color of the ball to be filled by.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
    }

    /**
     * Constructor for the ball class.
     * in this constructor the default velocity is 0.
     * @param center - the center point of the ball.
     * @param r - integer value of the radius of the ball.
     * @param color - color of the ball to be filled by.
     * @param velocity - the velocity of the ball for an animation - default value of 0.
     */
    public Ball(Point center, int r, java.awt.Color color, Velocity velocity) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.velocity = velocity;
    }

    /**
     * Constructor for the ball class.
     * @param x - x value of the center point of the ball.
     * @param y - y value of the center point of the ball.
     * @param r - integer value of the radius of the ball.
     * @param color - color of the ball to be filled by.
     * @param velocity - the velocity of the ball for animations.
     */
    public Ball(int x, int y, int r, java.awt.Color color, Velocity velocity) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.velocity = velocity;
    }

    /**
     * Get the x value of the center of this ball.
     * @return - x integer value of the center point.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Get the y value of the center of this ball.
     * @return - y integer value of the center point.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Get the radius size of this ball.
     * @return - integer value of this ball's radius.
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Get the color of the ball.
     * @return - color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Get the ball's velocity variable.
     * @return - the ball's velocity.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Change the ball's velocity variable by using a new velocity.
     * @param v - the new velocity.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Change the ball's velocity variable by using a new doubles.
     * @param dx - speed of the x-axis.
     * @param dy - speed of the y-axis.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Draw the ball on the surface (GUI) using the DrawSurface from BIU.
     * @param surface - the surface variable made by BIU to use GUI.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
    }

    /**
     * Change the center point of the ball according to the current velocity.
     */
    public void moveOneStep() {
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    /**
     * Check if the ball reached the horizontal boundaries.
     * for parts 3.3 and 3.4 it'll be the GUI window. For task 3.4 it'll also be for the balls in the gray rectangle.
     *
     * @param lowerBoundary - the lower boundary for the ball.
     * @param upperBoundary - the higher boundary for the ball.
     * @return - true if the ball reached one of the boundaries.
     */
    public boolean isReachedHorizontalBoundary(int lowerBoundary, int upperBoundary) {
        return ((this.velocity.getDx() < 0) && (this.center.getX() - this.r <= lowerBoundary))
                || ((this.velocity.getDx() > 0) && (this.center.getX() + this.r >= upperBoundary));
    }

    /**
     * Check if the ball reached the vertical boundaries.
     * for parts 3.3 and 3.4 it'll be the GUI window. For task 3.4 it'll also be for the balls in the gray rectangle.
     *
     * @param lowerBoundary - the lower boundary for the ball.
     * @param upperBoundary - the higher boundary for the ball.
     * @return - true if the ball reached one of the boundaries.
     */
    public boolean isReachedVerticalBoundary(int lowerBoundary, int upperBoundary) {
        return ((this.velocity.getDy() < 0) && (this.center.getY() - this.r <= lowerBoundary))
                || ((this.velocity.getDy() > 0) && (this.center.getY() + this.r >= upperBoundary));
    }

    /**
     * Check if a ball outside the rectangles reached a rectangle.
     * Will check if the x and y values are in range of the x,y coordinates of the rectangle.
     *
     * @param lowerX - left upper x coordinate of the rectangle.
     * @param width - width of the rectangle.
     * @param lowerY - left upper y coordinate of the rectangle.
     * @param height - height of the rectangle.
     * @return - true if the ball reached, false if not.
     */
    public boolean isReachedRectangle(int lowerX, int width, int lowerY, int height) {
        return (((this.center.getX() - this.r <= lowerX + width) && (this.center.getX() + this.r >= lowerX))
                && ((this.center.getY() - this.r <= lowerY + height) && (this.center.getY() + this.r >= lowerY)));
    }

    /**
     * Function to draw and move the ball using the classes we created and BIU jar file.
     *
     * @param ds - surface for animations made by BIU.
     * @param lowerXBoundary - lower boundary for the x-axis.
     * @param upperXBoundary - upper boundary for the x-axis.
     * @param lowerYBoundary - lower boundary for the y-axis.
     * @param upperYBoundary - upper boundary for the y-axis.
     */
    public void drawAnimation(DrawSurface ds, int lowerXBoundary, int upperXBoundary, int lowerYBoundary,
                              int upperYBoundary) {
        this.moveOneStep();
        //Check if the ball is touching the upper or lower boundaries. change the direction accordingly.
        if (this.isReachedVerticalBoundary(lowerYBoundary, upperYBoundary)) {
            this.getVelocity().flipAxisY();
            this.moveOneStep();
        }
        //Check if the ball is touching the left or right boundaries. change the direction accordingly.
        if (this.isReachedHorizontalBoundary(lowerXBoundary, upperXBoundary)) {
            this.getVelocity().flipAxisX();
            this.moveOneStep();
        }
        this.moveOneStep();
        this.drawOn(ds);
    }
}

