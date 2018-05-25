package SpiritRunner;

import java.util.ArrayList;

public class Menu {
    protected ArrayList<Button> buttons;
    protected int noButtons;

    public Menu(){}
    public void pressButton(int mousePosX, int mousePosY){}
    public void update() {
        //TODO MENU ANIMATIONS
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public int getNoButtons() {
        return noButtons;
    }
}
