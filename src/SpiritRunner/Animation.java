package SpiritRunner;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Animation {
    private ArrayList<Image> images;
    private ArrayList<Integer> stateLength;
    private ArrayList<Integer> framesLength;
    private int currentFrame, currentState, timer, firstImageToDisplay;

    public Animation() {
        images = new ArrayList<Image>();
        stateLength = new ArrayList<Integer>();
        framesLength = new ArrayList<Integer>();
        currentFrame = 0;
        currentState = 0;
        timer = 0;
        firstImageToDisplay = 0;
    }

    //When you want to add another state (e.g. player hanging/running), run addState method, then addFrame - multiple times
    public void addState(int stateLength, int framesLength) {
        this.stateLength.add(stateLength);
        this.framesLength.add(framesLength);
    }

    public void addFrame(Image image) {
        images.add(image);
    }

    public void update() {
        timer++;

        if (timer >= framesLength.get(currentState)) {
            currentFrame++;
            timer = 0;
        }

        if (currentFrame >= stateLength.get(currentState)) {
            currentFrame = 0;
        }
    }

    public void paint(Graphics g, int posX, int posY, ImageObserver observer) {
        g.drawImage(images.get(currentFrame + firstImageToDisplay), posX, posY, observer);
    }

    public void setState(int currentState) {
        this.currentState = currentState;
        firstImageToDisplay = 0;
        for (int i = 0; i < currentState; i++)
            firstImageToDisplay += stateLength.get(i);
    }

    public Image getImage(int frame) {
        return images.get(frame);
    }

}
