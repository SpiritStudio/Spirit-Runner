package SpiritRunner;

import java.util.ArrayList;

public class Menu {
    private ArrayList<Button> buttons;
    private static int noButtons = 3;

    public Menu() {
        buttons = new ArrayList<>();
        for (int i = 0; i < noButtons; i++)
            buttons.add(new Button(Main.getGameWidth()/2 - 100, 200.0+i*(90.0), 200, 40, " "));

        buttons.get(0).setText("PLAY");
        buttons.get(1).setText("HIGHEST SCORES");
        buttons.get(2).setText("EXIT");
    }

    public void pressButton(int mousePosX, int mousePosY) {
        if (buttons.get(0).pressButton(mousePosX,mousePosY)) Main.setGameState(Main.GameState.LEVELMENU);
      //  else if (buttons[1].pressButton(mousePosX,mousePosY))// Main.setGameState(Main.GameState.MENU;
        else if (buttons.get(2).pressButton(mousePosX,mousePosY)) Main.setGameState(Main.GameState.EXIT);
    }

    public void update() {
        //TODO MENU ANIMATIONS
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public static int getNoButtons() {
        return noButtons;
    }
}
