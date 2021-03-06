package SpiritRunner;

import java.util.ArrayList;

public class MainMenu extends Menu{

    public MainMenu() {
        noButtons = 3;
        buttons = new ArrayList<>();
        for (int i = 0; i < noButtons; i++)
            buttons.add(new Button(Main.getGameWidth()/2 - 100, 200.0+i*(90.0), 200, 40, " "));

        buttons.get(0).setText("PLAY");
        buttons.get(1).setText("INFINITE MODE");
        buttons.get(2).setText("EXIT");
    }

    public void pressButton(int mousePosX, int mousePosY) {
        if (buttons.get(0).pressButton(mousePosX,mousePosY)) Main.setGameState(Main.GameState.LEVELMENU);
        else if (buttons.get(1).pressButton(mousePosX,mousePosY)) {
            Main.setLevelStart(false);
            Main.getPlayer().reset();
            Main.getLevel().reset();
            Main.getBg1().reset(0,0, Main.getBg1().getParallax());
            Main.getBg2().reset(Main.getBg1().getWidth(), 0, Main.getBg2().getParallax());
            Main.getBg2_1().reset(0,0, Main.getBg2_1().getParallax());
            Main.getBg2_2().reset(Main.getBg2_1().getWidth(), 0, Main.getBg2_2().getParallax());
            Main.getLevel().start(0);
            LevelMenu.setLevelNumber(0);
            Main.setGameState(Main.GameState.INFINITEGAME);
        }
        else if (buttons.get(2).pressButton(mousePosX,mousePosY)) Main.setGameState(Main.GameState.EXIT);
    }

    public void update() {
        //TODO MENU ANIMATIONS
    }
}
