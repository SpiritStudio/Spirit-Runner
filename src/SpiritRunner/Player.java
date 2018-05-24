package SpiritRunner;

public class Player extends Object {

    private boolean inAir = true, hanging = false;
    private int score = 0, beerCounter = 0;

    public static double initialSpeed = 6.0;

    public Player() {
        reset();
    }

    public void reset(){
        posX = speedY = score = beerCounter = 0;
        posY = 20;
        speedX = 0;
        width = 60;
        height = 51;
    }

    public void jump() {
        if (!inAir) speedY = -20;
        inAir = true;
    }

    public void update(){
        if(inAir) speedY += 1;
        else speedY = 0;
        if(posY + height > Main.getGameHeight()) Main.setGameState(Main.GameState.GAMEOVERMENU); //TODO PLAYER RESET AFTER DEATH
        if (posX + width > Main.getLevel().getWidth() * Tile.getTileWidth()) {
            posX = Main.getLevel().getWidth() * Tile.getTileWidth() - width;
            speedX = 0;
        }

        posX += speedX;
        posY += speedY;
    }

    public boolean isInAir() { return inAir; }
    public boolean isHanging() { return hanging; }
    public void setHanging(boolean hanging) { this.hanging = hanging; }
    public void setInAir(boolean inAir) { this.inAir = inAir; }
    public void increaseBeerCount(){ beerCounter++; }
    public int getScore() { return score; }
    public int getBeerCounter() { return beerCounter; }
    public void setScore(int score) { this.score = score; }
}
