package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import geometry.Ball;
import geometry.Point;
import geometry.Rectangle;
import graphics.SpriteCollection;
import graphics.Sprite;
import physics.Collidable;
import physics.Velocity;

import java.awt.Color;
import java.util.Random;

/**
 * Class to handle the game's sprites animation and GUI creation.
 * @author Yuval Anteby
 */
public class Game {
    //Boundaries color variable.
    private static final Color BACKGROUND_COLOR = new Color(0, 128, 128), BOUNDS_COLOR = Color.GRAY;
    //Paddle color variable.
    private static final Color PADDLE_COLOR = Color.ORANGE;
    //Balls color variable.
    private static final Color FIRST_BALL_COLOR = Color.MAGENTA, SECOND_BALL_COLOR = Color.RED;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;

    /**
     * Constructor for the game, will create a new sprite collection and environment and set the GUI size.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui =  new GUI("Ass3Game", this.environment.getGuiWidth(), this.environment.getGuiHeight());
    }

    /**
     * Add a new collidable object to the game's environment.
     * @param c - collidable to be added.
     */
    public void addCollidable(Collidable c) {
        if (c != null) {
            this.environment.addCollidable(c);
        }
    }

    /**
     * Add a new sprite object to the game's environment.
     * @param s - sprite to be added.
     */
    public void addSprite(Sprite s) {
        if (s != null) {
            sprites.addSprite(s);
        }
    }

    /**
     * Function to initialize the game's objects.
     * Will create balls, paddle, blocks etc.
     */
    public void initialize() {
        //Create the boundaries of the GUI.
        generateBounds(this.environment.getBoundsWidth(), this.environment.getBoundsHeight());
        //Create the blocks.
        generateBlocks(this.environment.getNumOfRows(), this.environment.getBlockWidth(),
                this.environment.getBlockHeight(), this.environment.getBoundsWidth(),
                this.environment.getBoundsHeight());
        //Create the paddle.
        generatePaddle(this.environment.getBoundsHeight(), this.environment.getPaddleWidth(),
                this.environment.getPaddleHeight());
        //Create the ball.
        generateBalls();
    }

    /**
     * Generate blocks to be the boundaries and background of the GUI.
     * @param boundW - width of the GUI boundaries.
     * @param boundH - height of the GUI boundaries.
     */
    private void generateBounds(int boundW, int boundH) {
        //Background
        Rectangle backgroundRect = new Rectangle(new Point(0, 0),
                this.environment.getGuiWidth(), this.environment.getGuiHeight());
        Block backgroundBlock = new Block(backgroundRect, BACKGROUND_COLOR);
        backgroundBlock.addBackground(this);
        //Left boundary.
        Rectangle leftRec = new Rectangle(new Point(0, 1), boundW, this.environment.getGuiHeight());
        Block leftBound = new Block(leftRec, BOUNDS_COLOR);
        leftBound.addToGame(this);
        //Right boundary.
        Rectangle rightRec = new Rectangle(new Point(this.environment.getGuiWidth() - boundH, 1),
                boundW, this.environment.getGuiHeight());
        Block rightBound = new Block(rightRec, BOUNDS_COLOR);
        rightBound.addToGame(this);
        //Upper boundary.
        Rectangle topRec = new Rectangle(new Point(0, 1), this.environment.getGuiWidth(), boundH);
        Block topBound = new Block(topRec, BOUNDS_COLOR);
        topBound.addToGame(this);
        //Bottom boundary (Note: in the game there is no bottom boundary).
        Rectangle bottomRec = new Rectangle(new Point(0, this.environment.getGuiHeight() - boundH),
                this.environment.getGuiWidth(), boundH);
        Block bottomBound = new Block(bottomRec, BOUNDS_COLOR);
        bottomBound.addToGame(this);
    }

    /**
     * Generate balls for the game.
     */
    private void generateBalls() {
        Ball ball1 = new Ball(new Point(700, 580), 8, FIRST_BALL_COLOR, Velocity.randomVelocity(10));
        ball1.setGameEnvironment(this.environment);
        ball1.addToGame(this);
        Ball ball2 = new Ball(new Point(250, 570), 8, SECOND_BALL_COLOR, Velocity.randomVelocity(5));
        ball2.setGameEnvironment(this.environment);
        ball2.addToGame(this);
    }

    /**
     * Generate the paddle for the game.
     * Location will be based on the size of the paddle and the size of the bounds.
     * @param boundH - height of the GUI boundaries.
     * @param paddleW - width of the paddle.
     * @param paddleH - height of the paddle.
     */
    public void generatePaddle(int boundH, int paddleW, int paddleH) {
        Point topLeft = new Point((double) (this.environment.getGuiWidth() - paddleW) / 2,
                this.environment.getGuiHeight() - boundH -  paddleH);
        Rectangle paddleRec = new Rectangle(topLeft, paddleW, paddleH);
        Paddle paddle = new Paddle(new Block(paddleRec, PADDLE_COLOR), this.gui, this.environment);
        paddle.addToGame(this);
    }

    /**
     * Generate blocks to be on the top part of the gui.
     * @param rowsAmount - amount of rows of blocks to create.
     * @param blockW - width of the blocks.
     * @param blockH - height of the blocks.
     * @param boundW - width of the GUI boundaries.
     * @param boundH - height of the GUI boundaries.
     */
    public void generateBlocks(int rowsAmount, int blockW, int blockH, int boundW, int boundH) {
        //Generate several rows and columns of random colored blocks.
        for (int j = 0; j < rowsAmount; j++) {
            //generate row of blocks.
            Color color = generateRandomColor();
            for (int i = 0; i < rowsAmount * 2 - j; i++) {
                double xValue = this.environment.getGuiWidth() - ((i + 1) * blockW + boundW);
                double yValue = (j + 3) * blockH + boundH + 1;
                Rectangle rec = new Rectangle(new Point(xValue, yValue), blockW, blockH);
                Block block = new Block(rec, color);
                block.addToGame(this);
            }
        }
    }

    /**
     * Function to start the animation of the game.
     */
    public void run() {
        int counter = 0;
        //Start the GUI and sleeper.
        Sleeper sleeper = new Sleeper();
        //Set FPS.
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        //Start animation.
        while (true) {
            counter++;
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
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
}

