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
            int spawnInterval = Math.max(70 - (difficulty * 10), 30); // Cars spawn much faster
            
            if (carSpawnCounter >= spawnInterval) {
                spawnRandomCar();
                if (score > 1200 && Greenfoot.getRandomNumber(100) < 35) {
                    spawnRandomCar();
                }
                carSpawnCounter = 0;
            }
            
            showHud();
            return;
        }

        showText("Crash! Distance: " + score + " m", getWidth() / 2, getHeight() / 2 + 10);
        showText("Best: " + bestScore + " m", getWidth() / 2, getHeight() / 2 + 40);
        showText("Press ENTER / SPACE / R to restart", getWidth() / 2, getHeight() / 2 + 70);
        
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
        int difficulty = (score / 1500) + 1;
        String difficultyText = "Lvl " + difficulty;
        
        showText("Distance: " + score + " m", 70, 22);
        showText("Best: " + bestScore + " m", 220, 22);
        showText(difficultyText, 350, 22);
        showText("W/S or Arrows to move", 470, 22);

        if (gameRunning) {
            showText("", getWidth() / 2, getHeight() / 2 + 70);
        }
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

        // Start fair: fewer cars and no immediate crowding.
        int[] initialLanes = {0, 2};
        for (int i = 0; i < initialLanes.length; i++) {
            int lane = initialLanes[i];
            Banana car = new Banana();
            car.setLane(lane);
            addObject(car, getWidth() + 160 + (i * 220), getLaneY(lane));
        }

        showHud();
    }
    
    private void spawnRandomCar()
    {
        Banana car = new Banana();
        int randomLane = Greenfoot.getRandomNumber(getLaneCount());
        car.setLane(randomLane);
        // Spawn closer to the screen edge so new traffic appears sooner.
        addObject(car, getWidth() + 40, getLaneY(randomLane));
    }
}
