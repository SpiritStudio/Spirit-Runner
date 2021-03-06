package SpiritRunner;

import java.awt.Image;

public class Tile extends Object {
    private char type;
    private char interactionType; //b - background, s - standable, h - hangable, c - collidable, but neither s nor h
    public Image tileImage;
    private Background bg = Main.getBg1();

    private static int TileWidth = 40;

    public Tile() {
        posX = posY = speedX = speedY = 0;
        width = height = 0;
        type = ' ';
        tileImage = null;
    }

    public Tile(double x, double y, char typeChar) {
        this.speedX = this.speedY = 0;
        this.posX = x * TileWidth;
        this.posY = y * TileWidth;
        this.width = this.height = TileWidth;
        type = typeChar;

        if (type == '5') interactionType = 's';
        else if (type == '2') interactionType = 'h';
        else if (type == '1' || type == '3' || type == '4') interactionType = 'c';
        else if (type == '6') interactionType = 'b';

        if (Character.getNumericValue(type) <= Main.noTiles) {
            tileImage = Main.tiles[Character.getNumericValue(type)-1];
        }
    }

    public boolean checkPlayerInteraction(Player player){
        if(player.getPosX() + 0.9*player.getWidth() <= this.getPosX() || player.getPosX() + 0.1*player.getWidth() >= this.getPosX() + TileWidth ) {
            return false;
        } else {
            if (player.getPosY() + player.getHeight() <= this.posY && player.getPosY() + player.getHeight() + player.getSpeedY() >= this.posY && this.interactionType == 's' && player.getSpeedY() >= 0) { // checks y collision
                player.setInAir(false);
                player.setPosY(this.getPosY() - player.getHeight());
                return true; //standing
            }
            /*else if (player.getPosY() >= this.posY + this.getHeight() && player.getPosY() + player.getSpeedY() < this.posY + this.getHeight() && (this.interactionType == 's' || this.interactionType == 'c') && player.getSpeedY() < 0) { // checks y collision
                player.setPosY(this.getPosY() + this.getWidth());
                player.setSpeedY(0);
                return true; // ceiling hit
            } */else if ((player.getPosY() == this.posY || (player.getPosY() < this.posY && player.getPosY() + player.getSpeedY() >= this.posY )) && this.interactionType == 'h' && player.getSpeedY() >= 0) {
                player.setHanging(true);
                player.setInAir(false);
                player.setPosY(this.getPosY()); // change if you want a different height of the hanging position
                return true; // hanging
            } else return false;
        }
    }



    public Image getTileImage() {
        return tileImage;
    }

    public char getTileType() { return type; }

    public char getInteractionType() {return interactionType; }

    public static int getTileWidth() { return TileWidth; }

}
