package SpiritRunner;

public class Enemy extends CollidableObject {

    public static int enemyHeight = 80, enemyWidth = 60;

    public Enemy(){
        objectType = ObjectType.ENEMY;
        posX = posY = speedX = speedY = 0;
        width = height = 0;
        image = null;
    }

    public Enemy(double posX, double posY, int width, int height, char typeChar){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.speedX = 0;
        this.speedY = 0;
        objectType = ObjectType.ENEMY;
        if (Character.getNumericValue(typeChar) <= Main.noObjects) {
            image = Main.objects[Character.getNumericValue(typeChar)-1];
        }
    }

    public boolean update(Player player) {
        if (checkPlayerCollision(player)) {
            Main.setGameState(Main.GameState.GAMEOVERMENU);
            return true;
        } else return false;
    }

    public int x=0, y=0;
    public void findTileUnder(){

    }

}
