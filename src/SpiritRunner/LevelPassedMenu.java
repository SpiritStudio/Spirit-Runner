package SpiritRunner;

import java.util.ArrayList;

public class LevelPassedMenu extends Menu {

    public LevelPassedMenu(){
        noButtons = 3;
        buttons = new ArrayList<>();
        buttons.add(new Button(Main.getGameWidth()/2 - 100, 200.0, 200, 40, "NEXT LEVEL"));
        buttons.add(new Button(Main.getGameWidth()/2 - 100, 300.0, 200, 40, "MAIN MENU"));
        buttons.add(new Button(Main.getGameWidth()/2 - 100, 400.0, 200, 40, "REWATCH"));
    }

    public void pressButton(int mousePosX, int mousePosY) {
        if (buttons.get(0).pressButton(mousePosX, mousePosY)) {
            Main.getLevelMenu().setLevelNumber(Main.getLevelMenu().getLevelNumber() + 1);
            Main.getLevelMenu().reset(Main.getLevelMenu().getLevelNumber(), false);
        } else if (buttons.get(1).pressButton(mousePosX, mousePosY)) Main.setGameState(Main.GameState.MAINMENU);
        else if (buttons.get(2).pressButton(mousePosX, mousePosY)) {
            if (LevelMenu.getLevelNumber() > 0) {
                Main.setGameState(Main.GameState.REPLAY);
                for (int i = 0; i < LevelMenu.jumps.size();i++) System.out.println(LevelMenu.jumps.get(i));
                Main.getLevelMenu().reset(true);
            }
            else {
                Main.getLevelMenu().resetInfinite();
            }
        }
    }
}
