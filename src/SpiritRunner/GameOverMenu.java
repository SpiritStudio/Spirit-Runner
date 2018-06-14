package SpiritRunner;

import java.util.ArrayList;

public class GameOverMenu extends Menu{

    public GameOverMenu(){
        noButtons = 2;
        buttons = new ArrayList<>();
        buttons.add(new Button(Main.getGameWidth()/2 - 100, 200.0, 200, 40, "TRY AGAIN"));
        buttons.add(new Button(Main.getGameWidth()/2 - 100, 300.0, 200, 40, "MAIN MENU"));
    }

    public void pressButton(int mousePosX, int mousePosY) {
        if (buttons.get(0).pressButton(mousePosX,mousePosY)) {

            if (LevelMenu.getLevelNumber() > 0)
                Main.getLevelMenu().reset(false);
            else
                Main.getLevelMenu().resetInfinite();

        }
        else if (buttons.get(1).pressButton(mousePosX,mousePosY)) Main.setGameState(Main.GameState.MAINMENU);
    }

}
