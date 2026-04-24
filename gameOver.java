import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class gameOver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class gameOver extends Actor
{
    private final int finalScore;
    private final int bestScore;
    private int pulseTick;

    public gameOver(int score, int best)
    {
        finalScore = score;
        bestScore = best;
        pulseTick = 0;
        drawPanel(true);
    }

    /**
     * Act - do whatever the gameOver wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        pulseTick = (pulseTick + 1) % 50;
        if (pulseTick == 0 || pulseTick == 25) {
            drawPanel(pulseTick < 25);
        }
    }

    private void drawPanel(boolean brightPrompt)
    {
        GreenfootImage panel = new GreenfootImage(460, 205);
        panel.setColor(new Color(18, 20, 25, 228));
        panel.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        panel.setColor(new Color(255, 255, 255, 150));
        panel.drawRect(0, 0, panel.getWidth() - 1, panel.getHeight() - 1);

        GreenfootImage title = new GreenfootImage("CRASHED!", 58, new Color(255, 98, 98), new Color(0, 0, 0, 0));
        GreenfootImage scoreLine = new GreenfootImage("Distance: " + finalScore + " m", 32, new Color(255, 255, 255), new Color(0, 0, 0, 0));
        GreenfootImage bestLine = new GreenfootImage("Best: " + bestScore + " m", 28, new Color(180, 225, 255), new Color(0, 0, 0, 0));
        Color promptColor = brightPrompt ? new Color(130, 255, 182) : new Color(88, 195, 133);
        GreenfootImage hint = new GreenfootImage("PRESS ENTER/SPACE/R OR CLICK", 28, promptColor, new Color(0, 0, 0, 0));

        panel.drawImage(title, (panel.getWidth() - title.getWidth()) / 2, 18);
        panel.drawImage(scoreLine, (panel.getWidth() - scoreLine.getWidth()) / 2, 84);
        panel.drawImage(bestLine, (panel.getWidth() - bestLine.getWidth()) / 2, 120);
        panel.drawImage(hint, (panel.getWidth() - hint.getWidth()) / 2, 160);

        setImage(panel);
    }
}
