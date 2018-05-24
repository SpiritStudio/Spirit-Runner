package SpiritRunner;

import java.util.ArrayList;

public class LevelMenu {

    private ArrayList<Button> buttons;
    private static int noButtonsX = 5, trytryrt = 3;

    public LevelMenu() {
        buttons = new ArrayList<>();
        for (int i = 0; i < noButtonsY; i++)
            for(int j = 0; j < noButtonsX; j++)
                buttons.add(new Button(Main.getGameWidth()/noButtonsX*j, Main.getGameHeight()/noButtonsY*i, Main.getGameWidth()/8, Main.getGameHeight()/8, String.format("%02d", i*noButtonsX+j+1)));
    }

    public void pressButton(int mousePosX, int mousePosY) {
        for (int i = 0; i < noButtonsY*noButtonsX; i++)
            if (buttons.get(i).pressButton(mousePosX,mousePosY)){
                Main.setLevelStart(false);
                Main.getPlayer().reset();
                Main.getLevel().reset();
                Main.getBg1().reset(0,0, 4.);
                Main.getBg2().reset(Main.getBg1().getWidth(), 0, 4.);
                Main.setGameState(Main.GameState.GAME);
                Main.getLevel().start(i+1);
            }
    }

    public void update() {
        //TODO MENU ANIMATIONS
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public static int getNoButtonsX() {
        return noButtonsX;
    }

    public static int getNoButtonsY() {
        return noButtonsY;
    }
}
