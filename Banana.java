import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Banana extends Actor
{
    private int laneIndex;

    public Banana()
    {
        laneIndex = 0;
        setImage(createCarImage(randomColor()));
    }

    public void act()
    {
        if (!(getWorld() instanceof MyWorld)) {
            return;
        }

        MyWorld world = (MyWorld) getWorld();
        if (!world.isGameRunning()) {
            return;
        }

        setLocation(getX() - world.getCarSpeed(), world.getLaneY(laneIndex));

        if (getX() <= 0) {
            resetToRightSide(world);
            return;
        }

        if (isTouching(Hero.class)) {
            world.onHeroHit();
        }
    }

    public void setLane(int lane)
    {
        laneIndex = lane;
    }

    private void resetToRightSide(MyWorld world)
    {
        int spawnX = world.getWidth() + 120 + Greenfoot.getRandomNumber(120);
        setImage(createCarImage(randomColor()));
        setLocation(spawnX, world.getLaneY(laneIndex));
    }



    private GreenfootImage createCarImage(Color bodyColor)
    {
        GreenfootImage car = new GreenfootImage(82, 34);
        car.setColor(new Color(0, 0, 0, 80));
        car.fillOval(10, 24, 58, 8);

        car.setColor(bodyColor);
        car.fillRect(10, 8, 52, 14);
        car.fillRect(24, 2, 22, 8);

        car.setColor(new Color(190, 225, 255));
        car.fillRect(26, 4, 8, 6);
        car.fillRect(36, 4, 8, 6);

        car.setColor(new Color(24, 24, 24));
        car.fillOval(18, 18, 13, 13);
        car.fillOval(43, 18, 13, 13);

        return car;
    }

    private Color randomColor()
    {
        Color[] colors = {
            new Color(233, 86, 86),
            new Color(79, 148, 241),
            new Color(249, 188, 66),
            new Color(124, 198, 104)
        };
        return colors[Greenfoot.getRandomNumber(colors.length)];
    }
}
