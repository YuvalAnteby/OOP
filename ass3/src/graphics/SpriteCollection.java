package graphics;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to contain a list of objects which use sprite interface.
 * @author Yuval Anteby
 */
public class SpriteCollection {
    private List<Sprite> spriteList;

    /**
     * Default constructor for the class, will initialize a new array list.
     */
    public SpriteCollection() {
        this.spriteList = new ArrayList<>();
    }

    /**
     * Add a new sprite to the list of the sprites list.
     * @param s - a new sprite to be added.
     */
    public void addSprite(Sprite s) {
        if (s != null) {
            spriteList.add(s);
        }
    }

    /**
     * Notify every sprite that time passed.
     */
    public void notifyAllTimePassed() {
        for (Sprite sprite: spriteList) {
            sprite.timePassed();
        }
    }

    /**
     * Call the draw animation for each sprite.
     * @param d - the draw surface of the gui.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite: spriteList) {
            sprite.drawOn(d);
        }
    }
}
