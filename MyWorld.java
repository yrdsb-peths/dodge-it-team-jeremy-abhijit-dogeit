import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    private static final int[] LANE_Y = {85, 155, 225, 295};
    private static int bestScore = 0;

    private int score;
    private boolean gameRunning;
    private int carSpawnCounter = 0;

    /**
     * Constructor for objects of class MyWorld.
     *
     */
    public MyWorld()
    {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);
        drawBackdrop();
        startRound();
    }

    public void act()
    {
        if (gameRunning) {
            score++;
            
            // Progressive difficulty: spawn additional cars
            carSpawnCounter++;
            int difficulty = score / 1500; // Difficulty tier
            int spawnInterval = Math.max(90 - (difficulty * 8), 50); // More reasonable spawn rate
            
            if (carSpawnCounter >= spawnInterval) {
                spawnRandomCar();
                carSpawnCounter = 0;
            }
            
            showHud();
            return;
        }
        
        // Game over - only show restart message (gameOver actor handles crash display)
        String key = Greenfoot.getKey();
        if (key != null && (key.equals("enter") || key.equals("return") || key.equals("space") || key.equals("r"))) {
            Greenfoot.setWorld(new MyWorld());
        }
    }

    public boolean isGameRunning()
    {
        return gameRunning;
    }

    public int getLaneY(int laneIndex)
    {
        return LANE_Y[laneIndex];
    }

    public int getLaneCount()
    {
        return LANE_Y.length;
    }

    public int getCarSpeed()
    {
        // Gradually increase from 4 to 7 pixels/frame
        return Math.min(4 + (score / 1200), 7);
    }

    public void onHeroHit()
    {
        if (!gameRunning) {
            return;
        }

        gameRunning = false;
        bestScore = Math.max(bestScore, score);
        showHud();
        addObject(new gameOver(score, bestScore), getWidth() / 2, getHeight() / 2);
    }

    private void showHud()
    {
        
        showText("Distance: " + score + " m", 120, 22);
        showText("Use W & S to move", 460, 22);
    }

    private void drawBackdrop()
    {
        GreenfootImage bg = new GreenfootImage(getWidth(), getHeight());
        bg.setColor(new Color(34, 114, 51));
        bg.fill();

        bg.setColor(new Color(62, 62, 66));
        bg.fillRect(0, 45, getWidth(), getHeight() - 90);

        bg.setColor(new Color(245, 245, 245));
        bg.fillRect(0, 45, getWidth(), 3);
        bg.fillRect(0, getHeight() - 48, getWidth(), 3);

        bg.setColor(new Color(255, 255, 255, 190));
        for (int i = 0; i < LANE_Y.length - 1; i++) {
            int y = (LANE_Y[i] + LANE_Y[i + 1]) / 2;
            for (int x = 0; x < getWidth(); x += 42) {
                bg.fillRect(x, y, 24, 3);
            }
        }

        setBackground(bg);
    }

    private void startRound()
    {
        score = 0;
        carSpawnCounter = 0;
        gameRunning = true;

        Hero hero = new Hero();
        addObject(hero, 100, getLaneY(1));

        // Start fair: fewer initial cars with good spacing to prevent early crowding
        int[] initialLanes = {0, 2};
        for (int i = 0; i < initialLanes.length; i++) {
            int lane = initialLanes[i];
            Banana car = new Banana();
            car.setLane(lane);
            addObject(car, getWidth() + 200 + (i * 250), getLaneY(lane));
        }

        showHud();
    }
    
    private void spawnRandomCar()
    {
        Banana car = new Banana();
        int randomLane = Greenfoot.getRandomNumber(getLaneCount());
        car.setLane(randomLane);
        // Spawn further off-screen to prevent clustering and collision at spawn point
        addObject(car, getWidth() + 160 + Greenfoot.getRandomNumber(100), getLaneY(randomLane));
    }
}
