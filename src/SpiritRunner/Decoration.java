package SpiritRunner;

import java.awt.*;

public class Decoration extends Object {
    private Image image;

    public Decoration() {
        posX = posY = speedX = speedY = 0;
        width = height = 0;
    }

    public Decoration(double posX, double posY, char chosenObject) {
        this.posX = posX;
        this.posY = posY;
        speedX = speedY = 0;

        if (Character.getNumericValue(chosenObject) <= Main.getNoObjects() + Main.getNoDecorations()) {
            int objectNumber = Character.getNumericValue(chosenObject);

            image = Main.objects[objectNumber - 1];
        }
    }
    public Image getImage() {
        return image;
    }
}
