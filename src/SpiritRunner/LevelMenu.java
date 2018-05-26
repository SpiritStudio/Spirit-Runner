package SpiritRunner;

import java.util.ArrayList;

public class LevelMenu extends Menu{

    private int levelNumber = 0;
    private static int noButtonsX = 5, noButtonsY = 3;

    public LevelMenu() {
        noButtons = noButtonsX*noButtonsY;
        buttons = new ArrayList<>();
        for (int i = 0; i < noButtonsY; i++)
            for(int j = 0; j < noButtonsX; j++)
                buttons.add(new Button(Main.getGameWidth()/noButtonsX*j, Main.getGameHeight()/noButtonsY*i, Main.getGameWidth()/8, Main.getGameHeight()/8, String.format("%02d", i*noButtonsX+j+1)));
    }

    public void pressButton(int mousePosX, int mousePosY) {
        for (int i = 0; i < noButtonsY*noButtonsX; i++)
            if (buttons.get(i).pressButton(mousePosX,mousePosY)){
                levelNumber = i;
                reset(levelNumber);
            }
    }

    public void reset(int levelNumber) {
        Main.setLevelStart(false);
        Main.getPlayer().reset();
        Main.getLevel().reset();
        Main.getBg1().reset(0,0, Main.getBg1().getParallax());
        Main.getBg2().reset(Main.getBg1().getWidth(), 0, Main.getBg2().getParallax());
        Main.getBg2_1().reset(0,0, Main.getBg2_1().getParallax());
        Main.getBg2_2().reset(Main.getBg2_1().getWidth(), 0, Main.getBg2_2().getParallax());
        Main.getLevel().start(levelNumber+1);
        Main.setGameState(Main.GameState.GAME);
    }

    public void reset() {
        reset(levelNumber);
    }

    public void update() {
        //TODO MENU ANIMATIONS
    }

    public static int getNoButtonsX() {
        return noButtonsX;
    }
    public static int getNoButtonsY() {
        return noButtonsY;
    }

    public int getLevelNumber() { return levelNumber; }
    public void setLevelNumber(int levelNumber) { this.levelNumber = levelNumber; }
}
