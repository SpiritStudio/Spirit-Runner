package SpiritRunner;

public class Beer extends CollidableObject{

    public static int beerHeight = 40, beerWidth = 80;

    public Beer(){
        objectType = ObjectType.BEER;
        posX = posY = speedX = speedY = 0;
        width = height = 0;
        image = null;
    }

    public Beer(double posX, double posY, int width, int height, char typeChar){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.speedX = 0;
        this.speedY = 0;
        objectType = ObjectType.BEER;
        if (Character.getNumericValue(typeChar) <= Main.noObjects) {
            image = Main.objects[Character.getNumericValue(typeChar)-1];
        }
    }

    public boolean update(Player player) {
        if (checkPlayerCollision(player)) {
            player.increaseBeerCount();
            return true;
        } else return false;
    }
}
