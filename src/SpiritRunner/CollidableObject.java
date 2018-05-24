package SpiritRunner;

import java.awt.Image;

public class CollidableObject extends Object {

    public enum ObjectType {
        BEER, COIN, ENEMY
    }

    protected ObjectType objectType;
    protected boolean remove;
    public Image image;

    public CollidableObject(){
        posX = posY = speedX = speedY = 0;
        width = height = 0;
        image = null;
        remove = false;
    }

    public CollidableObject(double posX, double posY, char chosenObject ) {
        this.posX = posX;
        this.posY = posY;

        if (Character.getNumericValue(chosenObject)- Main.getNoTiles() < Main.noObjects) {
            int objectNumber = Character.getNumericValue(chosenObject) - Main.getNoTiles();

            image = Main.objects[objectNumber - 1];
            switch (objectNumber) {
                case 1:
                    objectType = ObjectType.BEER;
                    break;
                case 2:
                    objectType = ObjectType.COIN;
                    break;
                case 3:
                    objectType = ObjectType.ENEMY;
                    break;
            }
        }
    }
    //returns true if there was a collision
    public boolean update(Player player) {
        if (checkPlayerCollision(player)) {
            //do something
            return true;
        } else return false;
    }

    public boolean checkPlayerCollision(Player player) {
        if (player.getPosX() + 0.7*player.getWidth() > this.posX && player.getPosX() + 0.3*player.getWidth()< this.posX + this.width) {
            if (player.getPosY() + 0.9*player.getHeight() > this.posY && player.getPosY() + 0.1*player.getHeight() < this.posY + this.height) {
                return true;
            }
        }
        return false;
    }

    public ObjectType getObjectType() { return objectType; }
    public Image getImage() { return image; }
    public boolean isRemove() {return remove;}
    public void setRemove(boolean remove) {
        this.remove = remove;
    }
}
