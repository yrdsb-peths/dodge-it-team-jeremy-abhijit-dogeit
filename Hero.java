import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Hero extends Actor
{
    private static final int HERO_X = 100;
    private int laneIndex = 1;

    public Hero()
    {
        GreenfootImage heroImage = new GreenfootImage("man01.png");
        heroImage.scale(60, 60);
        setImage(heroImage);
    }

    public void act()
    {
        if (!(getWorld() instanceof MyWorld)) {
            return;
        }

        MyWorld world = (MyWorld) getWorld();
        if (world.isGameRunning()) {
            String key = Greenfoot.getKey();
            if ("w".equals(key) || "up".equals(key)) {
                laneIndex--;
            } else if ("s".equals(key) || "down".equals(key)) {
                laneIndex++;
            }

            if (laneIndex < 0) {
                laneIndex = 0;
            }

            int lastLane = world.getLaneCount() - 1;
            if (laneIndex > lastLane) {
                laneIndex = lastLane;
            }
        }

        setLocation(HERO_X, world.getLaneY(laneIndex));
    }
}
