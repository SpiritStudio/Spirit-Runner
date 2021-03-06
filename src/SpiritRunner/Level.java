package SpiritRunner;

import java.io.*;
import java.util.ArrayList;

public class Level {
    private ArrayList<Tile> tilearray;
    private ArrayList<CollidableObject> objectarray;
    private ArrayList<Decoration> decorationarray;
    private int width, height;

    public static int StandardHeight  = 12;


    public Level() {
        reset();
    }

    public void reset(){
        this.width = 0;
        this.height = 0;
        tilearray = new ArrayList<Tile>();
        objectarray = new ArrayList<CollidableObject>();
        decorationarray = new ArrayList<Decoration>();
    }

    public void start(int noMap) {
        if (noMap >= 1) {
            try {
                String map = "/data/map" + noMap + ".txt";
                loadMap(map);
                System.out.println("Mapa " + noMap + " zaladowana!");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            MapGenerator.generateMap(this);
        }

    }

    private void loadMap(String filename) throws IOException {
        ArrayList lines = new ArrayList();

        //String filePath = new File("").getAbsolutePath();
        String filePath = "X:/Gierka"; //TODO Something wrong with absolute path reading;
        filePath = filePath.replace('\\', '/');
        filePath = filePath + "/src/SpiritRunner/" + filename;
        System.out.println(filePath);

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            if (!line.startsWith("#")) {
                lines.add(line);
                width = Math.max(width, line.length());
            } else {
                break;
            }
        }
        height = lines.size();

        for (int j = 0; j < height; j++) {
            String line = (String) lines.get(j);
            for (int i = 0; i < width; i++) {
                //System.out.println(i + "is i ");

                if (i < line.length()) {
                    char ch = line.charAt(i);
                    if (Character.isDigit(ch) || Character.isLetter(ch))
                        if (Character.getNumericValue(ch) != 0)
                            placeTile( ch, i,j );
                }

            }
        }

        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }
            double posX, posY;
            char chosenObject;
            String linesTemp[] = line.split(",");

            chosenObject = linesTemp[0].charAt(0);
            posX = Double.parseDouble(linesTemp[1]);
            posY = Double.parseDouble(linesTemp[2]);
            //System.out.println(chosenObject);
            //System.out.println(posX);
            //System.out.println(posY);
            if (chosenObject == '1')
                objectarray.add(new Beer(posX, posY, Beer.beerWidth, Beer.beerHeight, '1'));
            else if (chosenObject == '2')
                objectarray.add(new Coin(posX, posY, Coin.coinWidth, Coin.coinHeight, 1,  '2'));
            else if (chosenObject == '3')
                objectarray.add(new Enemy(posX, posY, Enemy.enemyWidth, Enemy.enemyHeight, '3'));
            else
                decorationarray.add(new Decoration(posX, posY, chosenObject));
        }
    }

    public void placeTile(char chosenTile, int posX, int posY) {
        boolean placeable = true;
        if (tilearray.size() > 0) {
            for (int i = 0; i < tilearray.size(); i++) {
                if (tilearray.get(i).getPosX()/Tile.getTileWidth() == (double)posX) {
                    if (tilearray.get(i).getPosY()/Tile.getTileWidth() == (double)posY) {
                        placeable = false;
                        break;
                    }
                }
            }
        }
        if (placeable) {
            Tile t = new Tile(posX, posY, chosenTile);
            tilearray.add(t);

            //System.out.println("placed");
        }
    }

    public void update(Player player){
        //checks collisions with tiles
        player.setInAir(true);
        player.setHanging(false);

        for (int i =0; i < tilearray.size(); i++)
            if (tilearray.get(i).checkPlayerInteraction(player))
               break;


        //checks collision with collidable objects
        for(int i = 0; i < objectarray.size(); i++) {
            objectarray.get(i).update(player);
            if (objectarray.get(i).checkPlayerCollision(player) && (objectarray.get(i).getObjectType() == CollidableObject.ObjectType.COIN ||objectarray.get(i).getObjectType() == CollidableObject.ObjectType.BEER)) {
                objectarray.get(i).setRemove(true);
            }
            if (objectarray.get(i).isRemove() && objectarray.get(i).getPosY() < -200) {
                objectarray.remove(i);
            }
        }
    }

    public ArrayList<Tile> getTilearray() { return tilearray; }
    public Tile getTile(int tileIndex){return tilearray.get(tileIndex); }
    public ArrayList<CollidableObject> getObjectarray() { return objectarray; }
    public CollidableObject getObject(int objectIndex){ return objectarray.get(objectIndex); }
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) {this.height = height;}

    public ArrayList<Decoration> getDecorationarray() {
        return decorationarray;
    }
}
