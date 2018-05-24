package SpiritRunner;

import java.awt.*;

public class Button extends Object {
    private String text;

    public Button() {
        this.posX = 0;
        this.posY = 0;
        this.width = 0;
        this.height = 0;
        this.text = " ";
        this.speedX = 0;
        this.speedY = 0;
    }

    public Button(double posX, double posY, int width, int height, String text) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.text = text;
        this.speedX = 0;
        this.speedY = 0;
    }

    public boolean pressButton(int mousePosX, int mousePosY) {
        if (mousePosX > posX && mousePosX < posX+width && mousePosY > posY && mousePosY < posY+height) return true;
        else return false;
    }

    public String getText() { return text; }
    public void setText(String text) {
        this.text = text;
    }

}
