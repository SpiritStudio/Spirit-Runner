package SpiritRunner;

public class Coin extends CollidableObject {

    public static int coinHeight = 40, coinWidth = 40;
    private int value = 1;

    public Coin(){
        objectType = ObjectType.COIN;
        posX = posY = speedX = speedY = 0;
        width = height = 0;
        image = null;
    }

    public Coin(double posX, double posY, int width, int height, int val, char typeChar){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.speedX = 0;
        this.speedY = 0;
        objectType = ObjectType.COIN;
        value = val;
        if (Character.getNumericValue(typeChar) <= Main.noObjects) {
            image = Main.objects[Character.getNumericValue(typeChar)-1];
        }
    }

    //returns true if there was a collision
    public boolean update(Player player) {
        if (remove) {
            speedY = -10;
            posY += speedY;
            return false;
        }
        else if (checkPlayerCollision(player)) {
            player.setScore(player.getScore() + value);
            return true;
        } else return false;
    }

}
