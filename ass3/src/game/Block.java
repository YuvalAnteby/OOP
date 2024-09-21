package game;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import physics.Velocity;
import physics.Collidable;
import graphics.Sprite;

import java.awt.Color;


/**
 * Class to represent a block on the GUI. Will be of a rectangle shape and get a color.
 *  * @author Yuval Anteby
 */
public class Block implements Collidable, Sprite {

    private Rectangle rectangle;
    private Color color;

    /**
     * Constructor for the block class.
     * @param rectangle - rectangle that the block is made of.
     * @param color - color that the block will be filled by.
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    @Override
    public Block getCollisionBlock() {
        return this;
    }

    /**
     * Getter for the block's color.
     * @return - color of the block.
     */
    public Color getColor() {
        return color;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        if ((collisionPoint == null) || (currentVelocity == null)) {
            throw new IllegalArgumentException("Null exception hit function");
        }
        double dx = currentVelocity.getDx(), dy = currentVelocity.getDy();
        //Check horizontal lines collision.
        if (this.rectangle.getTopLine().isContaining(collisionPoint)
                || this.rectangle.getBottomLine().isContaining(collisionPoint)) {
            dy *= -1;
        }
        //Check vertical lines collision.
        if (this.rectangle.getLeftLine().isContaining(collisionPoint)
                || this.rectangle.getRightLine().isContaining(collisionPoint)) {
            dx *= -1;
        }
        return new Velocity(dx, dy);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
        d.setColor(Color.black);
        d.drawRectangle((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(),
                (int) rectangle.getWidth(), (int) rectangle.getHeight());
    }

    @Override
    public void timePassed() {

    }

    /**
     * Add the block to the game a sprite and a collidable object.
     * @param g - the game reference we add to.
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Add the background as only a sprite to the game.
     * @param g - the game reference we add to.
     */
    public void addBackground(Game g) {
        g.addSprite(this);
    }
    @Override
    public String toString() {
        return "Block [rectangle=" + rectangle + ", color=" + color + "]";
    }
}
