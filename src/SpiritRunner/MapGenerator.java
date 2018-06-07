package SpiritRunner;

import java.util.ArrayList;

public class MapGenerator {
    public static void generateMap(Level level) {
        level.setWidth(10000);
        level.setHeight(Level.StandardHeight);

        int x = 0, y = 6, width = 8;
        for (int i = 0; i < 100; i++) {
            placePlatform(level, x, y, width);
            //x = x + width + (int)(Math.random()*4+1) + (int)(Main.getPlayer().getSpeedX()-6.0);
            x = x + width + (int)(Math.random()*4+1) + x/(4000/Tile.getTileWidth());
            if (y < 4) y = y + (int)(Math.random()*4);
            else if (y > 8) y = y - (int)(Math.random()*4);
            else y = y + (int)(Math.random()*8 - 4);

            width = (int)(Math.random()*6 + 5) + x/(8000/Tile.getTileWidth());
        }
    }

    private static void placePlatform(Level level, int x, int y, int width) {
        for (int i = 0; i < width; i++)
            level.placeTile('5', x+i, y);

        for (int i = 0; i < width; i++) {
            for (int j = y+1; j < 12; j++)
                level.placeTile('1', x+i, j);
        }

        if(Math.random() >= 0.5) {
            int coinsWidth = (int)(Math.random()*(width-1.0)+1);
            placeCoins(level, x + (width - coinsWidth)/2, y-1, coinsWidth);
        }
    }

    private static void placeCoins(Level level, int x, int y, int width) {
        ArrayList<CollidableObject> o = level.getObjectarray();
        for (int i = 0; i < width; i++)
            o.add(new Coin((x+i)*Coin.coinWidth, (y)*Coin.coinHeight, Coin.coinWidth, Coin.coinHeight, 1,  '2'));
    }
}
